package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)//다대일관계
    @JoinColumn(name = "member_id")//FK(외래키)있는 곳 연관관계 주인 name이 member_id
    private Member member; //주문 회원
    //멤버 하나에 여러 개 오더

    @OneToMany(mappedBy = "order",  cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")//외래키 지정
    private Delivery delivery; //배송정보
    //order_date
    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)//순서에 밀리는 것이 없음
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
        //this 자기자신, 멤버가 주문을 했다면 지정을 해주고 이 멤버가 주문을 했다고 리스트 뒤에 넣어주는 것 연관관계 메서드
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
