package logic.DAO;

import logic.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PeopleJdbcTemplateDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleJdbcTemplateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("select * from person", new PersonMapper());
    }

    public Person getPerson(int id) {
        List<Person> list = jdbcTemplate.query("select * from person where id=?",
                new BeanPropertyRowMapper<>(Person.class), id);
        return list.stream().findAny().orElse(null);
    }

    public Optional<Person> getPerson(String mail) {
        List<Person> list = jdbcTemplate.query("select * from person where mail=?",
                new BeanPropertyRowMapper<>(Person.class), mail);
        return list.stream().findAny();
    }

    public void save(Person p) {
        String sql = "insert into person (name, age, mail, address) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, p.getName(), p.getAge(), p.getMail(), p.getAddress());
    }

    public void update(Person p, int id) {
        String sql = "update person set name = ?, age = ?, mail = ?, address = ? where id = ?";
        jdbcTemplate.update(sql, p.getName(), p.getAge(), p.getMail(), p.getAddress(), id);
    }

    public void delete(int id) {
        String sql = "delete from person where id = ?";
        jdbcTemplate.update(sql, id);
    }

    //performance test Batch update
    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for (Person p : people) {
            save(p);
        }
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    public void withBatchUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("insert into person (name, age, mail, address) VALUES (?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, people.get(i).getName());
                        ps.setInt(2, people.get(i).getAge());
                        ps.setString(3, people.get(i).getMail());
                        ps.setString(4, people.get(i).getAddress());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Person person = new Person();
            person.setName("Name" + i);
            person.setAge(30);
            person.setMail("some.mail" + i + "@mail.com");
            person.setAddress("Country, City, 123456");
            list.add(person);
        }
        return list;
    }
}
