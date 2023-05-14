package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany //item과 category 다대다
    @JoinTable(name = "category_item",
            //관계형 DB는 컬랙션관계를 양쪽에 가질 수 없기 때문에 일대다 다대일로 풀어내는 중간테이블이 있어야 가능
            joinColumns = @JoinColumn(name = "category_id"),
            //중간테이블에 있는 category_id
            inverseJoinColumns = @JoinColumn(name = "item_id"))
            //category_item테이블에 아이템쪽으로 들어가는
    private List<Item> items = new ArrayList<>();
    @ManyToOne(fetch = LAZY)//이름만 내꺼고 다른 엔티티를 맵핑하는 거랑 똑같은 식으로 연관관계 형성
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {//부모에도 들어가고 자식에도 들어가야함
        this.child.add(child);
        child.setParent(this);//자식에서도 부모가 누군지
    }
}
