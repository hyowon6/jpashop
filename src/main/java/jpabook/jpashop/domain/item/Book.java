package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")//싱글테이블이기에 DB에서 구분할 수 있어야함
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;

}
