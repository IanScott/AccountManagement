package my.project.accman.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * An Enum containing all possible Account Types. At the moment their are only two possible types:
 * - Savings
 * - Current Account
 */
public enum AccountType {
    Savings("Savings"), CurrentAccount("Current Account");

    private String value;

    private AccountType(String value){
        this.value = value;
    }

    /**
     * Method returns a String representation of the enum.
     * @return a string representation.
     */
    @JsonValue
    public String value(){
        return value;
    }
}