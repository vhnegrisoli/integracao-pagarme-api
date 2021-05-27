package br.com.biot.integracaopagarmeapi.modulos.transacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    @JsonProperty("external_id")
    private String idExterno;

    @JsonProperty("name")
    private String nome;

    @JsonProperty("type")
    private String tipo;

    @JsonProperty("country")
    private String pais;

    @JsonProperty("email")
    private String email;

    @JsonProperty("documents")
    private List<ClienteDocumentoDto> documentos = new ArrayList<>();

    @JsonProperty("phone_numbers")
    private List<String> numerosTelefone = new ArrayList<>();
}
