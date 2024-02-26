package miniproject.stellanex.persistence;

import miniproject.stellanex.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.movie.movie_id = :movieId")
    List<Review> findByMovieId(Long movieId);

    @Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId ORDER BY r.grade ASC")
    List<Review> findByMovieIdOrderByGradeAsc(Long movieId);

    @Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId ORDER BY r.grade DESC")
    List<Review> findByMovieIdOrderByGradeDesc(Long movieId);

    @Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId ORDER BY r.date ASC")
    List<Review> findByMovieIdOrderByDateAsc(Long movieId);

    @Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId ORDER BY r.date DESC")
    List<Review> findByMovieIdOrderByDateDesc(Long movieId);

    @Query("DELETE FROM Review r WHERE r.writer.email = :email")
    void deleteByEmail(String email);
}