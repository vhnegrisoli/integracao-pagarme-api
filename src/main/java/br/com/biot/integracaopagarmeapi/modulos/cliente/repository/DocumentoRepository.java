package br.com.biot.integracaopagarmeapi.modulos.cliente.repository;

import br.com.biot.integracaopagarmeapi.modulos.cliente.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

    List<Documento> findByClienteId(Integer clienteId);
}
