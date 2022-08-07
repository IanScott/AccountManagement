package my.project.accman.testutils;

import my.project.accman.model.*;

import java.util.*;

public class TestUtils {
    public static Account createValidAccount() {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.MONTH, 2);
        Date twoMonthsInFuture = cal.getTime();

        var account = new Account();
        account.setType(AccountType.CurrentAccount);
        account.setOpeningDate(today);
        account.setTemporary(true);
        account.setClosureDate(twoMonthsInFuture);


        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doo");
        person.setEmail("test@domain.com");

        cal.add(Calendar.MONTH, -2);
        cal.add(Calendar.YEAR, -18);

        person.setDateOfBirth(cal.getTime());
        account.setHolder(person);

        return account;
    }

    public static Account clone(Account account){
        Account clone = new Account();

        clone.setUuid(account.getUuid());
        clone.setInitialDeposit(account.getInitialDeposit());
        clone.setType(account.getType());
        clone.setTemporary(account.isTemporary());
        clone.setOpeningDate(account.getOpeningDate());
        clone.setOpeningDate(account.getOpeningDate());

        clone.setHolder(clone(account.getHolder()));

        return clone;
    }

    private static Person clone(Person person){
        Person clone = new Person();
        clone.setFirstName(person.getFirstName());
        clone.setLastName(person.getLastName());
        clone.setDateOfBirth(person.getDateOfBirth());
        clone.setEmail(person.getEmail());
        return person;
    }
}
