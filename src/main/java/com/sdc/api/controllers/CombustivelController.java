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
import com.sdc.api.entities.Combustivel;
import com.sdc.api.services.CombustivelService;
import com.sdc.api.response.Response;

@RestController
@RequestMapping("/api/combustivel")
@CrossOrigin(origins = "*")
public class CombustivelController {

	private static final Logger log = LoggerFactory.getLogger(CombustivelController.class);

	@Autowired
	private CombustivelService combustivelService;

	@GetMapping(value = "/tipo/{tipo}")
	public /* ResponseEntity<Response<Combustivel>> */ void buscarPorTipo(@PathVariable("tipo") int tipo) {

	}

	@PostMapping
	public /* ResponseEntity<Response<Combustivel>> */ void salvar(@RequestBody Combustivel combustivel) {

	}
}
