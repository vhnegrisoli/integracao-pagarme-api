package br.com.biot.integracaopagarmeapi.modulos.cliente.repository;

import br.com.biot.integracaopagarmeapi.modulos.cliente.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    List<Endereco> findByClienteId(Integer clienteId);
}
