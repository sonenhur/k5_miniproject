package miniproject.stellanex.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinFailResponse {

    private int status;
    private String message;

}
