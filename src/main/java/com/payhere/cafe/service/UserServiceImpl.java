package com.payhere.cafe.service;

import com.payhere.cafe.dto.response.Response;
import com.payhere.cafe.dto.request.LoginRequestDTO;
import com.payhere.cafe.dto.request.TokenRequestDTO;
import com.payhere.cafe.dto.response.UserResponseDTO;
import com.payhere.cafe.entity.User;
import com.payhere.cafe.jwt.JwtTokenProvider;
import com.payhere.cafe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Response response;
    private final StringRedisTemplate redisTemplate;

    @Autowired
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private static final String ID_KEY = "id";

    public UserServiceImpl(
            UserRepository repository, JwtTokenProvider jwtTokenProvider,
            Response response, StringRedisTemplate redisTemplate, AuthenticationManagerBuilder authenticationManagerBuilder
    ){
        this.repository = repository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.response = response;
        this.redisTemplate = redisTemplate;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Transactional
    @Override
    public ResponseEntity<?> login(LoginRequestDTO loginRequestDTO) {
        LoginRequestDTO newrequest = new LoginRequestDTO();
        newrequest.setPhonenum(loginRequestDTO.getPhonenum());
        try {
            newrequest.setPw(encrypt(loginRequestDTO.getPw()));
        } catch (NoSuchAlgorithmException e) {
            return response.fail("비밀번호 암호화에 실패하였습니다. 죄송합니다.", HttpStatus.BAD_REQUEST);
        }
        User user = repository.findByPhonenum(loginRequestDTO.getPhonenum());
        if (user != null) {
            if ((user.getPw()).equals(newrequest.getPw())) {
                UsernamePasswordAuthenticationToken authenticationToken = newrequest.toAuthentication();
                log.info(authenticationToken.toString());
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                UserResponseDTO.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, Math.toIntExact(user.getId()));
                redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
                return response.success(tokenInfo, "ok", HttpStatus.OK);
            } else {
                return response.fail("비밀번호를 틀리셨습니다.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return response.fail("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public int join(User user) {
        User requestUser = new User();
        requestUser.setPhonenum(user.getPhonenum());
        try {
            requestUser.setPw(encrypt(user.getPw()));
        } catch (NoSuchAlgorithmException e) {
            return 3;
        }
        if(repository.findByPhonenum(user.getPhonenum()) == null) {
            if(repository.save(requestUser) != null) {
                return 200;
            } else {
                return 2;
            }
        }
        else {
            return 1;
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> reissue(TokenRequestDTO tokenRequestDTO) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDTO.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = redisTemplate.opsForValue().get("RT:" + authentication.getName());
        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        if(!refreshToken.equals(tokenRequestDTO.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 4. 새로운 토큰 생성
        UserResponseDTO.TokenInfo tokenInfo = jwtTokenProvider.generateToken(
            authentication, (Integer) jwtTokenProvider.parseClaims(tokenRequestDTO.getAccessToken()).get(ID_KEY));

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
    }

    @Transactional
    @Override
    public int logout(TokenRequestDTO tokenRequestDTO) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDTO.getAccessToken())) {
            return 1;
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(tokenRequestDTO.getAccessToken());
        redisTemplate.opsForValue()
                .set(tokenRequestDTO.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return 200;
    }

    private String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
