package jpabook.jpashop.repository;


import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {// item은 처음에 데이터를 저장할때는 id가 없음
        if (item.getId() == null) {// id가 없으면 신규로 보고
            em.persist(item);// 그래서 jpa가 제공하는 persist 사용
        } else {// id가 있으면 이미 DB에 저장된 엔티티를 수정한다고 보고
            Item merge = em.merge(item);// 강제로 업데이트
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
