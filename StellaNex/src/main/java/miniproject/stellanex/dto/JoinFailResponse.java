package miniproject.stellanex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinFailResponse {
    private int status;
    private String message;

    public static JoinFailResponse toDto() {
        return JoinFailResponse.builder()
                .status(400)
                .message("Failed!")
                .build();
    }
}
