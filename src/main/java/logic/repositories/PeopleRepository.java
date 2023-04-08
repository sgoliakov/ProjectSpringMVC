package logic.repositories;

import logic.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findAllByName(String name);

    List<Person> findAllByNameOrderByAge(String name);

    List<Person> findAllByMail(String mail);

    List<Person> findByNameStartingWith(String startingWith);

    List<Person> findByNameOrMail(String name, String mail);
}
