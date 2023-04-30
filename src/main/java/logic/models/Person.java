package logic.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 3, max = 25, message = "name should be between 2 and 25 character")
    @Column(name = "name")
    private String name;
    @Min(value = 0, message = "age should be greater than 0")
    @Column(name = "age")
    private int age;
    @NotEmpty(message = "mail should not be empty")
    @Email(message = "email should be valid")
    @Column(name = "mail")
    private String mail;
    // Country, City, index
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",
            message = "Your address should be in this format: Country, City, index(6 digits)")
    @Column(name = "address")
    private String address;
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToMany(mappedBy = "owner")
    private List<Item> items;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                '}';
    }

    public Person(int id, String name, int age, String mail, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mail = mail;
        this.address = address;
    }
}
