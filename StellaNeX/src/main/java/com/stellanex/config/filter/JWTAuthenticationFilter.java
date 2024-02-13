package com.stellanex.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stellanex.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager; //인증 객체

    @Override // POST/login 요청이 왔을 때 인증을 시도하는 메서드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // request에서 json타입의 [username, password]를 읽어서 Member 객체를 생성한다
        ObjectMapper mapper = new ObjectMapper();
        Member member = null;
        try {
            member = mapper.readValue(request.getInputStream(), Member.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Security에게 로그인 요청에 필요한 객체 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

        // 인증 진행 -> UserDetailsService를 상속받은 클래스의 loadUserByUsername을 호출한다
        Authentication auth = authenticationManager.authenticate(authToken);
        System.out.println("auth:" + auth);
        return auth;
    }

    @Override // 인증에 성공했을 때 실행되는 후처리 메서드
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 인증 결과 생성된 Authentication 객체에서 필요한 정보를 읽어서 토큰을 만들어서 헤더에 추가한다.
        User user = (User) authResult.getPrincipal();
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC256("com.stellanex.jwt"));
        response.addHeader("Authorization", "Bearer " + token);
    }
}
