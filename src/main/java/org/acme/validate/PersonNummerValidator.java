package org.acme.validate;

public class PersonNummerValidator {

    public static boolean isValidNIN(String nin) {
        if (nin == null) {
            return false;
        }
        if (nin.length() != 11) {
            return false;
        }
        return true;
    }
}
