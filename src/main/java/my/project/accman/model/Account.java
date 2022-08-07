package my.project.accman.model;

import lombok.*;
import my.project.accman.service.implementation.validation.annotations.opening.OpeningValidation;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.*;

import static my.project.accman.constants.ValidationMessages.*;

/**
 * This class represents an Account Entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @OpeningValidation(maxDaysInPast = 30, message = A1)
    private Date openingDate;

    private boolean temporary;
    private Date closureDate;

    @PositiveOrZero(message = A2)
    private long initialDeposit;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Person holder;
}