package course.udemy.springboot.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {

    @Id
    private Long accountNumber;
    private Long customerId;
    private String accountType;
    private String branchAddress;
    private Boolean communicationSw;
}
