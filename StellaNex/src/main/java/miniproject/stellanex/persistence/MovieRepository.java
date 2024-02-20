package miniproject.stellanex.persistence;

import miniproject.stellanex.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
