package miniproject.stellanex.dto;

import lombok.*;
import miniproject.stellanex.domain.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoResponse {

    private String email;
    private String name;

    public static MemberInfoResponse dto(Member member){
        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
