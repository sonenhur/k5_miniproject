package miniproject.stellanex.config;

import miniproject.stellanex.config.filter.JWTAuthenticationFilter;
import miniproject.stellanex.config.filter.JWTAuthorizationFilter;
import miniproject.stellanex.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private MemberRepository memberRepository;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());  //CSRF보호 비활성화 (JS에서 호출 가능하도록)
        // 람다식:함수형 인터페이스에만 사용 가능 (메서드가 하나뿐인 경우)
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/member/**").authenticated()
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // MANAGER, ADMIN에게만 접근 권한을 부여
                .requestMatchers("/admin/**").hasRole("ADMIN") // ROLE_ADMIN인 사람에게 여러 접근 권한을 부여
                // .requestMatchers("/api/crud/**").hasRole("MANAGER") // ROLE_MANAGER인 사람에게 CRUD페이지 권한을 부여
                // .requestMatchers("/admin/**", "/api/delete/**").hasRole("ADMIN") // ROLE_ADMIN인 사람에게 여러 접근 권한을 부여
                .anyRequest().permitAll());
        // 람다식:함수형 인터페이스에만 사용 가능 (메서드가 하나뿐인 경우)
        http.formLogin(frmLogin -> frmLogin.disable()); //Form을 이용한 로그인을 사용하지 않겠다는 설정
        http.httpBasic(basic -> basic.disable());       // Http Basic 인증 방식을 사용하지 않겠다는 설정

        //세션을 유지하지 않겠다고 설정 -> URL 호출 뒤 응답할때까지는 유지되지만, 응답 후 삭제된다는 의미
        http.sessionManagement(ssmn -> ssmn.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //스프링 시큐리티가 등록한 필터체인의 뒤에 작성한 필터를 추가한다
        http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));

        //스프링 시큐리티가 등록한 필터들 중에서 AuthorizationFilter 앞에다가 앞에서 작성한 필터를 삽입한다
        http.addFilterBefore(new JWTAuthorizationFilter(memberRepository), AuthorizationFilter.class);

        return http.build();
    }
}