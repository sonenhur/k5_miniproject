package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.domain.Movie;
import miniproject.stellanex.dto.MovieInputRequest;
import miniproject.stellanex.persistence.MovieRepository;
import miniproject.stellanex.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;


    @PostMapping("/movie/post")
    public ResponseEntity<?> save(@RequestBody MovieInputRequest dto) {
        try {
            movieService.save(dto.getTitle(), dto.getRelease_date(), dto.getRunning_time(), dto.getAge_rating(), dto.getGenre(), dto.getSynopsis(), dto.getDirector(), dto.getCasts());
            log.info("새 영화 정보가 성공적으로 등록되었습니다: {}", dto.getTitle());
            return ResponseEntity.ok("게시 성공!");
        } catch (Exception e) {
            log.error("영화 정보 게시 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Post Failed: " + e.getMessage());
        }
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long movieId) {
        try {
            log.info("영화 정보 조회: {}", movieId);
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new NoSuchElementException("Movie ID Not Found"));
            return ResponseEntity.ok(movie);
        } catch (NoSuchElementException e) {
            log.error("영화 정보 조회 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie Not Found");
        } catch (Exception e) {
            log.error("영화 정보 조회 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회 실패: " + e.getMessage());
        }
    }

}