package com.bichomania.clinicavet.common.validator;

public class CepValidator {

    private CepValidator(){
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Valida apenas o formato do CEP (8 dígitos numéricos).
     * A existência do CEP é verificada via ViaCEP na camada de infraestrutura.
     */
    public static boolean isValid(String cep){
        if (cep == null) return false;
        String cleaned = clean(cep);
        return cleaned.length() == 8 && cleaned.matches("\\d{8}");
    }

    public static String clean(String cep){
        if (cep == null) return null;
        return cep.replaceAll("\\D", "");
    }

    public static String format(String cep){
        if (cep == null || clean(cep).length() != 8) return cep;
        String c = clean(cep);
        return c.substring(0,5) + "-" + c.substring(5);
    }
}
