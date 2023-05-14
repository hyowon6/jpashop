package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded //내장타입
    private Address address;

    @Enumerated(EnumType.STRING)//ORDINAL:다른 값 끼면 안됨, 순서에 밀리는 것이 없음
    private DeliveryStatus status; //[READY(준비), COMP(배송)]
}
