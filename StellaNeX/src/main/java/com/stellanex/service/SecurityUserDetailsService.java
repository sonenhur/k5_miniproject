package com.stellanex.service;

import com.stellanex.domain.Member;
import com.stellanex.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memRepo;

    @Override   // AuthenticationManager의 authenticate 메소드가 호출되면 실행
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found"));
        return new User(member.getUsername(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }
}
