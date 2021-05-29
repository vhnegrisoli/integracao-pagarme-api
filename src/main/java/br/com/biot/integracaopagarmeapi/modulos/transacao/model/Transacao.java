package br.com.biot.integracaopagarmeapi.modulos.transacao.model;

import br.com.biot.integracaopagarmeapi.modulos.cartao.model.Cartao;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientResponse;
import br.com.biot.integracaopagarmeapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.biot.integracaopagarmeapi.modulos.transacao.enums.TransacaoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACAO")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CARTAO_ID", nullable = false)
    private Cartao cartao;

    @Column(name = "TRANSACAO_ID", nullable = false)
    private String transacaoId;

    @Column(name = "TOTAL_TRANSACAO", nullable = false)
    private BigDecimal totalTransacao;

    @Column(name = "SITUACAO_TRANSACAO", nullable = false)
    private String situacaoTransacao;

    @Column(name = "TRANSACAO_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransacaoStatus transacaoStatus;

    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        dataCadastro = LocalDateTime.now();
    }

    public static Transacao converterDe(JwtUsuarioResponse usuario,
                                        TransacaoClientResponse transacaoResponse,
                                        Cartao cartao) {
        return null;
    }
}
