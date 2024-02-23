package miniproject.stellanex.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false, length = 30)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }
}