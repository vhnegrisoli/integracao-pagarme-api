package br.com.biot.integracaopagarmeapi.modulos.cliente.model;

import br.com.biot.integracaopagarmeapi.modulos.cartao.model.Cartao;
import br.com.biot.integracaopagarmeapi.modulos.cliente.dto.ClienteRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLIENTE")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "ID_EXTERNO", nullable = false)
    private String idExterno;

    @Column(name = "USUARIO_ID", nullable = false)
    private String usuarioId;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "TIPO", nullable = false)
    private String tipo;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "NUMEROS_TELEFONE", nullable = false)
    private List<NumeroTelefone> numerosTelefone;

    @ManyToOne
    @JoinColumn(name = "FK_CARTAO", nullable = false)
    private Cartao cartao;

    public static Cliente converterDe(ClienteRequest request, Cartao cartao) {
        return Cliente
            .builder()
            .nome(request.getNome())
            .email(request.getEmail())
            .numerosTelefone(!isEmpty(request.getTelefones())
                ? request
                .getTelefones()
                .stream()
                .map(NumeroTelefone::converterDe)
                .collect(Collectors.toList())
                : Collections.emptyList())
            .cartao(cartao)
            .build();
    }
}