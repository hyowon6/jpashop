//package jpabook.jpashop.service;
//
//import jpabook.jpashop.domain.Member;
//import jpabook.jpashop.repository.MemberRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.Assert.*;
//
////@RunWith(SpringRunner.class)//junit spring이랑 엮어서 실행할때
////Junit4일때 사용하는데 현재 스프링 부트의 경우 JUnit5를 기본으로 사용해서 해당 코드를 제거해야 동작
//@SpringBootTest//springboot를 띄운 상태로 테스트 스프링 컨테이너안에서 테스트를 돌리는 것
//@Transactional//데이터를 변경해야하기 때문, 테스트가 끝나면 트랜잭션을 강제로 롤백
//public class MemberServiceTest {
//
//    @Autowired MemberService memberService;
//    @Autowired MemberRepository memberRepository;
//
//    @Test
////    @Rollback(false)//insert문이 나가는 것을 확인
//    public void 회원가입() throws Exception {
//        //given
//        Member member = new Member();
//        member.setName("kim");
//
//        //when
//        Long saveId = memberService.join(member);
//
//        //then
//        assertEquals(member, memberRepository.findOne(saveId));
//        //찾아온 member랑 저장한 member랑 똑같은게 나와야함
//    }
//
//    @Test//(expected = IllegalStateException.class) 오류
//    public void 중복_회원_예외() throws Exception {
//        //given
//        Member member1 = new Member();
//        member1.setName("kim");
//
//        Member member2 = new Member();
//        member2.setName("kim");
//
//        //when
//        memberService.join(member1);
//        memberService.join(member2); //예외가 발생해야 한다.
//
//        //then
//        fail("예외가 발생해야 한다.");//밑으로 로직이 나갈 경우 대비
//    }
//}