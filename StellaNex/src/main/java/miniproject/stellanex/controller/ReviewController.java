package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.domain.Member;
import miniproject.stellanex.domain.Movie;
import miniproject.stellanex.dto.ReviewInfoResponse;
import miniproject.stellanex.dto.ReviewRequest;
import miniproject.stellanex.persistence.MemberRepository;
import miniproject.stellanex.persistence.MovieRepository;
import miniproject.stellanex.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;

    @PostMapping("/movie/review")
    public ResponseEntity<Void> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReviewRequest dto) {
        String email = userDetails.getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
        reviewService.save(member.getEmail(), dto.getMovieId(), dto.getGrade(), dto.getContent());
        log.info("사용자 {}가 리뷰를 성공적으로 저장했습니다.", email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/movie/review/{movieId}")
    public ResponseEntity<?> getReviews(@PathVariable Long movieId) {
        log.info("{} 번 영화의 리뷰를 가져오는 중입니다.", movieId);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 영화를 찾을 수 없습니다."));
        List<ReviewInfoResponse> reviews = reviewService.getAllReviewsByMovieId(movieId);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/movie/review/{reviewId}")
    public ResponseEntity<Void> edit(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long reviewId, @RequestBody ReviewRequest dto) {
        String email = userDetails.getUsername();
        reviewService.edit(email, reviewId, dto);
        log.info("사용자 {}가 ID {}의 리뷰를 성공적으로 수정했습니다.", email, reviewId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/movie/review/{reviewId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long reviewId) {
        String email = userDetails.getUsername();
        reviewService.delete(email, reviewId);
        log.info("사용자 {}가 ID {}의 리뷰를 성공적으로 삭제했습니다.", email, reviewId);
        return ResponseEntity.ok().build();
    }
}