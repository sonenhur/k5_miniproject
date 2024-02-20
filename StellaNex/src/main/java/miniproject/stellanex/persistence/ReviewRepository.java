package miniproject.stellanex.persistence;

import miniproject.stellanex.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
