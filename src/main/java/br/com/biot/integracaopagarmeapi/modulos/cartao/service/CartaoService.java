package br.com.biot.integracaopagarmeapi.modulos.cartao.service;

import br.com.biot.integracaopagarmeapi.config.exception.ValidacaoException;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoResponse;
import br.com.biot.integracaopagarmeapi.modulos.cartao.model.Cartao;
import br.com.biot.integracaopagarmeapi.modulos.cartao.repository.CartaoRepository;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.CartaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.service.IntegracaoPagarmeService;
import br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.CreditCardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private IntegracaoPagarmeService integracaoPagarmeService;

    @Transactional
    public CartaoResponse salvarCartao(CartaoRequest request) {
        log.info("Realizando chamada ao endpoint de salvar cartão: ".concat(request.toJson()));
        validarDadosCartao(request);
        var cartaoCriadoPagarme = integracaoPagarmeService
            .salvarCartao(CartaoClientRequest.converterDe(request));
        var cartao = Cartao.converterDe(cartaoCriadoPagarme, "sadads5d1a56s65");
        cartaoRepository.save(cartao);
        var response = CartaoResponse.converterDe(cartao);
        log.info("Resposta do endpoint de salvar cartão: ".concat(response.toJson()));
        return response;
    }

    private void validarDadosCartao(CartaoRequest cartaoRequest) {
        validarDadosNaoExistentes(cartaoRequest);
        validarCartaoCreditoValido(cartaoRequest);
    }

    private void validarDadosNaoExistentes(CartaoRequest cartaoRequest) {
        if (isEmpty(cartaoRequest)
            || isEmpty(cartaoRequest.getUsuarioId())
            || isEmpty(cartaoRequest.getNumeroCartao())
            || isEmpty(cartaoRequest.getCvvCartao())
            || isEmpty(cartaoRequest.getNumeroCartao())
            || isEmpty(cartaoRequest.getNomeProprietarioCartao())) {
            throw new ValidacaoException("É necessário informar todos os dados do cartão.");
        }
    }

    private void validarCartaoCreditoValido(CartaoRequest cartaoRequest) {
        if (!new CreditCardValidator().isValid(cartaoRequest.getNumeroCartao())) {
            throw new ValidacaoException("O número do cartão de crédito não está válido.");
        }
    }

    public CartaoResponse buscarCartaoPorCartaoId(String cartaoId) {
        log.info("Realizando chamada ao endpoint de buscar cartão por cartaoId: ".concat(cartaoId));
        var cartao = CartaoResponse.converterDe(cartaoRepository
            .findByCartaoId(cartaoId)
            .orElseThrow(() -> new ValidacaoException("O cartão ".concat(cartaoId).concat(" não foi encontrado.")))
        );
        log.info("Resposta da chamda de buscar cartão por cartaoId: ".concat(cartao.toJson()));
        return cartao;
    }

    public List<CartaoResponse> buscarCartaoPorUsuarioId(String usuarioId) {
        log.info("Realizando chamada ao endpoint de buscar cartões por usuarioId: ".concat(usuarioId));
        var cartoes = cartaoRepository
            .findByUsuarioId(usuarioId)
            .stream()
            .map(CartaoResponse::converterDe)
            .collect(Collectors.toList());
        log.info("Resposta da chamda de buscar cartão por cartaoId: ".concat(JsonUtil.converterJsonParaString(cartoes)));
        return cartoes;
    }
}