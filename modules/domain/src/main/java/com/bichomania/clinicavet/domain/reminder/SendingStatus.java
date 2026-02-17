package com.bichomania.clinicavet.domain.reminder;

public enum SendingStatus {
    PENDENTE,
    ENVIADO,
    FALHA // Adicionado para suportar melhor o ciclo de vida no domínio
}