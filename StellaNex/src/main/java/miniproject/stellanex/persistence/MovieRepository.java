package miniproject.stellanex.persistence;

import miniproject.stellanex.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // 전체 영화 목록 조회 메서드

    List<Movie> findAll();

}
