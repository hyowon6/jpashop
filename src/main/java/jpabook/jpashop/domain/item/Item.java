package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy =  InheritanceType.SINGLE_TABLE)//SINGLE_TABLE 한 테이블에 다 넣어줌
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {//추상클래스 구현체를 가지고 할 것이기 때문에
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    //stock 증가, 재고 수량을 증가하는 로직
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    //stock 감소
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        //파라미터로 넘어온 수량 값으로 남은 수량 계산
        if(restStock < 0){//남은 수량이 0보다 작으면
            throw new NotEnoughStockException("need more stock");//예외발생
        }
        this.stockQuantity = restStock;//현재 stockQuantity는 남은 수량
    }


}
