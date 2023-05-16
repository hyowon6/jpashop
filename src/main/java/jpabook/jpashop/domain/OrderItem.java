package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)//orderitem은 하나의 order만 가질 수 있음
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량


    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);//넘어온 것만큼 item의 재고를 없애줌
        return orderItem;

    }
    //==비즈니스 로직==//
    //주문취소
    public void cancel() {
        getItem().addStock(count);
        //item을 가져온 다음 addStock에서 재고를 다시 주문수량만큼 늘려줘야함
    }

    //==조회 로직==/
    //전체상품 전체 가격 조회
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
        //주문 가격과 주문 수량을 곱해야 함
    }
}
