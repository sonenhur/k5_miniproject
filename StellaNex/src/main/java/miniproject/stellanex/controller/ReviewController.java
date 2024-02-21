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
import org.springframework.http.HttpStatus;
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

    // 리뷰 작성
    @PostMapping("/movie/review")
    public ResponseEntity<?> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReviewRequest dto) {
        String email = userDetails.getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Member Not Found"));
        reviewService.save(member.getEmail(), dto.getMovieId(), dto.getGrade(), dto.getContent());
        return ResponseEntity.ok("Post Success!");
    }

//    // 리뷰 조회
//    @GetMapping("/movie/review/{movieId}")
//    public ResponseEntity<?> getReview(@PathVariable Long movieId) {
//        Movie movie = movieRepository.findById(movieId)
//                .orElseThrow(() -> new NoSuchElementException("해당 ID의 영화를 찾을 수 없습니다."));
//        ReviewInfoResponse review = reviewService.getReview(movieId);
//        return new ResponseEntity<>(review, HttpStatus.OK);
//    }

    // 리뷰 조회
    @GetMapping("/movie/review/{movieId}")
    public ResponseEntity<?> getReviews(@PathVariable Long movieId) {
        System.out.println("get reviews");
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 영화를 찾을 수 없습니다."));
        List<ReviewInfoResponse> reviews = reviewService.getAllReviewsByMovieId(movieId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);

    }


    // 리뷰 수정
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