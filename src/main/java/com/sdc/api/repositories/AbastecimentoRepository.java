package com.sdc.api.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sdc.api.entities.Abastecimento;

public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Integer> {
	@Query("SELECT abast FROM Abastecimento abast "
			+ "WHERE abast.cartao.cliente.usuario.id = :clienteId")
   	List<Abastecimento> findByClienteId(@Param("clienteId") int clienteId);
}
