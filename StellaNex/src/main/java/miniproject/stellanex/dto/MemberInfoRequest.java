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
    // 추가적인 필드가 있다면 여기에 추가합니다.
}