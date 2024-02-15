package miniproject.stellanex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberLoginRequest {
    private String userId;
    private String password;
}
