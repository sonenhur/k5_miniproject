package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.domain.Member;
import miniproject.stellanex.domain.Movie;
import miniproject.stellanex.dto.ReviewInfoResponse;
import miniproject.stellanex.dto.ReviewRequest;
import miniproject.stellanex.exception.UnauthorizedException;
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

    @PostMapping("/movie/review")
    public ResponseEntity<Void> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReviewRequest dto) {
        try {
            String email = userDetails.getUsername();
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
            reviewService.save(member.getEmail(), dto.getMovieId(), dto.getGrade(), dto.getContent());
            log.info("사용자 {}가 리뷰를 성공적으로 저장했습니다.", email);
            return ResponseEntity.ok().build();
        } catch (UsernameNotFoundException e) {
            log.error("회원을 찾을 수 없습니다: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("리뷰 저장 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/movie/review/{movieId}")
    public ResponseEntity<?> getAllReviewsByMovieId(@PathVariable Long movieId, @RequestParam(required = false) String ordertype, @RequestParam(required = false) String order) {
        try {
            log.info("{} 번 영화의 리뷰를 가져오는 중입니다.", movieId);
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new NoSuchElementException("해당 ID의 영화를 찾을 수 없습니다."));
            List<ReviewInfoResponse> reviews;

            // 기본적으로는 오름차순으로 정렬
            if (ordertype == null || order == null) {
                reviews = reviewService.getAllReviewsByMovieId(movieId);
            } else {
                // ordertype 및 order에 따라 정렬 방식을 결정
                if ("grade".equals(ordertype)) {
                    // rating을 기준으로 정렬
                    if ("asc".equals(order)) {
                        reviews = reviewService.getAllReviewsByMovieIdOrderByRatingAsc(movieId);
                    } else {
                        reviews = reviewService.getAllReviewsByMovieIdOrderByRatingDesc(movieId);
                    }
                } else if ("date".equals(ordertype)) {
                    // date를 기준으로 정렬
                    if ("asc".equals(order)) {
                        reviews = reviewService.getAllReviewsByMovieIdOrderByDateAsc(movieId);
                    } else {
                        reviews = reviewService.getAllReviewsByMovieIdOrderByDateDesc(movieId);
                    }
                } else {
                    // 유효하지 않은 정렬 기준이면 기본적으로 오름차순으로 정렬
                    reviews = reviewService.getAllReviewsByMovieId(movieId);
                }
            }

            return ResponseEntity.ok(reviews);
        } catch (NoSuchElementException e) {
            log.error("영화를 찾을 수 없습니다: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("리뷰 조회 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/movie/review/{reviewId}")
    public ResponseEntity<Void> edit(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long reviewId, @RequestBody ReviewRequest dto) {
        try {
            String email = userDetails.getUsername();
            reviewService.edit(email, reviewId, dto);
            log.info("사용자 {}가 ID {}의 리뷰를 성공적으로 수정했습니다.", email, reviewId);
            return ResponseEntity.ok().build();
        } catch (UnauthorizedException e) {
            log.error("리뷰 수정 권한이 없습니다: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            log.error("리뷰 수정 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/movie/review/{reviewId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long reviewId) {
        try {
            String email = userDetails.getUsername();
            reviewService.delete(email, reviewId);
            log.info("사용자 {}가 ID {}의 리뷰를 성공적으로 삭제했습니다.", email, reviewId);
            return ResponseEntity.ok().build();
        } catch (UnauthorizedException e) {
            log.error("리뷰 삭제 권한이 없습니다: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            log.error("리뷰 삭제 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}