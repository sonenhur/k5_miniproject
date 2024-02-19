package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.domain.Member;
import miniproject.stellanex.dto.ReviewInfoResponse;
import miniproject.stellanex.dto.ReviewRequest;
import miniproject.stellanex.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    // 게시글 작성
    @PostMapping("/member/review")
    public ResponseEntity<?> save(@AuthenticationPrincipal Member member, @RequestBody ReviewRequest dto) {
        String email = member.getEmail();
        reviewService.save(email, dto.getGrade(),dto.getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 게시글 조회
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> getReview(@AuthenticationPrincipal Member member, @PathVariable("reviewId") Long id) {
        String email = "";
        if (member != null) {
            email = member.getEmail();
        }
        ReviewInfoResponse review = reviewService.getReview(email, id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/member/review/{reviewId}")
    public ResponseEntity<?> edit(@AuthenticationPrincipal Member member, @PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequest dto) {
        String email = member.getEmail();
        reviewService.edit(email, reviewId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/member/review/{reviewId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal Member member, @PathVariable("reviewId") Long reviewId) {
        String email = member.getEmail();
        reviewService.delete(email, reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}