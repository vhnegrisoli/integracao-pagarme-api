package br.com.biot.integracaopagarmeapi.modulos.integracao.client;

import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.CartaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.CartaoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
    name = "pagarmeTransacaoClient",
    contextId = "pagarmeTransacaoClient",
    url = "${pagarme.transacoes.uri}")
public interface PagarmeTransacaoClient {

    @PostMapping
    Optional<CartaoClientResponse> salvarCartao(@RequestBody CartaoClientRequest request);
}
