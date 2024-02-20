package miniproject.stellanex.service;

import lombok.RequiredArgsConstructor;
import miniproject.stellanex.domain.Member;
import miniproject.stellanex.domain.Review;
import miniproject.stellanex.dto.ReviewInfoResponse;
import miniproject.stellanex.dto.ReviewRequest;
import miniproject.stellanex.persistence.MemberRepository;
import miniproject.stellanex.persistence.ReviewRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public void save(String email, int grade, String content) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        if (member == null) {
            throw new IllegalStateException("Member 객체가 null입니다.");
        }

        Review review = Review.builder()
                .grade(grade)
                .content(content)
                .writer(member)
                .build();

        reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public ReviewInfoResponse getReview(String email, Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 게시물입니다."));

        return ReviewInfoResponse.builder()
                .content(review.getContent())
                .writer(review.getWriter().getEmail())
                .build();
    }

    @Transactional
    public void edit(String email, Long reviewId, ReviewRequest dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new FindException("존재하지 않는 게시물입니다"));

        // 리뷰의 내용을 업데이트
        review.updateContent(dto.getContent());
    }


    @Transactional
    public void delete(String email, Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
