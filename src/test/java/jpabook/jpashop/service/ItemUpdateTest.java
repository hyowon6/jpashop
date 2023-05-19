package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        //TX 트랜잭션 안에서 이름을 변경
        book.setName("asdgasdf");

        //변경감지 == dirty checking
        //order 클래스에서 this.setStatus(OrderStatus.CANCEL);
        //TX commit 이 되면 JPA가 변경분을 찾아서 업데이트해서 DB에 자동으로 반영 = 변경감지
    }
}
