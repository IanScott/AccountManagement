package my.project.accman.constants;

import java.util.*;

/**
 * This class contains all the error codes and their corresponding error messages.
 * This configuration is hard coded at the moment, it can (should) be made more configurable in the future.
 */
public class ValidationMessages {
    public static final String P1 = "p1";
    public static final String P2 = "p2";
    public static final String P3 = "p3";
    public static final String P4 = "p4";
    public static final String P5 = "p5";
    public static final String P6 = "p6";
    public static final String P7 = "p7";
    public static final String P8 = "p8";

    public static final String A1 = "a1";
    public static final String A2 = "a2";

    public static final String D1 = "d1";
    public static final String D2 = "d2";
    public static final String D3 = "d3";
    public static final String D4 = "d4";
    public static final String D5 = "d5";
    public static final String D6 = "d6";

    public static final String M1 = "m1";
    public static final String M2 = "m2";
    public static final String M3 = "m3";
    public static final String M4 = "m4";
    private static Map<String, String> messages = new HashMap<>();

    static {
        messages.put(P1, "[First name] is a mandatory field.");
        messages.put(P2, "[First name] must be between 3 and 35 characters");
        messages.put(P3, "[Last name] is a mandatory field.");
        messages.put(P4, "[Last name] must be between 3 and 35 characters");
        messages.put(P5, "[Date of birth] is a mandatory field.");
        messages.put(P6, "[Holder] must be a minimum of 18 years old.");
        messages.put(P7, "[Email] is a mandatory field.");
        messages.put(P8, "[Email] must be a valid email");

        messages.put(A1, "Account may not be opened more than 30 days in the past");
        messages.put(A2, "[InitialDeposit] can not be negative");

        messages.put(D1, "[Closure date] is mandatory for temporary accounts");
        messages.put(D2, "[Closure date] cannot be earlier than opening date + 2 months");
        messages.put(D3, "[Holder] is mandatory");
        messages.put(D4, "[Type] is mandatory");
        messages.put(D5, "[UUID] is mandatory when updating");
        messages.put(D6, "[Open date] is mandatory");

        messages.put(M1, "[UUID] cannot be modified");
        messages.put(M2, "[Type] cannot be modified");
        messages.put(M3, "[Opening date] cannot be modified");
        messages.put(M4, "When an account set to temporary, [Closure date] cannot be earlier than modification date + 1 month");
    }

    /**
     * Method used for converting error codes into error messages.
     * @param key the error code.
     * @return the error message.
     */
    public static String codeToMessage(String key) {
        return messages.getOrDefault(key, "unknown message");
    }
}