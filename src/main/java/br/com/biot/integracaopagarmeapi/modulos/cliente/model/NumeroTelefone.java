package br.com.biot.integracaopagarmeapi.modulos.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NUMERO_TELEFONE")
public class NumeroTelefone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NUMERO_TELEFONE", nullable = false)
    private String numeroTelefone;

    public static NumeroTelefone converterDe(String telefone) {
        return NumeroTelefone
            .builder()
            .numeroTelefone(telefone)
            .build();
    }
}
