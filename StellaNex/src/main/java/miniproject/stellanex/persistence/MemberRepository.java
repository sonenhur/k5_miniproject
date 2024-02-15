package miniproject.stellanex.persistence;

import miniproject.stellanex.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Object> findByEmail(String email);

    Optional<Object> findByName(String name);
}
