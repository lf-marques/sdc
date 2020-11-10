package com.sdc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sdc.api.entities.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
	@Query("SELECT ender FROM Endereco ender WHERE ender.cliente.id = :clienteId")
   	List<Endereco> findByClienteId(@Param("clienteId") int clienteId);
}
