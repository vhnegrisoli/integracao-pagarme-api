package br.com.biot.integracaopagarmeapi.modulos.transacao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TransacaoStatus {

    PROCESSANDO("processing"),
    AUTORIZADA("authorized"),
    PAGA("paid"),
    RECUSADA("refused"),
    CHARGEDBACK("chargedback"),
    ANALISANDO("analyzing"),
    PENDENTE_REVISAO("pending_review");

    private final String statusPagarme;

    public static boolean possuiStatusValidos(String status) {
        return List.of(
            PROCESSANDO.getStatusPagarme(),
            AUTORIZADA.getStatusPagarme(),
            PAGA.getStatusPagarme()
        ).contains(status);
    }
}
