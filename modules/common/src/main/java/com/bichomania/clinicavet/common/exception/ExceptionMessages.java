package com.bichomania.clinicavet.common.exception;

/**
 * Centraliza mensagens de erro do sistema.
 */
public final class ExceptionMessages {

    // PET
    public static final String PET_FIELD_REQUIRED =
            "Campos obrigatórios do pet não foram preenchidos.";
    public static final String PET_BIRTH_DATE_IN_FUTURE =
            "A data de nascimento não pode ser no futuro.";
    public static final String PET_NOT_FOUND =
            "Pet não encontrado.";
    public static final String PET_NAME_TOO_LONG =
            "Nome do pet não pode exceder 100 caracteres.";
    public static final String PET_BREED_TOO_LONG =
            "Raça não pode exceder 50 caracteres.";
    public static final String PET_MICROCHIP_INVALID =
            "Número do microchip inválido.";
    // VACCINE
    public static final String VACCINE_FIELD_REQUIRED =
            "Campos obrigatórios da vacina não foram preenchidos.";
    public static final String VACCINE_NOT_FOUND =
            "Vacina não encontrada.";
    public static final String VACCINE_NAME_TOO_LONG =
            "Nome da vacina não pode exceder 100 caracteres.";
    public static final String VACCINE_MANUFACTURER_TOO_LONG =
            "Nome do fabricante não pode exceder 100 caracteres.";
    public static final String VACCINE_DESCRIPTION_TOO_LONG =
            "Descrição não pode exceder 500 caracteres.";
    public static final String VACCINE_VALIDITY_OUT_OF_RANGE =
            "Validade deve estar entre 1 e 24 meses.";
    // DEWORMER
    public static final String DEWORMER_FIELD_REQUIRED =
            "Campos obrigatórios do vermífugo não foram preenchidos.";
    public static final String DEWORMER_NOT_FOUND =
            "Vermífugo não encontrado.";
    public static final String DEWORMER_NAME_REQUIRED =
            "O nome do vermífugo é obrigatório.";
    public static final String DEWORMER_NAME_TOO_LONG =
            "Nome do vermífugo não pode exceder 100 caracteres.";
    public static final String DEWORMER_MANUFACTURER_REQUIRED =
            "O fabricante do vermífugo é obrigatório.";
    public static final String DEWORMER_MANUFACTURER_TOO_LONG =
            "Nome do fabricante não pode exceder 100 caracteres.";
    public static final String DEWORMER_DESCRIPTION_TOO_LONG =
            "Descrição não pode exceder 500 caracteres.";
    public static final String DEWORMER_VALIDITY_OUT_OF_RANGE =
            "Validade deve estar entre 1 e 24 meses.";
    // GUARDIAN
    public static final String GUARDIAN_NAME_REQUIRED =
            "O nome do tutor é obrigatório";
    public static final String GUARDIAN_NAME_TOO_LONG =
            "Nome não pode exceder 100 caracteres.";

    public static final String GUARDIAN_CPF_REQUIRED = "CPF do tutor é obrigatório.";
    public static final String GUARDIAN_CPF_INVALID = "CPF informado é inválido.";
    public static final String GUARDIAN_CPF_DUPLICATE = "CPF já cadastrado: %s";
    public static final String GUARDIAN_EMAIL_REQUIRED = "Email do tutor é obrigatório.";
    public static final String GUARDIAN_EMAIL_INVALID = "Email informado é inválido.";
    public static final String GUARDIAN_CONTACT_REQUIRED = "Tutor deve ter pelo menos um contato.";
    public static final String GUARDIAN_CONTACT_TYPE_REQUIRED = "Tipo do contato é obrigatório.";
    public static final String GUARDIAN_CONTACT_VALUE_REQUIRED = "Valor do contato é obrigatório.";
    public static final String GUARDIAN_CONTACT_ONE_PRINCIPAL = "Deve haver exatamente um contato principal.";
    public static final String GUARDIAN_NOT_FOUND = "Tutor não encontrado.";
    public static final String GUARDIAN_ALREADY_INACTIVE = "Tutor já está inativo.";
    public static final String GUARDIAN_ALREADY_ACTIVE = "Tutor já está ativo.";

    // ADDRESS
    public static final String GUARDIAN_ADDRESS_TO_STRING = "Endereço [cidade=%s, estado=%s, cep=%s, detalhes=%s, principal=%s]";
    public static final String GUARDIAN_CITY_REQUIRED = "Cidade é obrigatória.";
    public static final String GUARDIAN_CITY_TOO_LONG = "Cidade não pode exceder 100 caracteres.";
    public static final String GUARDIAN_STATE_INVALID = "Estado deve ter 2 caracteres.";
    public static final String GUARDIAN_CEP_INVALID = "CEP inválido.";

    // USER
    public static final String USER_FIELD_REQUIRED =
            "Campos obrigatórios do usuário não foram preenchidos.";
    public static final String USER_NOT_FOUND =
            "Usuário não encontrado.";
    public static final String USER_EMAIL_DUPLICATE =
            "E-mail já cadastrado no sistema.";
    public static final String USER_EMAIL_INVALID =
            "E-mail inválido.";
    public static final String USER_PASSWORD_WEAK =
            "Senha não atende aos requisitos mínimos de segurança.";
    public static final String USER_NAME_TOO_LONG =
            "Nome não pode exceder 100 caracteres.";
    // VACCINE APPLICATION
    public static final String VACCINE_APPLICATION_FIELD_REQUIRED =
            "Campos obrigatórios da aplicação de vacina não foram preenchidos.";
    public static final String VACCINE_APPLICATION_DATE_IN_FUTURE =
            "Data de aplicação não pode ser no futuro.";
    public static final String VACCINE_APPLICATION_NOT_FOUND =
            "Aplicação de vacina não encontrada.";
    // DEWORMER APPLICATION
    public static final String DEWORMER_APPLICATION_FIELD_REQUIRED =
            "Campos obrigatórios da aplicação de vermífugo não foram preenchidos.";
    public static final String DEWORMER_APPLICATION_DATE_IN_FUTURE =
            "Data de aplicação não pode ser no futuro.";
    public static final String DEWORMER_APPLICATION_NOT_FOUND =
            "Aplicação de vermífugo não encontrada.";
    // REMINDER
    public static final String REMINDER_FIELD_REQUIRED =
            "Campos obrigatórios do lembrete não foram preenchidos.";
    public static final String REMINDER_NOT_FOUND =
            "Lembrete não encontrado.";
    public static final String REMINDER_MESSAGE_TOO_LONG =
            "Mensagem do lembrete não pode exceder 1000 caracteres.";
    public static final String USER_INVALID_EMAIL =
            "E-mail inválido.";
    // AUTH
    public static final String AUTHENTICATION_FAILED =
            "Credenciais inválidas.";
    public static final String AUTHENTICATION_REQUIRED =
            "Autenticação necessária para acessar este recurso.";
    public static final String AUTHORIZATION_FAILED =
            "Você não tem permissão para acessar este recurso.";
    public static final String TOKEN_INVALID =
            "Token inválido ou expirado.";
    public static final String TOKEN_EXPIRED =
            "Token expirado.";
    // GERAL
    public static final String INTERNAL_SERVER_ERROR =
            "Erro interno do servidor. Contate o suporte.";
    public static final String INVALID_REQUEST =
            "Requisição inválida.";
    public static final String RESOURCE_NOT_FOUND =
            "Recurso não encontrado.";
    public static final String DUPLICATE_RESOURCE =
            "Recurso já existe no sistema.";
    public static final String INVALID_DATE_FORMAT =
            "Formato de data inválido.";
    public static final String INVALID_UUID_FORMAT =
            "Formato de UUID inválido.";

    private ExceptionMessages() {
        throw new UnsupportedOperationException("Classe utilitária não pode ser instanciada");
    }
}
