package miniproject.stellanex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.text.DecimalFormat;

@AllArgsConstructor
@Builder
@Getter
public class ReviewRequest {
    private DecimalFormat grade;
    private String content;
}
