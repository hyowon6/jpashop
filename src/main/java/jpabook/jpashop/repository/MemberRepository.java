package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//컨포넌트 스캔에서 자동으로 빈 생성
@RequiredArgsConstructor
public class MemberRepository {
    // @PersistenceContext JPA 스프링 엔티티 매니저를 주입
    private final EntityManager em;

    public void save(Member member) { em.persist(member);}
    //member 객체를 넣으면 나중에 트랙젝션이 커밋되는 시점에 DB 반영

    public Member findOne(Long id) {
        return em.find(Member.class, id);//member를 찾아서 반환
    }

    //리스트 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)//JPQL, 엔티티 객체를 대상으로 쿼리
                .getResultList();
    }

    //이름으로 회원 검색
    public List<Member> findByName(String name) {//파라미터를 name으로 넘김
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                //JPQL, 파라미터를 바인딩하는 것, 조회 타입 Memer.class
                .setParameter("name", name)//
                .getResultList();
    }





}
