package br.com.biot.integracaopagarmeapi.modulos.integracao.client;

import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ApiKeyRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ClienteClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ClienteClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
    name = "pagarmeClienteClient",
    contextId = "pagarmeClienteClient",
    url = "${pagarme.clientes.uri}")
public interface PagarmeClienteClient {

    @PostMapping
    Optional<ClienteClientResponse> salvarCliente(@RequestBody ClienteClientRequest request);

    @GetMapping("{customer_id}")
    Optional<ClienteClientResponse> buscarClientePorId(@PathVariable(name = "customer_id") String customerId,
                                                       @SpringQueryMap ApiKeyRequest request);
}
