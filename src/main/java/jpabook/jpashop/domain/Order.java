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

    //==생성 메서드==//
    //주문 생성관련 변경할 때 createOrder만 변경해주면 됨
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {//... 여러개를 넘길 때 사용
        Order order = new Order();
        order.setMember(member);//파라미터로 넘어온 member 세팅
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);//생성한 order에다가 orderItem을 넣어줌
        }
        order.setStatus(OrderStatus.ORDER);//처음상태를 ORDER로
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    //주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {//배송이 완료된 상태면
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);//order의 상태를 cancel로 바꿔줌
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    //전체 주문 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
            //orderItem에 있는 getTotalPrice를 가져옴
        }
        return totalPrice;
    }
}
