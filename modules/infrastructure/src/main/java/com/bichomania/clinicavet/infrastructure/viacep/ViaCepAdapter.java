package com.bichomania.clinicavet.infrastructure.viacep;

import com.bichomania.clinicavet.application.address.AddressData;
import com.bichomania.clinicavet.application.address.AddressLookupPort;
import com.bichomania.clinicavet.common.validator.CepValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ViaCepAdapter implements AddressLookupPort {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    private final RestClient restClient;

    public ViaCepAdapter(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    @Override
    public AddressData findByCep(String cep) {
        String cleaned = CepValidator.clean(cep);

        ViaCepResponse response = restClient.get()
                .uri(VIA_CEP_URL, cleaned)
                .retrieve()
                .body(ViaCepResponse.class);

        // retrabalhar
        if (response == null || response.erro()) {
            throw new IllegalArgumentException("CEP não encontrado: " + cleaned);
        }

        return new AddressData(
                cleaned,
                response.localidade(),
                response.uf(),
                response.logradouro(),
                response.bairro()
        );
    }
}
