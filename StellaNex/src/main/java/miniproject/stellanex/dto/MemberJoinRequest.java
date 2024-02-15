package miniproject.stellanex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberJoinRequest {
    private String email;
    private String password;
    private String name;
    private String birth;
}
