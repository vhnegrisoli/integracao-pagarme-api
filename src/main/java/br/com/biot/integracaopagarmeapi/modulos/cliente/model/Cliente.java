package br.com.biot.integracaopagarmeapi.modulos.cliente.model;

import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ClienteClientResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @Column(name = "ID_CLIENTE_PAGARME", nullable = false)
    private Integer idClientePagarme;

    @Column(name = "ID_EXTERNO", nullable = false)
    private String idExterno;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "TIPO", nullable = false)
    private String tipo;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "NUMEROS_TELEFONE", nullable = false)
    private List<NumeroTelefone> numerosTelefone;

    public static Cliente converterDe(ClienteClientResponse response) {
        return Cliente
            .builder()
            .idClientePagarme(response.getId())
            .idExterno(response.getIdExterno())
            .nome(response.getNome())
            .email(response.getEmail())
            .tipo(response.getTipo())
            .numerosTelefone(response
                .getNumerosTelefone()
                .stream()
                .map(NumeroTelefone::converterDe)
                .collect(Collectors.toList()))
            .build();
    }
}