package miniproject.stellanex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessResponse {
    private int status;
    private String message;

    public static LoginSuccessResponse toDto() {
        return LoginSuccessResponse.builder()
                .status(200)
                .message("로그인 성공!")
                .build();
    }
}
