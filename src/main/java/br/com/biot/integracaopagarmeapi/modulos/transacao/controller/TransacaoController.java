package br.com.biot.integracaopagarmeapi.modulos.transacao.controller;

import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.TransacaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.TransacaoResponse;
import br.com.biot.integracaopagarmeapi.modulos.transacao.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public TransacaoResponse salvarTransacao(@RequestBody TransacaoRequest transacaoRequest) {
        return transacaoService.salvarTransacao(transacaoRequest);
    }
}
