package com.stellanex.config;

import com.stellanex.config.filter.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(cf -> cf.disable()); //CSRF 보호 비활성화 (JavaScript 호출)
        http.authorizeHttpRequests(security -> security
                .requestMatchers(new AntPathRequestMatcher("/member/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/manager/**")).hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .anyRequest().permitAll());

        http.formLogin(frmLogin -> frmLogin.disable()); //Form을 이요한 로그인을 사용하지 않겠다는 설정
        http.httpBasic(basic -> basic.disable()); // Http Basic 인증방식을 사용하지 않겠다는 설정

        // 세션을 유지하지 않겠다고 설정 -> URL 호출 뒤 응답할때까지는 유지되지만 응답 후 삭제된다는 의미
        http.sessionManagement(ssmn -> ssmn.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(ex -> ex.accessDeniedPage("/accessDenied"));
        http.logout(logout -> logout
                .invalidateHttpSession(true) //현재 브라우저와 연결된 세션 강제 종료
                .deleteCookies("JSESSIONID") // 세션 아이디가 저장된 쿠키를 삭제
                .logoutSuccessUrl("/login")); // 로그아웃 후 이동할 URL 지정
        return http.build();
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 스프링 시큐리티가 등록한 필터체인의 뒤에 작성한 필터를 추가한다.
        http.addFilterBefore(new JWTAuthenticationFilter(
                        authenticationConfiguration.getAuthenticationManager()),
                UsernamePasswordAuthenticationFilter.class); // JWT 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
        return http.build();
    }
}