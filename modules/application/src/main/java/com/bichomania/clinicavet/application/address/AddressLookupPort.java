package com.bichomania.clinicavet.application.address;

public interface AddressLookupPort {
    AddressData findByCep(String cep);
}