package release.release_proj.repository;

import org.springframework.stereotype.Repository;
import release.release_proj.domain.Item;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaItemRepository implements ItemRepository {

    private final EntityManager em;
    // JPA: entitymanager라는 것으로 모든 것이 동작함
    // build.gradle에서 data jpa를 받으면 spring boot가 자동으로 entitymanager를 생성해줌(현재 database와 연결해서)
    // 만들어진 것을 injection 받으면 됨

    public JpaItemRepository(EntityManager em) {
        this.em = em;
    }

    public Item save(Item item) {
        em.persist(item);
        return item;
    };

    /*public Item update(Item item) { //save와 합침
        return em.merge(item);
    };*/

    /*public void delete(Item item) {
        em.remove(item);
    };*/

    public void deleteById(Long id) {
        Item item = em.find(Item.class, id);
        if (item != null) {
            em.remove(item);
        }
    };

    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        //조회할 type, 식별자인 pk
        return Optional.ofNullable(item);
    };

    public Optional<Item> findByName(String name) {
        List<Item> result = em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    };

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
        // jpql: table이 아니라 객체(entity)를 대상으로 query를 날리는 것 => 이게 sql로 번역이 됨
        // 여기서는 객체 entity인 i 자체를 select 함
    };
}
