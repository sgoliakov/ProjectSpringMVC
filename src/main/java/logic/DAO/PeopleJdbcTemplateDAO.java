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

    public Person getPersonById(int id) {
        List<Person> list = jdbcTemplate.query("select * from person where id=?",
                new BeanPropertyRowMapper<>(Person.class), id);
        return list.stream().findAny().orElse(null);
    }

    public void save(Person p) {
        String sql = "insert into person (name, age, mail) VALUES (?,?,?)";
        jdbcTemplate.update(sql, p.getName(), p.getAge(), p.getMail());
    }

    public void update(Person p, int id) {
        String sql = "update person set name = ?, age = ?, mail = ? where id = ?";
        jdbcTemplate.update(sql, p.getName(), p.getAge(), p.getMail(), id);
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
        jdbcTemplate.batchUpdate("insert into person (name, age, mail) VALUES (?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, people.get(i).getName());
                        ps.setInt(2, people.get(i).getAge());
                        ps.setString(3, people.get(i).getMail());
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
            list.add(person);
        }
        return list;
    }
}
