package miniproject.stellanex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;


    public void join(String member_id, String member_pw, String nickname) {

        // userId(email) 중복 check
        userRepository.findByUserId(userId)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERID_DUPLICATED, userId + "는 이미 존재하는 회원입니다 .");
                });

        userRepository.findByNickname(nickname)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.NICKNAME_DUPLICATED, nickname + "는 이미 존재하는 닉네임입니다.");
                });

        // 저장
        User user = User.builder()
                .userId(userId)
                .password(encoder.encode(password))
                .nickname(nickname)
                .build();
        userRepository.save(user);

    }

    public JwtResponse login(String userId, String password) {

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtResponse token = jwtProvider.createToken(authentication);

        return token;
    }
