package logic.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    private int id;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 3, max = 25, message = "name should be between 2 and 25 character")
    private String name;
    @Min(value = 0, message = "age should be more than 0")
    private int age;
    @NotEmpty(message = "mail should not be empty")
    @Email(message = "email should be valid")
    private String mail;
}
