package br.com.biot.integracaopagarmeapi.modulos.transacao.repository;

import br.com.biot.integracaopagarmeapi.modulos.transacao.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
}
