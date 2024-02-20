package miniproject.stellanex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.dto.MovieInputRequest;
import miniproject.stellanex.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MovieController {
    private final MovieService movieService;

    // 게시글 작성
    @PostMapping("/movie/post")
    public ResponseEntity<?> save(@RequestBody MovieInputRequest dto) {
        movieService.save(dto.getTitle(), dto.getRelease_date(), dto.getRunning_time(), dto.getAge_rating(), dto.getGenre(), dto.getSynopsis(), dto.getDirector(), dto.getCasts());
        return ResponseEntity.ok("posting success!");
    }
}
