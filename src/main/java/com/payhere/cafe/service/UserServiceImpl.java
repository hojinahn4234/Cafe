package com.payhere.cafe.service;

import com.payhere.cafe.dto.request.LoginRequestDTO;
import com.payhere.cafe.dto.request.TokenRequestDTO;
import com.payhere.cafe.dto.response.UserResponseDTO;
import com.payhere.cafe.entity.User;
import com.payhere.cafe.jwt.JwtTokenProvider;
import com.payhere.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;
    private JwtTokenProvider jwtTokenProvider;
    private RedisTemplate redisTemplate;
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public int join(User user) throws NoSuchAlgorithmException {
        User requestUser = new User();
        requestUser.setPhoneNum(user.getPhoneNum());
        requestUser.setPw(encrypt(user.getPw()));
        if(repository.findByPhoneNum(user.getPhoneNum()) == null) {
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

    @Override
    public int login(LoginRequestDTO loginRequestDTO) throws NoSuchAlgorithmException {
        User user = repository.findByPhoneNum(loginRequestDTO.getPhoneNum());
        if(user != null) {
            if((user.getPw()).equals(encrypt(loginRequestDTO.getPhoneNum()))) {
                // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
                // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
                UsernamePasswordAuthenticationToken authenticationToken = loginRequestDTO.toAuthentication();

                // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
                // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

                // 3. 인증 정보를 기반으로 JWT 토큰 생성
                UserResponseDTO.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

                // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
                redisTemplate.opsForValue()
                        .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
                return 200;
            }
            else {
                return 2;
            }
        } else {
            return 1;
        }
    }

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
