package br.com.biot.integracaopagarmeapi.modulos.integracao.client;

import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ApiKeyRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
    name = "pagarmeTransacaoClient",
    contextId = "pagarmeTransacaoClient",
    url = "${pagarme.transacoes.uri}")
public interface PagarmeTransacaoClient {

    @PostMapping
    Optional<TransacaoClientResponse> salvarTransacao(@RequestBody TransacaoClientRequest request);

    @PostMapping("{transaction_id}/capture")
    Optional<TransacaoClientResponse> capturarTransacao(@PathVariable(name = "transaction_id") Long transactionId,
                                                 @SpringQueryMap ApiKeyRequest apiKeyRequest);

    @GetMapping("{transaction_id}")
    Optional<TransacaoClientResponse> buscarTransacaoPorId(@PathVariable(name = "transaction_id") Long transactionId,
                                                    @SpringQueryMap ApiKeyRequest apiKeyRequest);
}
