package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable//JPA의 내장타입
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {//public 많이 호출하기 때문에
    }
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }//값 타입은 변경이 되면 안된다 생성할 때만 값이 세팅이 되고 setter을 안 함-> 변경이 불가능해짐
     //@Setter 를 제거하고, 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스
}
