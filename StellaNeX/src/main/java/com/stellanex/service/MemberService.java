package com.stellanex.service;

import com.stellanex.domain.Member;
import com.stellanex.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepo;

    public void save(Member member) {
        memberRepo.save(member);
    }
}
