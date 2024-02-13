package com.stellanex;

import com.stellanex.domain.Member;
import com.stellanex.domain.Role;
import com.stellanex.persistence.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberInitialize {
    PasswordEncoder encoder = new BCryptPasswordEncoder(); // 비밀번호 암호화 인터페이스/구현체
    @Autowired
    private MemberRepository memRepo;

    @Test
    public void memberInitialize() {
        memRepo.save(Member.builder()
                .username("member")
                .password(encoder.encode("abcd"))
                .role(Role.ROLE_MEMBER)
                .enabled(true)
                .build());

        memRepo.save(Member.builder()
                .username("manager")
                .password(encoder.encode("abcd"))
                .role(Role.ROLE_MANAGER)
                .enabled(true).build());

        memRepo.save(Member.builder()
                .username("admin")
                .password(encoder.encode("abcd"))
                .role(Role.ROLE_ADMIN)
                .enabled(true).build());
    }
}
