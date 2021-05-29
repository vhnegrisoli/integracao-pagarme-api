package br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao;

import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.EnderecoCobrancaRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import static br.com.biot.integracaopagarmeapi.modulos.util.Constantes.COMPLEMENTO_EMPRESA;
import static br.com.biot.integracaopagarmeapi.modulos.util.Constantes.PAIS_BRASIL;
import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCobrancaClientRequest {

    @JsonProperty("country")
    private String pais;

    @JsonProperty("state")
    private String estado;

    @JsonProperty("city")
    private String cidade;

    @JsonProperty("neighborhood")
    private String bairro;

    @JsonProperty("street")
    private String rua;

    @JsonProperty("street_number")
    private String numeroRua;

    @JsonProperty("complementary")
    private String complemento;

    @JsonProperty("zipcode")
    private String cep;

    public static EnderecoCobrancaClientRequest converterDe(EnderecoCobrancaRequest enderecoCobrancaRequest) {
        var enderecoCobrancaClientRequest = new EnderecoCobrancaClientRequest();
        BeanUtils.copyProperties(enderecoCobrancaRequest, enderecoCobrancaClientRequest);
        enderecoCobrancaClientRequest.setPais(PAIS_BRASIL);
        if (isEmpty(enderecoCobrancaClientRequest.getComplemento())) {
            enderecoCobrancaClientRequest.setComplemento(COMPLEMENTO_EMPRESA);
        }
        return enderecoCobrancaClientRequest;
    }
}
