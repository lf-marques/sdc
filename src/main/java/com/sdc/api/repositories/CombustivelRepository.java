package com.sdc.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Combustivel;

public interface CombustivelRepository extends JpaRepository<Combustivel, Integer> {

	@Transactional(readOnly = true)
	Optional<Combustivel> findByTipo(int tipo);
}
