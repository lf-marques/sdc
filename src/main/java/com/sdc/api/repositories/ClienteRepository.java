package com.sdc.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sdc.api.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	@Transactional(readOnly = true)
	Optional<Cliente> findByRg(String rg);
	
	@Query("SELECT cli FROM Cliente cli WHERE cli.usuario.id = :usuarioId")
	Optional<Cliente> findByUsuarioId(@Param("usuarioId") int usuarioId);
}