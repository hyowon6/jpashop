package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")//PK
    private Long id;
    private String name;
    @Embedded//내장 타입
    private Address address;
    @OneToMany(mappedBy = "member")
    //하나의 회원이 여러개의 상품 주문 일대다관계, 연관관계에서 주인이 아님 매핑되는 거울 일뿐
    private List<Order> orders = new ArrayList<>();


}