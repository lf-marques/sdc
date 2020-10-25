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
import com.sdc.api.entities.Cliente;
import com.sdc.api.services.ClienteService;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	private ClienteService clienteService;

	@GetMapping(value = "/{id}")
	public /* ResponseEntity<Cliente> */ void buscarPorId(@PathVariable("id") int id) {

	}

	@GetMapping(value = "/rg/{rg}")
	public /* ResponseEntity<Cliente> */ void buscarPorRg(@PathVariable("rg") String rg) {

	}

	@GetMapping(value = "/email/{email}")
	public /* ResponseEntity<Cliente> */ void buscarPorEmail(@PathVariable("email") String email) {

	}

	@PostMapping
	public /* ResponseEntity<Cliente> */ void salvar(@RequestBody Cliente cliente) {

	}
}
