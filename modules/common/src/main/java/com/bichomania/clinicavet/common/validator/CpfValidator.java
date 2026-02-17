package com.bichomania.clinicavet.common.validator;

public final class CpfValidator {

    private CpfValidator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isValid(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int fistDigit = 11 - (sum % 11);
            if (fistDigit >= 10) fistDigit = 0;

            if (fistDigit != Character.getNumericValue(cpf.charAt(9))) {
                return false;
            }

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) secondDigit = 0;

            if (secondDigit != Character.getNumericValue(cpf.charAt(10))) {
                return false;
            }

        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static String clean(String cpf) {
        if (cpf == null ) return null;
        return cpf.replaceAll("\\D", "");
    }

    public static String format(String cpf) {
        if (cpf == null || cpf.length() != 11) return cpf;
        return String.format("%s.%s.%s-%s",
                cpf.substring(0, 3),
                cpf.substring(3, 6),
                cpf.substring(6, 9),
                cpf.substring(9, 11)
                );
    }
}
