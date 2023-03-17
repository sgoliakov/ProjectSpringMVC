package logic.DAO;

import logic.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeopleJdbcDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/repeatSpring";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "1992svyat";

    private static final Connection connection;

    static {
        try {//explicitly download driver to RAM
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Person");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String mail = resultSet.getString("mail");
                people.add(new Person(id, name, age, mail));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person getPersonById(int id) {
        Person person = new Person();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from person where id =" + id;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int idPerson = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String mail = resultSet.getString("mail");
                person.setId(idPerson);
                person.setName(name);
                person.setAge(age);
                person.setMail(mail);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return person;
    }

    public void save(Person person) {
        String sql = "insert into person (name, age, mail) VALUES (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getMail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(Person person, int id) {
        String sql = "update person set name = ?, age = ?, mail = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getMail());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id) {
        String sql = "delete from person where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
