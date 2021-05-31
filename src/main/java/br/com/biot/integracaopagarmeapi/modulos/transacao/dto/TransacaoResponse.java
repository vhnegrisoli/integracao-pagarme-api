package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import br.com.biot.integracaopagarmeapi.modulos.transacao.model.Transacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoResponse {

    private Integer transacaoId;
    private Long transacaoPagarmeId;
    private String transacaoPagarmeStatus;
    private String transacaoStatus;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime horarioTransacao;
    private String transacaoCartaoId;
    private String transacaoUsuarioId;
    private String transacaoCartaoUltimosDigitos;
    private String transacaoCartaoBandeira;

    public static TransacaoResponse converterDe(Transacao transacao) {
        return TransacaoResponse
            .builder()
            .transacaoId(transacao.getId())
            .transacaoPagarmeId(transacao.getTransacaoId())
            .transacaoPagarmeStatus(transacao.getSituacaoTransacao())
            .transacaoStatus(!isEmpty(transacao.getTransacaoStatus())
                ? transacao.getTransacaoStatus().name()
                : Strings.EMPTY)
            .horarioTransacao(transacao.getDataCadastro())
            .transacaoUsuarioId(transacao.getUsuarioId())
            .transacaoCartaoId(transacao.getCartao().getCartaoId())
            .transacaoCartaoUltimosDigitos(transacao.getCartao().getUltimosDigitos())
            .transacaoCartaoBandeira(transacao.getCartao().getBandeira())
            .build();
    }
}
