package br.com.biot.integracaopagarmeapi.modulos.cartao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CARTAO")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "CARTAO_ID", nullable = false)
    private String cartaoId;

    @Column(name = "USUARIO_ID", nullable = false)
    private String usuarioId;

    @Column(name = "ULTIMOS_DIGITOS ", nullable = false)
    private String ultimosDigitos;

    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    private LocalDateTime dataAtualizacao;
}