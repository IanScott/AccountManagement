package my.project.accman.model;

import lombok.*;
import my.project.accman.service.implementation.validation.annotations.age.AdultValidation;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

import static my.project.accman.constants.ValidationMessages.*;

/**
 * A class representing a Person Entity.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = P1)
    @Size(min = 3, max = 35, message = P2)
    private String firstName;

    @NotNull(message = P3)
    @Size(min = 3, max = 35, message = P4)
    private String lastName;

    @NotNull(message = P5)
    @AdultValidation(ageOfAdult = 18, message = P6)
    private Date dateOfBirth;

    @NotEmpty(message = P7)
    @Email(message = P8)
    private String email;
}
