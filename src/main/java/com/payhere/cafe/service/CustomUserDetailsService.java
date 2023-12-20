package com.payhere.cafe.service;

import com.payhere.cafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phonenum) throws RuntimeException {
        com.payhere.cafe.entity.User user = userRepository.findByPhonenum(phonenum);
        // 해당 아이디의 유저가 없을 때
        if (user == null) {
            throw new RuntimeException("유저를 찾을 수 없습니다.");
        }
        return createUserDetails(user);
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(com.payhere.cafe.entity.User user) {
        return new User(user.getPhonenum(), user.getPw(), user.getAuthorities());
    }
}