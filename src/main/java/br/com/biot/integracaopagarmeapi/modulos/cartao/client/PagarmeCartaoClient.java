package br.com.biot.integracaopagarmeapi.modulos.cartao.client;

import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
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
}
