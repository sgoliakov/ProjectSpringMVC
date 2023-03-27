package logic.models;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    private int id;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 3, max = 25, message = "name should be between 2 and 25 character")
    private String name;
    @Min(value = 0, message =  "age should be greater than 0")
    private int age;
    @NotEmpty(message = "mail should not be empty")
    @Email(message = "email should be valid")
    private String mail;
    // Country, City, index
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",
            message = "Your address should be in this format: Country, City, index(6 digits)")
    private String address;
}
