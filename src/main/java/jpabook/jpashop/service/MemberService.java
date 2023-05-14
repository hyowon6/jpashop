package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//읽기에 readOnly=true 영속성 컨텍스트를 플러시 하지 않으므로 성능을 최적화
@RequiredArgsConstructor // final에 있는 필드만 가지고 생성자를 만들어줌
public class MemberService {

    //@Autowired 생성자 Injection 많이 사용, 생성자가 하나면 생략 가능
    private final MemberRepository memberRepository;//변경을 안 할 것이기에 final

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    @Transactional //변경
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);//회원 저장
        return member.getId();//아이디 반환
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());//유니크 제약 조건Q
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
            return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {//단건 조회
        return memberRepository.findOne(memberId);
    }
}
