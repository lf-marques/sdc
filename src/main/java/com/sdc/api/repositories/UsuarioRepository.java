package com.sdc.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.sdc.api.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	@Transactional(readOnly = true)
	Usuario findByCpf(String cpf);
}
