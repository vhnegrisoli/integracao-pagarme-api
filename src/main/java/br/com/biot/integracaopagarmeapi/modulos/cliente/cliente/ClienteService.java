package br.com.biot.integracaopagarmeapi.modulos.cliente.cliente;

import br.com.biot.integracaopagarmeapi.config.exception.ValidacaoException;
import br.com.biot.integracaopagarmeapi.modulos.cliente.dto.ClienteRequest;
import br.com.biot.integracaopagarmeapi.modulos.cliente.dto.EnderecoRequest;
import br.com.biot.integracaopagarmeapi.modulos.cliente.model.Cliente;
import br.com.biot.integracaopagarmeapi.modulos.cliente.model.Documento;
import br.com.biot.integracaopagarmeapi.modulos.cliente.model.Endereco;
import br.com.biot.integracaopagarmeapi.modulos.cliente.repository.ClienteRepository;
import br.com.biot.integracaopagarmeapi.modulos.cliente.repository.DocumentoRepository;
import br.com.biot.integracaopagarmeapi.modulos.cliente.repository.EnderecoRepository;
import br.com.biot.integracaopagarmeapi.modulos.integracao.service.IntegracaoClienteService;
import br.com.biot.integracaopagarmeapi.modulos.jwt.dto.JwtUsuarioResponse;
import br.com.biot.integracaopagarmeapi.modulos.transacao.dto.ClienteDocumentoDto;
import br.com.biot.integracaopagarmeapi.modulos.integracao.dto.ClienteClientRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class ClienteService {

    private static final String PAIS_BRASIL = "br";

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private IntegracaoClienteService integracaoClienteService;

    public Cliente definirCliente(ClienteRequest clienteRequest,
                                  JwtUsuarioResponse usuarioAutenticado) {
        try {
            definirDadosDoCliente(clienteRequest, usuarioAutenticado);
            if (!existeClientePorEmail(clienteRequest.getEmail()))  {
                return salvarCliente(clienteRequest);
            }
            return buscarClientePorEmail(clienteRequest.getEmail());
        } catch (Exception ex) {
            var mensagemErro = "Erro ao tentar salvar ou recuperar o cliente.";
            log.error(mensagemErro, ex);
            throw new ValidacaoException(mensagemErro);
        }
    }

    private void definirDadosDoCliente(ClienteRequest clienteRequest,
                                       JwtUsuarioResponse usuarioAutenticado) {
        validarDadosDoCliente(clienteRequest);
        validarDadosEndereco(clienteRequest.getEndereco());
        enriquecerDadosCliente(clienteRequest, usuarioAutenticado);
    }

    private void enriquecerDadosCliente(ClienteRequest clienteRequest,
                                        JwtUsuarioResponse usuarioAutenticado) {
        clienteRequest.setDocumentos(Collections.singletonList(usuarioAutenticado.getCpf()));
        clienteRequest.setNome(usuarioAutenticado.getNome());
        clienteRequest.setEmail(usuarioAutenticado.getEmail());
        clienteRequest.getEndereco().setPais(PAIS_BRASIL);
        clienteRequest.setUsuarioId(usuarioAutenticado.getId());
    }

    private boolean existeClientePorEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    private Cliente salvarCliente(ClienteRequest clienteRequest) {
        var clientePagarme = integracaoClienteService.salvarCliente(ClienteClientRequest.converterDe(clienteRequest));
        var cliente = clienteRepository.save(Cliente.converterDe(clientePagarme));
        salvarDocumentos(clientePagarme.getDocumentos(), cliente);
        salvarEndereco(clienteRequest.getEndereco(), cliente);
        return cliente;
    }

    public Cliente buscarClientePorEmail(String email) {
        return clienteRepository
            .findByEmail(email)
            .orElseThrow(() -> new ValidacaoException("O cliente não foi encontrado pelo email ".concat(email)));
    }

    private void salvarDocumentos(List<ClienteDocumentoDto> documentos, Cliente cliente) {
        documentoRepository.saveAll(documentos
            .stream()
            .map(documento -> Documento.converterDe(documento.getNumeroDocumento(), cliente))
            .collect(Collectors.toList())
        );
    }

    private void salvarEndereco(EnderecoRequest enderecoRequest, Cliente cliente) {
        enderecoRepository.save(Endereco.converterDe(enderecoRequest, cliente));
    }

    private void validarDadosEndereco(EnderecoRequest enderecoRequest) {
        if (isEmpty(enderecoRequest.getEstado())
            || isEmpty(enderecoRequest.getCidade())
            || isEmpty(enderecoRequest.getRua())
            || isEmpty(enderecoRequest.getNumeroRua())
            || isEmpty(enderecoRequest.getCep())) {
            throw new ValidacaoException("Os dados de endereço são necessários.");
        }
    }

    private void validarDadosDoCliente(ClienteRequest clienteRequest) {
        if (isEmpty(clienteRequest)
            || isEmpty(clienteRequest.getTelefones())
            || isEmpty(clienteRequest.getEndereco())) {
            throw new ValidacaoException("É necessário informar os dados de telefone e endereço do cliente.");
        }
    }
}
