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
@Table(name = "ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "PAIS", nullable = false)
    private String pais;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @Column(name = "CIDADE", nullable = false)
    private String cidade;

    @Column(name = "RUA", nullable = false)
    private String rua;

    @Column(name = "NUMERO_RUA", nullable = false)
    private String numeroRua;

    @Column(name = "CEP", nullable = false)
    private String cep;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @ManyToOne
    @JoinColumn(name = "FK_CLIENTE", nullable = false)
    private Cliente cliente;
}
