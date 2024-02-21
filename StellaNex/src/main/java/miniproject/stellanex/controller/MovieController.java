package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.domain.Movie;
import miniproject.stellanex.dto.MovieInputRequest;
import miniproject.stellanex.persistence.MovieRepository;
import miniproject.stellanex.service.MovieService;
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
    public ResponseEntity<String> save(@RequestBody MovieInputRequest dto) {
        movieService.save(dto.getTitle(), dto.getRelease_date(), dto.getRunning_time(), dto.getAge_rating(), dto.getGenre(), dto.getSynopsis(), dto.getDirector(), dto.getCasts());
        log.info("새 영화 정보가 성공적으로 등록되었습니다: {}", dto.getTitle());
        return ResponseEntity.ok("게시 성공!");
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Movie> getInfo(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long movieId) {
        log.info("영화 정보 조회: {}", movieId);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 영화를 찾을 수 없습니다."));
        return ResponseEntity.ok(movie);
    }
}