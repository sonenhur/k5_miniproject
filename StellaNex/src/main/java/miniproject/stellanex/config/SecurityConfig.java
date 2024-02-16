package miniproject.stellanex.config;

import miniproject.stellanex.config.filter.JWTAuthorizationFilter;
import miniproject.stellanex.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtProvider jwtProvider;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 람다식:함수형 인터페이스에만 사용 가능 (메서드가 하나뿐인 경우)
        http.authorizeHttpRequests(auth -> auth
//                .requestMatchers("/member/**").authenticated()
//                .requestMatchers("/admin/**").hasRole("ADMIN") // ROLE_ADMIN인 사람에게 여러 접근 권한을 부여
                // .requestMatchers("/admin/**", "/api/delete/**").hasRole("ADMIN") // ROLE_ADMIN인 사람에게 여러 접근 권한을 부여
                .anyRequest().permitAll());
        // 람다식:함수형 인터페이스에만 사용 가능 (메서드가 하나뿐인 경우)
        http.formLogin(AbstractHttpConfigurer::disable) //Form을 이용한 로그인을 사용하지 않겠다는 설정
                .httpBasic(AbstractHttpConfigurer::disable) // Http Basic 인증 방식을 사용하지 않겠다는 설정
                .csrf(AbstractHttpConfigurer::disable); //CSRF보호 비활성화 (JS에서 호출 가능하도록)

        //세션을 유지하지 않겠다고 설정 -> URL 호출 뒤 응답할때까지는 유지되지만, 응답 후 삭제된다는 의미
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(new JWTAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}