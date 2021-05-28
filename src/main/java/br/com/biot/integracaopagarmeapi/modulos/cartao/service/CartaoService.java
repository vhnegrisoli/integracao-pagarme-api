package br.com.biot.integracaopagarmeapi.modulos.cartao.service;

import br.com.biot.integracaopagarmeapi.config.exception.ValidacaoException;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.cartao.dto.CartaoResponse;
import br.com.biot.integracaopagarmeapi.modulos.cartao.model.Cartao;
import br.com.biot.integracaopagarmeapi.modulos.cartao.repository.CartaoRepository;
import br.com.biot.integracaopagarmeapi.modulos.cliente.cliente.ClienteService;
import br.com.biot.integracaopagarmeapi.modulos.cliente.model.Cliente;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.CartaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.CartaoClientResponse;
import br.com.biot.integracaopagarmeapi.modulos.integracao.service.IntegracaoCartaoService;
import br.com.biot.integracaopagarmeapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.biot.integracaopagarmeapi.modulos.jwt.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.CreditCardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.biot.integracaopagarmeapi.modulos.util.JsonUtil.converterJsonParaString;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private IntegracaoCartaoService integracaoCartaoService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ClienteService clienteService;

    @Transactional
    public CartaoResponse salvarCartao(CartaoRequest request) {
        log.info("Realizando chamada ao endpoint de salvar cartão: ".concat(request.toJson()));
        validarDadosCartao(request);
        var cartaoCriadoPagarme = integracaoCartaoService
            .salvarCartao(CartaoClientRequest.converterDe(request));
        var usuarioAutenticado = jwtService.recuperarUsuarioAutenticado();
        var cliente = clienteService.definirCliente(request.getCliente(), usuarioAutenticado);
        validarCartaoIdJaExistente(cartaoCriadoPagarme.getId());
        var cartaoSalvo = salvarCartaoDoPagarme(cartaoCriadoPagarme, cliente, usuarioAutenticado);
        var response = CartaoResponse.converterDe(cartaoSalvo);
        log.info("Resposta do endpoint de salvar cartão: ".concat(response.toJson()));
        return response;
    }

    private Cartao salvarCartaoDoPagarme(CartaoClientResponse cartaoClientResponse,
                                         Cliente cliente,
                                         JwtUsuarioResponse usuarioAutenticado) {
        return cartaoRepository.save(Cartao.converterDe(cartaoClientResponse, cliente, usuarioAutenticado.getId()));
    }

    private void validarDadosCartao(CartaoRequest cartaoRequest) {
        validarDadosNaoExistentes(cartaoRequest);
        validarCartaoCreditoValido(cartaoRequest);
    }

    private void validarDadosNaoExistentes(CartaoRequest cartaoRequest) {
        if (isEmpty(cartaoRequest)
            || isEmpty(cartaoRequest.getNumeroCartao())
            || isEmpty(cartaoRequest.getCvvCartao())
            || isEmpty(cartaoRequest.getNumeroCartao())
            || isEmpty(cartaoRequest.getNomeProprietarioCartao())
            || isEmpty(cartaoRequest.getCliente())) {
            throw new ValidacaoException("É necessário informar todos os dados do cartão.");
        }
    }

    private void validarCartaoIdJaExistente(String cartaoId) {
        if (cartaoRepository.existsByCartaoId(cartaoId)) {
            throw new ValidacaoException("Este cartão já existe.");
        }
    }

    private void validarCartaoCreditoValido(CartaoRequest cartaoRequest) {
        if (!new CreditCardValidator().isValid(cartaoRequest.getNumeroCartao())) {
            throw new ValidacaoException("O número do cartão de crédito não está válido.");
        }
    }

    public CartaoResponse buscarCartaoPorCartaoId(String cartaoId) {
        log.info("Realizando chamada ao endpoint de buscar cartão por cartaoId: ".concat(cartaoId));
        var usuario = jwtService.recuperarUsuarioAutenticado();
        var cartao = CartaoResponse.converterDe(cartaoRepository
            .findByCartaoId(cartaoId)
            .orElseGet(() -> salvarCartaoDoPagarme(integracaoCartaoService.buscarCartaoPorId(cartaoId),
                clienteService.buscarClientePorEmail(usuario.getEmail()), usuario)));
        if (isEmpty(cartao)) {
            throw new ValidacaoException("O cartão ".concat(cartaoId).concat(" não foi encontrado."));
        }
        log.info("Resposta da chamda de buscar cartão por cartaoId: ".concat(cartao.toJson()));
        return cartao;
    }

    public List<CartaoResponse> buscarCartaoPorUsuarioId() {
        var usuarioId = jwtService.recuperarUsuarioAutenticado().getId();
        log.info("Realizando chamada ao endpoint de buscar cartões por usuarioId: ".concat(usuarioId));
        var cartoes = cartaoRepository
            .findByUsuarioId(usuarioId)
            .stream()
            .map(CartaoResponse::converterDe)
            .collect(Collectors.toList());
        log.info("Resposta da chamda de buscar cartão por cartaoId: ".concat(converterJsonParaString(cartoes)));
        return cartoes;
    }
}