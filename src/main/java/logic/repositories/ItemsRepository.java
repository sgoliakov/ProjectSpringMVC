package logic.repositories;

import logic.models.Item;
import logic.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByName(String itemsName);

    List<Item> findAllByOwner(Person owner);
}
