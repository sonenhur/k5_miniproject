package miniproject.stellanex.service;

import lombok.RequiredArgsConstructor;
import miniproject.stellanex.domain.Review;
import miniproject.stellanex.domain.Member;
import miniproject.stellanex.dto.ReviewInfoResponse;
import miniproject.stellanex.dto.ReviewRequest;
import miniproject.stellanex.persistence.ReviewRepository;
import miniproject.stellanex.persistence.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public void save(String email, DecimalFormat grade, String content) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Review review = Review.builder()
                .content(content)
                .writer(member)
                .build();

        reviewRepository.save(review);
    }

    @Transactional
    public ReviewInfoResponse getReview(String email, Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 게시물입니다."));

        Member writer = review.getWriter();

        ReviewInfoResponse info = ReviewInfoResponse.builder()
                .content(review.getContent())
                .writer(writer.getEmail())
                .build();

        return info;
    }

    public void edit(String email, Long reviewId, ReviewRequest dto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다"));
        reviewRepository.findById(reviewId)
                .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다"));
        delete(email, reviewId);

        Review review = Review.builder()
                .id(member.getId())
                .content(dto.getContent())
                .writer(member)
                .build();
        reviewRepository.save(review);
    }

    public void delete(String email, Long reviewId) {
        memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다"));
        memberRepository.findById(reviewId)
                .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다"));
        reviewRepository.deleteById(reviewId);
    }


}