package miniproject.stellanex.persistence;

import miniproject.stellanex.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  @Query("select r from Review r where r.movie.movie_id = :movieId")
    List<Review> findByMovieId(Long movieId);
}