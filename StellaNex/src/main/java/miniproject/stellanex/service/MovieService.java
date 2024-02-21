package miniproject.stellanex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniproject.stellanex.domain.Movie;
import miniproject.stellanex.persistence.MovieRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;

    public void save(String title, LocalDate releaseDate, String runningTime, String age_rating, String genre, String synopsis, String director, String casts) {
        Movie movie = Movie.builder()
                .title(title)
                .release_date(releaseDate)
                .running_time(runningTime)
                .age_rating(age_rating)
                .genre(genre)
                .synopsis(synopsis)
                .director(director)
                .casts(casts)
                .build();
        movieRepository.save(movie);
    }
}
