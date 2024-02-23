package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.dto.*;
import miniproject.stellanex.jwt.JwtResponse;
import miniproject.stellanex.service.MemberService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> join(@RequestBody MemberJoinRequest dto) {
        try {
            memberService.join(dto.getEmail(), dto.getPassword(), dto.getName(), dto.getBirth());
            log.info("사용자 {} 가입 성공", dto.getEmail());

            JoinSuccessResponse response = JoinSuccessResponse.toDto();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("가입 실패: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequest dto) {
        try {
            JwtResponse token = memberService.login(dto.getEmail(), dto.getPassword());
            log.info("사용자 {} 로그인 성공", dto.getEmail());
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token.getAccessToken())
                    .body("success!");
        } catch (Exception e) {
            log.error("로그인 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("failed!");
        }
    }


    @GetMapping("/member/mypage")
    public ResponseEntity<MemberInfoResponse> getInfo(@AuthenticationPrincipal User user) {
        try {
            String email = user.getUsername();
            MemberInfoResponse info = memberService.getInfo(email);
            log.info("사용자 {} 정보 조회", email);
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            log.error("사용자 정보 조회 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/member/update")
    public ResponseEntity<?> updateInfo(@AuthenticationPrincipal User user, @RequestBody MemberInfoRequest dto) {
        try {
            String email = user.getUsername();
            memberService.updateInfo(email, dto);
            log.info("사용자 {} 정보 수정", email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("사용자 정보 수정 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/member/delete")
    public ResponseEntity<?> deleteInfo(@AuthenticationPrincipal User user) {
        try {
            String email = user.getUsername();
            memberService.deleteInfo(email);
            log.info("사용자 {} 정보 삭제", email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("사용자 정보 삭제 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}