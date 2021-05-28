package br.com.biot.integracaopagarmeapi.modulos.integracao.dto;

import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.ClienteDocumentoDto;
import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteClientResponse {

    private Integer id;

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
    private List<ClienteDocumentoDto> documentos;

    @JsonProperty("phone_numbers")
    private List<String> numerosTelefone;

    public String toJson() {
        return JsonUtil.converterJsonParaString(this);
    }
}
