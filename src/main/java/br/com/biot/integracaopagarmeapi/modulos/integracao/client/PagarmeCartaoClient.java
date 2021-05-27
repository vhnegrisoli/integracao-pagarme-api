package br.com.biot.integracaopagarmeapi.modulos.integracao.client;

import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ApiKeyRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.CartaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.CartaoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
    name = "pagarmeCartaoClient",
    contextId = "pagarmeCartaoClient",
    url = "${pagarme.cartoes.uri}")
public interface PagarmeCartaoClient {

    @PostMapping
    Optional<CartaoClientResponse> salvarCartao(@RequestBody CartaoClientRequest request);

    @GetMapping("{cardId}")
    Optional<CartaoClientResponse> buscarCartaoPorId(@PathVariable(name = "card_id") String cardId,
                                                     @SpringQueryMap ApiKeyRequest request);
}
