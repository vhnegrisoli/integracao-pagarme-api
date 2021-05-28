package br.com.biot.integracaopagarmeapi.modulos.integracao.service;

import br.com.biot.integracaopagarmeapi.config.exception.ValidacaoException;
import br.com.biot.integracaopagarmeapi.modulos.integracao.client.PagarmeClienteClient;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ApiKeyRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ClienteClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ClienteClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IntegracaoClienteService {

    @Autowired
    private PagarmeClienteClient clienteClient;

    @Value("${pagarme.api_keys.teste}")
    private String apiKey;

    public ClienteClientResponse salvarCliente(ClienteClientRequest request) {
        try {
            request.setApiKey(apiKey);
            log.info("Realizando chamada à API do Pagar.me para salvar o cliente com dados: ".concat(request.toJson()));
            var response = clienteClient
                .salvarCliente(request)
                .orElseThrow(() -> new ValidacaoException("Erro ao tentar salvar cliente na Pagar.me."));
            log.info("Obtendo resposta da API do Pagar.me do cliente salvo: ".concat(response.toJson()));
            return response;
        } catch (Exception ex) {
            log.error("Erro ao tentar salvar cliente na API da Pagar.me: ", ex);
            throw new ValidacaoException("Erro interno ao tentar salvar cliente na Pagar.me.");
        }
    }

    public ClienteClientResponse buscarClientePorId(String clienteId) {
        try {
            log.info("Realizando chamada à API do Pagar.me para buscar um cliente pelo ID: ".concat(clienteId));
            var response = clienteClient
                .buscarClientePorId(clienteId, ApiKeyRequest.criar(apiKey))
                .orElseThrow(() -> new ValidacaoException("Erro ao tentar buscar cliente por ID na Pagar.me."));
            log.info("Obtendo resposta da API do Pagar.me de cartão por ID: ".concat(response.toJson()));
            return response;
        } catch (Exception ex) {
            log.error("Erro ao tentar buscar cliente por ID na API da Pagar.me: ", ex);
            throw new ValidacaoException("Erro interno ao tentar buscar cliente por ID na Pagar.me.");
        }
    }
}
