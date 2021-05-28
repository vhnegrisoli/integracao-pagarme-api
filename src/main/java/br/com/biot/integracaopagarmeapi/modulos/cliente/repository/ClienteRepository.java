package br.com.biot.integracaopagarmeapi.modulos.cliente.repository;

import br.com.biot.integracaopagarmeapi.modulos.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByEmail(String nome);

    Boolean existsByEmail(String nome);
}
