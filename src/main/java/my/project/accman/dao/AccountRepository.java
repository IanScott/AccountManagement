package my.project.accman.dao;

import my.project.accman.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * The JPA Account Repository.
 */
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Retrieve Accounts by UUID.
     * @param uuid an account's UUID.
     * @return An Optional containing the Account if found, else an empty Optional.
     */
    Optional<Account> findByUuidEquals(String uuid);

    /**
     * Method used for deleting Accounts by UUID.
     * @param uuid an account's UUID.
     * @return the number of objects deleted.
     */
    Integer removeByUuid(String uuid);
}