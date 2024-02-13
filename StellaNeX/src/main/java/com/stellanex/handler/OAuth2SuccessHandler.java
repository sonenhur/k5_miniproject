package com.stellanex.handler;

import com.stellanex.domain.Member;
import com.stellanex.domain.Role;
import com.stellanex.persistence.MemberRepository;
import com.stellanex.util.CustomMyUtil;
import com.stellanex.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
// @RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private MemberRepository memRepo;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2SuccessHandler:onAuthenticationSuccess");
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        //임의의 사용자를 만들어서 서버에 저장
        String username = CustomMyUtil.getUsernameFromOAuth2User(user);
        if (username == null) {
            log.error("onAuthenticationSuccess: Cannot generate username from oauth2user!");
            throw new ServletException("Cannot generate username from oauth2user!");
        }
        log.info("onAuthenticationSuccess:" + username);
        memRepo.save(Member.builder()
                .username(username)
                .password(encoder.encode("1a2s3d4f"))
                .role(Role.valueOf("ROLE_MEMBER"))
                .enabled(true)
                .build());
        String jwtToken = JWTUtil.getJWT(username);
        response.addHeader(HttpHeaders.AUTHORIZATION, jwtToken);
    }
}
