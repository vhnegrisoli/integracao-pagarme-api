package br.com.biot.integracaopagarmeapi.modulos.cartao.controller;

import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoResponse;
import br.com.biot.integracaopagarmeapi.modulos.cartao.service.CartaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public CartaoResponse salvarCartao(@RequestBody CartaoRequest request) {
        return cartaoService.salvarCartao(request);
    }

    @GetMapping("{cartaoId}")
    public CartaoResponse buscarCartaoPorCartaoId(@PathVariable String cartaoId) {
        return cartaoService.buscarCartaoPorCartaoId(cartaoId);
    }

    @GetMapping("usuario/{usuarioId}")
    public List<CartaoResponse> buscarCartaoPorUsuarioId(@PathVariable String usuarioId) {
        return cartaoService.buscarCartaoPorUsuarioId(usuarioId);
    }
}
