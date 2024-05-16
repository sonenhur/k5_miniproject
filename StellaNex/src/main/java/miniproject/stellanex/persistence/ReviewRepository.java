package miniproject.stellanex.persistence;

import miniproject.stellanex.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long> {
	//	@Query("select r from Review r where r.movie.movie_id = :movieId")
	//	List<Review> findByMovieId(Long movieId);
	//
	//	@Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId ORDER BY r.grade ASC")
	//	List<Review> findByMovieIdOrderByGradeAsc(Long movieId);
	//
	//	@Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId ORDER BY r.grade DESC")
	//	List<Review> findByMovieIdOrderByGradeDesc(Long movieId);
	//
	//	@Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId ORDER BY r.date ASC")
	//	List<Review> findByMovieIdOrderByDateAsc(Long movieId);
	//
	//	@Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId ORDER BY r.date DESC")
	//	List<Review> findByMovieIdOrderByDateDesc(Long movieId);

	@Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId AND r.isDeleted = false")
	List<Review> findActiveByMovieId(Long movieId);

	@Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId AND r.isDeleted = false ORDER BY r.grade ASC")
	List<Review> findActiveByMovieIdOrderByGradeAsc(Long movieId);

	@Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId AND r.isDeleted = false ORDER BY r.grade DESC")
	List<Review> findActiveByMovieIdOrderByGradeDesc(Long movieId);

	@Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId AND r.isDeleted = false ORDER BY r.date ASC")
	List<Review> findActiveByMovieIdOrderByDateAsc(Long movieId);

	@Query("SELECT r FROM Review r WHERE r.movie.movie_id = :movieId AND r.isDeleted = false ORDER BY r.date DESC")
	List<Review> findActiveByMovieIdOrderByDateDesc(Long movieId);

	// 기존 삭제 메소드 삭제 또는 주석 처리
	// @Query("DELETE FROM Review r WHERE r.writer.email = :email")
	// void deleteByEmail(String email);

	// 삭제 플래그 업데이트 메소드 추가
	@Modifying
	@Query("UPDATE Review r SET r.isDeleted = true WHERE r.writer.email = :email")
	void softDeleteByEmail(String email);

}