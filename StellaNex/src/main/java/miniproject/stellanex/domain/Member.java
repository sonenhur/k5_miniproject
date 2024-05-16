package miniproject.stellanex.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String birth;

	@Builder.Default
	private LocalDateTime registered = LocalDateTime.now();

	@OneToMany(mappedBy = "writer", fetch = FetchType.LAZY)
	private List<Review> reviewList = new ArrayList<>();

	@Column
	@Builder.Default
	private boolean isDeleted = false;
}
