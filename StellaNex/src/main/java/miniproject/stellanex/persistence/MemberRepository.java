package miniproject.stellanex.persistence;

import miniproject.stellanex.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);

	// 삭제 메소드 삭제 또는 주석 처리
	// void deleteByEmail(String email);

	// 삭제 플래그 업데이트 메소드 추가
	@Modifying
	@Query("UPDATE Member m SET m.isDeleted = true WHERE m.email = :email")
	void softDeleteByEmail(String email);
}
