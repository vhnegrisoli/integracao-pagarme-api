package br.com.biot.integracaopagarmeapi.modulos.cartao.controller;

import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoResponse;
import br.com.biot.integracaopagarmeapi.modulos.cartao.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("api/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public CartaoResponse salvarCartao(@RequestBody CartaoRequest request) throws ValidationException {
        return cartaoService.salvarCartao(request);
    }
}
