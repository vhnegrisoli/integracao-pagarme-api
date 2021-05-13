package br.com.biot.integracaopagarmeapi.modulos.cartao.repository;

import br.com.biot.integracaopagarmeapi.modulos.cartao.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Integer> {

    List<Cartao> findByUsuarioId(String usuarioId);

    Optional<Cartao> findByCartaoId(String cartaoId);

    Boolean existsByCartaoId(String cartaoId);
}
