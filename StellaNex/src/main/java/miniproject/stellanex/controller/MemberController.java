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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            return ResponseEntity.ok().header("Authorization", "Bearer " + token.getAccessToken()).body("success!");
        } catch (Exception e) {
            log.error("로그인 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("failed!");
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
            memberService.updatePassword(email, dto.getPassword());
            log.info("사용자 {} 비밀번호 변경", email);
            return ResponseEntity.ok().body(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
        } catch (Exception e) {
            log.error("사용자 비밀번호 변경 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "비밀번호 변경에 실패하였습니다."));
        }
    }

    @Transactional
    @DeleteMapping("/member/delete")
    public ResponseEntity<?> deleteMember(@AuthenticationPrincipal User user) {
        try {
            String email = user.getUsername();
            // REVIEW 테이블에서 회원의 모든 리뷰 삭제
//        reviewService.deleteReviewsByEmail(email);
            // MEMBER 테이블에서 회원 삭제
            memberService.deleteMemberByEmail(email);
            log.info("사용자 {} 정보 삭제", email);
            return ResponseEntity.ok("delete success");
        } catch (Exception e) {
            log.error("회원 정보 삭제 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}