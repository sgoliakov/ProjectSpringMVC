package logic.DAO;

import logic.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
}
