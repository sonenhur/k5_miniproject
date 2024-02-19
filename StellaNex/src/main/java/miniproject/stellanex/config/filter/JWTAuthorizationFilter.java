package miniproject.stellanex.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import miniproject.stellanex.jwt.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 필터가 요청을 처리하는 메서드

        String token = resolveToken(request); // 토큰 추출

        if (token != null && jwtProvider.validateToken(token)) { // 토큰 유효성 검사
            Authentication authentication = jwtProvider.getAuthentication(token); // 토큰에서 인증 정보 추출
            SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 인증 정보 저장
        }
        filterChain.doFilter(request, response); // 다음 필터로 요청 전달
    }

    private String resolveToken(HttpServletRequest request) {
        // Request Header에서 토큰 추출

        String bearerToken = request.getHeader("Authorization"); // Authorization 헤더에서 토큰 추출

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 부분을 제외한 토큰 반환
        }
        return null; // 토큰이 없으면 null 반환
    }

}
