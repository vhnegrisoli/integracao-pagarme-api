package br.com.biot.integracaopagarmeapi.modulos.cartao.service;

import br.com.biot.integracaopagarmeapi.modulos.cartao.client.PagarmeCartaoClient;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

@Service
public class CartaoService {

    @Autowired
    private PagarmeCartaoClient cartaoClient;

    @Value("${pagarme.api_keys.teste}")
    private String apiKey;

    public CartaoResponse salvarCartao(CartaoRequest cartaoRequest) throws ValidationException {
        var cartaoSalvo = cartaoClient
            .salvarCartao(CartaoClientRequest.converterDe(cartaoRequest,apiKey));
        return CartaoResponse.converterDe(
            cartaoSalvo
                .orElseThrow(() -> new ValidationException("Erro ao salvar cartão."))
        , "assad-55151a-asas5155as1-asdf");
    }
}
