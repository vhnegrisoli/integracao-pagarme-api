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
@Table(name = "DOCUMENTO")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NUMERO_DOCUMENTO", nullable = false)
    private String numeroDocumento;

    @ManyToOne
    @JoinColumn(name = "FK_CLIENTE", nullable = false)
    private Cliente cliente;

    public static Documento converterDe(String documento,
                                        Cliente cliente) {
        return Documento
            .builder()
            .numeroDocumento(documento)
            .cliente(cliente)
            .build();
    }
}
