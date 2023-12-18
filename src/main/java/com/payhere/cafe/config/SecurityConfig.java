package com.spamallday.payhere.configuration;

import com.spamallday.payhere.auth.JwtAuthenticationFilter;
import com.spamallday.payhere.auth.JwtTokenProvider;
import com.spamallday.payhere.enums.Role;
import com.spamallday.payhere.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate stringRedisTemplate;

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new CustomPasswordEncoder();

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()              // httpBasic 비활성화
                .csrf().disable()                   // CSRF 비활성화
                .cors().and()        // CORS 활성화

                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)         // 세션 관리 없이 token으로 인증
                    .and()
                .authorizeRequests()
                    .antMatchers("/member/signup", "/auth/login").permitAll()   // 로그인, 회원가입은 제한 없음
                    .antMatchers("/product/*").hasRole(Role.USER_OWNER.name())  // OWNER (사장님) 회원만 상품 접근 가능
                    .anyRequest().authenticated()
//                    .anyRequest().permitAll()
                    .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, stringRedisTemplate), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
