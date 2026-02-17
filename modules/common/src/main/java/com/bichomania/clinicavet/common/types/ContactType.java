package com.bichomania.clinicavet.common.types;

public enum ContactType {
    WHATSAPP("WhatsApp"),
    MOBILE("Celular"),
    LANDLINE("Telefone Fixo");

    private final String description;

    ContactType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
