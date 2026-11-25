package com.example.demo.model.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Enrollment;
import com.example.demo.model.domain.Member;
import com.example.demo.model.domain.Post;
import com.example.demo.model.dto.SeatMessage;
import com.example.demo.model.repository.EnrollmentRepository;
import com.example.demo.model.repository.MemberRepository;
import com.example.demo.model.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.example.demo.model.dto.Notification;

@Service // [11주차] 단계4 : 신청서비스
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired // [12주차] 실시간메시지
    private ApplicationEventPublisher eventPublisher; // [12주차] 단계3 : 이벤트발행

    public List<Enrollment> findMine(String username) {
        return enrollmentRepository.findByMember_UsernameOrderByIdDesc(username);
    }

    @Transactional // 단계5 : 하나라도실패하면모두취소(롤백)
    // 단계4 : 바구니의모집글을한번에신청확정
    public int confirm(String username, Collection<Long> postIds) {
        if (postIds.isEmpty()) {
            throw new IllegalStateException("바구니가비어있습니다.");
        }
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("회원이존재하지않습니다."));
        for (Long postId : postIds) {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalStateException("삭제된모집글이바구니에있습니다."));
            if (enrollmentRepository.existsByMemberAndPost(member, post)) {
                throw new IllegalStateException("이미 신청한 모집글입니다: " + post.getTitle());
            }
            if (post.isFull()) { // 담을때는자리가있었지만, 그사이마감됐을수있다
                throw new IllegalStateException("정원이 마감 되었습니다: " + post.getTitle());
            }
            Enrollment enrollment = new Enrollment();
            enrollment.setMember(member);
            enrollment.setPost(post);
            enrollment.setCreatedAt(LocalDateTime.now());
            enrollmentRepository.save(enrollment); // ①INSERT 신청내역
            post.setAppliedCount(post.getAppliedCount() + 1);
            postRepository.save(post); // ②UPDATE 신청인원+ 1 (남은자리-1)
            eventPublisher.publishEvent(SeatMessage.from(post)); // [12주차] 단계3 : 이벤트만던진다(커밋되면RealtimeNotifier 가보낸다)
            String writer = post.getAuthor().getUsername();
            if (!writer.equals(username)) { // [12주차] 단계4 : 모집글쓴사람에게알림(내글에내가신청하면생략)
                eventPublisher.publishEvent(new Notification(writer,
                        member.getName() + "님이'" + post.getTitle() + "' 에신청했습니다. ("
                                + post.getAppliedCount() + "/" + post.getCapacity() + ")",
                        post.getId()));
            }
        }
        return postIds.size();
    }

        @Transactional // [연습문제 1] 신청 내역 삭제 + 신청 인원 -1 을 한 트랜잭션으로
    public void cancel(Long enrollmentId, String username) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "신청 내역이 없습니다."));
        if (!enrollment.getMember().getUsername().equals(username)) { // 본인 신청만 취소
            throw new AccessDeniedException("본인 신청만 취소할 수 있습니다."); // → 403
        }
        Post post = enrollment.getPost();
        post.setAppliedCount(post.getAppliedCount() - 1); // save() 없이 변경 감지로 UPDATE
        enrollmentRepository.delete(enrollment); // DELETE 신청 내역
        eventPublisher.publishEvent(SeatMessage.from(post)); // [12주차 연습 1] 남은 자리 다시 늘어난 것을 모두에게
        eventPublisher.publishEvent(new Notification(post.getAuthor().getUsername(), // [12주차 연습 1] 모집글 쓴 사람에게 취소 알림
                enrollment.getMember().getName() + "님이 '" + post.getTitle() + "' 신청을 취소했습니다.", post.getId()));
    }
}
