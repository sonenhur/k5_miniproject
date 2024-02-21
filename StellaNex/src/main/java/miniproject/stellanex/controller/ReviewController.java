package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.domain.Member;
import miniproject.stellanex.dto.ReviewInfoResponse;
import miniproject.stellanex.dto.ReviewRequest;
import miniproject.stellanex.persistence.MemberRepository;
import miniproject.stellanex.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    // 게시글 작성
    @PostMapping("/movie/review")
    public ResponseEntity<?> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReviewRequest dto) {
        String email = userDetails.getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Member Not Found"));
        reviewService.save(member.getEmail(), dto.getMovieId(), dto.getGrade(), dto.getContent());
        return ResponseEntity.ok("Post Success!");
    }

    // 게시글 조회
    @GetMapping("/movie/review/{reviewId}")
    public ResponseEntity<?> getReview(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("reviewId") Long id) {
        String email = userDetails.getUsername();
        ReviewInfoResponse review = reviewService.getReview(email, id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/movie/review/{reviewId}")
    public ResponseEntity<?> edit(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequest dto) {
        String email = userDetails.getUsername();
        reviewService.edit(email, reviewId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/movie/review/{reviewId}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("reviewId") Long reviewId) {
        String email = userDetails.getUsername();
        reviewService.delete(email, reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}