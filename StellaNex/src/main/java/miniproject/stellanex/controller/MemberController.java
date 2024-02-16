package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import miniproject.stellanex.dto.JoinSuccessResponse;
import miniproject.stellanex.dto.MemberJoinRequest;
import miniproject.stellanex.dto.MemberLoginRequest;
import miniproject.stellanex.jwt.JwtResponse;
import miniproject.stellanex.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
public class MemberController {

    private final MemberService memberService;

    //     회원 가입
    @PostMapping("/member/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinRequest dto) {
        memberService.join(dto.getEmail(), dto.getPassword(), dto.getName(), dto.getBirth());
        JoinSuccessResponse response = JoinSuccessResponse.toDto();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 로그인 - access token 발급
    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequest dto) {
        JwtResponse token = memberService.login(dto.getEmail(), dto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccessToken());

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }
}
