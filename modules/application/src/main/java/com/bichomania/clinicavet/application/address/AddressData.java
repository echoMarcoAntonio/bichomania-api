package com.bichomania.clinicavet.application.address;

public record AddressData(
        String cep,
        String city,
        String state,
        String street,
        String neighborhood
) {}