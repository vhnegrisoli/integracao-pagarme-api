package br.com.biot.integracaopagarmeapi.modulos.cliente.repository;

import br.com.biot.integracaopagarmeapi.modulos.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {}
