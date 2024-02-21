package miniproject.stellanex.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoRequest {
    private String name;
    private String birth;
    private String password;
    private String email;
}