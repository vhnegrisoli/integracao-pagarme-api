package br.com.biot.integracaopagarmeapi.modulos.transacao.service;

import br.com.biot.integracaopagarmeapi.config.exception.ValidacaoException;
import br.com.biot.integracaopagarmeapi.modulos.cartao.service.CartaoService;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientRequest;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.transacao.TransacaoClientResponse;
import br.com.biot.integracaopagarmeapi.modulos.integracao.service.IntegracaoTransacaoService;
import br.com.biot.integracaopagarmeapi.modulos.jwt.service.JwtService;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.CobrancaRequest;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.EnderecoCobrancaRequest;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.ItemTransacaoRequest;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.TransacaoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.biot.integracaopagarmeapi.modulos.transacao.enums.TransacaoStatus.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class TransacaoService {

    @Autowired
    private IntegracaoTransacaoService integracaoTransacaoService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private JwtService jwtService;

    public TransacaoClientResponse salvarTransacao(TransacaoRequest transacaoRequest) {
        try {
            log.info("Realizando chamada ao endpoint de salvar e capturar transações com dados: ".concat(transacaoRequest.toJson()));
            validarDadosTransacao(transacaoRequest);
            var usuario = jwtService.recuperarUsuarioAutenticado();
            var transacaoClientRequest =  TransacaoClientRequest.converterDe(usuario, transacaoRequest);
            var transacaoRealizada = realizarTransacaoPagarme(transacaoClientRequest);
            validarTransacaoAprovada(transacaoRealizada);
            capturarTransacaoPagarme(transacaoRealizada);
            log.info("Resposta da chamada de realização de transações: ".concat(transacaoRealizada.toJson()));
            return transacaoRealizada;
        } catch (Exception ex) {
            log.error("Erro ao salvar transação: ", ex);
            throw new ValidacaoException("Erro ao salvar transação: " + ex.getMessage());
        }
    }

    private TransacaoClientResponse realizarTransacaoPagarme(TransacaoClientRequest transacaoClientRequest) {
        return integracaoTransacaoService
            .salvarTransacao(transacaoClientRequest);
    }

    private void validarTransacaoAprovada(TransacaoClientResponse transacaoResponse) {
        var status = transacaoResponse.getStatus();
        if (isEmpty(status)) {
            throw new ValidacaoException("A transação não retornou status.");
        }
        log.info("Status da transação ".concat(String.valueOf(transacaoResponse.getId())).concat(": ").concat(status));
        if (!possuiStatusValidos(status)) {
            throw new ValidacaoException("A transação foi recusada na Pagar.me com status: ".concat(status));
        }
    }

    private TransacaoClientResponse capturarTransacaoPagarme(TransacaoClientResponse transacaoRealizada) {
        var transacaoId = String.valueOf(transacaoRealizada.getId());
        if (transacaoRealizada.getStatus().equals(AUTORIZADA.getStatusPagarme())) {
            log.info("A transação".concat(transacaoId).concat(" está AUTORIZADA. Poderá ser feita a captura."));
            return integracaoTransacaoService
                .capturarTransacao(transacaoRealizada.getId());
        }
        if (transacaoRealizada.getStatus().equals(ANALISANDO.getStatusPagarme())) {
            log.info("A transação ".concat(transacaoId).concat(" não pode ser capturada pois está em análise."));
        }
        if (transacaoRealizada.getStatus().equals(PAGA.getStatusPagarme())) {
            log.info("A transação ".concat(transacaoId).concat(" já está paga e capturada."));
        }
        return transacaoRealizada;
    }

    private void validarDadosTransacao(TransacaoRequest request) {
        validarDadosRequest(request);
        validarDadosCobranca(request.getCobranca());
        validarDadosEndereco(request.getCobranca().getEndereco());
        request.getItens().forEach(this::validarDadosItem);
    }

    private void validarDadosRequest(TransacaoRequest request) {
        if (isEmpty(request)
            || isEmpty(request.getCartaoId())
            || isEmpty(request.getNumerosTelefone())
            || isEmpty(request.getCobranca())
            || isEmpty(request.getTotal())
            || isEmpty(request.getItens())) {
            throw new ValidacaoException("É necessário informar todos os seguintes campos: "
                .concat("cartaoId, números de telefone, cobranca, itens e total."));
        }
    }

    private void validarDadosCobranca(CobrancaRequest cobrancaRequest) {
        if (isEmpty(cobrancaRequest.getNome())
            || isEmpty(cobrancaRequest.getEndereco())) {
            throw new ValidacaoException("É necessário informar os campos de nome e endereço da entidade de cobrança.");
        }
    }

    private void validarDadosEndereco(EnderecoCobrancaRequest enderecoCobrancaRequest) {
        if (isEmpty(enderecoCobrancaRequest.getEstado())
            || isEmpty(enderecoCobrancaRequest.getCidade())
            || isEmpty(enderecoCobrancaRequest.getNumeroRua())
            || isEmpty(enderecoCobrancaRequest.getRua())
            || isEmpty(enderecoCobrancaRequest.getCep())) {
            throw new ValidacaoException("É necessário informar estado, cidade, rua,"
                + " número da rua e o cep do endereço de cobrança.");
        }
    }

    private void validarDadosItem(ItemTransacaoRequest itemTransacaoRequest) {
        if (isEmpty(itemTransacaoRequest.getId())
            || isEmpty(itemTransacaoRequest.getPrecoUnitario())
            || isEmpty(itemTransacaoRequest.getTitulo())
            || isEmpty(itemTransacaoRequest.getQuantidade())) {
         throw new ValidacaoException("É necessário informar os campos dos itens:"
             + " id, preço unitário, título e quantidade.");
        }
    }
}
