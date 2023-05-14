package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
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


}
