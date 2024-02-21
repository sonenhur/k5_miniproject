package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.dto.*;
import miniproject.stellanex.jwt.JwtResponse;
import miniproject.stellanex.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/join")
    public ResponseEntity<JoinSuccessResponse> join(@RequestBody MemberJoinRequest dto) {
        memberService.join(dto.getEmail(), dto.getPassword(), dto.getName(), dto.getBirth());
        JoinSuccessResponse joinSuccessResponse = JoinSuccessResponse.toDto();
        log.info("사용자 {} 가입 성공", dto.getEmail());
        return ResponseEntity.ok(joinSuccessResponse);
    }

    @PostMapping("/member/login")
    public ResponseEntity<Void> login(@RequestBody MemberLoginRequest dto) {
        JwtResponse token = memberService.login(dto.getEmail(), dto.getPassword());
        log.info("사용자 {} 로그인 성공", dto.getEmail());
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token.getAccessToken())
                .build();
    }

    @GetMapping("/member/mypage")
    public ResponseEntity<MemberInfoResponse> getInfo(@AuthenticationPrincipal User user) {
        String email = user.getUsername();
        MemberInfoResponse info = memberService.getInfo(email);
        log.info("사용자 {} 정보 조회", email);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/member/update")
    public ResponseEntity<Void> updateInfo(@AuthenticationPrincipal User user, @RequestBody MemberInfoRequest dto) {
        String email = user.getUsername();
        memberService.updateInfo(email, dto);
        log.info("사용자 {} 정보 수정", email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/member/delete")
    public ResponseEntity<Void> deleteInfo(@AuthenticationPrincipal User user) {
        String email = user.getUsername();
        memberService.deleteInfo(email);
        log.info("사용자 {} 정보 삭제", email);
        return ResponseEntity.ok().build();
    }
}