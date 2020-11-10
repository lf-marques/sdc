package com.sdc.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	@Transactional(readOnly = true)
	Optional<Cliente> findByRg(String rg);
}
