package miniproject.stellanex.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movie_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate release_date;

    @Column(nullable = false)
    private String running_time;

    @Column(nullable = false)
    private String age_rating;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String synopsis;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String casts;
}
