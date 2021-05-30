package br.com.biot.integracaopagarmeapi.modulos.transacao.repository;

import br.com.biot.integracaopagarmeapi.modulos.transacao.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    Optional<Transacao> findByTransacaoId(Long transacaoId);
}
