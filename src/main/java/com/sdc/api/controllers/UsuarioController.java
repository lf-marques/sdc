package com.sdc.api.controllers;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sdc.api.entities.Usuario;
import com.sdc.api.services.UsuarioService;
import com.sdc.api.response.Response;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	 
	@Autowired
	private UsuarioService usuarioService;
	 
	@GetMapping(value = "/{id}")
	public /* ResponseEntity<Response<Usuario>> */ void buscarPorId(@PathVariable("id") int id) {
		log.info("Controller: buscando um usuário com o id: {}", id);
	
	}
	 
	@GetMapping(value = "/cpf/{cpf}")
	public /* ResponseEntity<Response<Usuario>> */ void buscarPorCpf(@PathVariable("cpf") String cpf) {
		log.info("Controller: buscando um usuário com o cpf: {}", cpf);
	}
	
	@PostMapping
	public /* ResponseEntity<Response<Usuario>> */ void salvar(@RequestBody Usuario usuario) {
	}
}
