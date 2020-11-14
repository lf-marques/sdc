package com.sdc.api.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdc.api.dtos.ClienteDto;
import com.sdc.api.dtos.CombustivelDto;
import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Combustivel;
import com.sdc.api.services.CombustivelService;
import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.utils.ConversaoUtils;
import com.sdc.api.response.Response;

@RestController
@RequestMapping("/api/combustivel")
@CrossOrigin(origins = "*")
public class CombustivelController {

	private static final Logger log = LoggerFactory.getLogger(CombustivelController.class);

	@Autowired
	private CombustivelService combustivelService;

	@GetMapping(value = "/tipo/{tipo}")
	public ResponseEntity<Response<CombustivelDto>> buscarPorTipo(@PathVariable("tipo") int tipo) {
		Response<CombustivelDto> response = new Response<CombustivelDto>();

		try {
			log.info("Controller: buscando combustivel de tipo: {}", tipo);
			Optional<Combustivel> combustivel = combustivelService.buscarPorTipo(tipo);
			response.setDados(ConversaoUtils.Converter(combustivel.get()));
			return ResponseEntity.ok(response);

		} catch (ConsistenciaException e) {
			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);

		} catch (Exception e) {
			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO')")
	@PostMapping
	public ResponseEntity<Response<CombustivelDto>> salvar(@Valid @RequestBody CombustivelDto combustivelDto,
			BindingResult result) {
		Response<CombustivelDto> response = new Response<CombustivelDto>();

		try {
			
			log.info("Controller: salvando o combustivel: {}", combustivelDto.toString());

			if (result.hasErrors()) {

				for (int i = 0; i < result.getErrorCount(); i++) {
					response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
				}

				log.info("Controller: Os campos obrigatórios não foram preenchidos");
				return ResponseEntity.badRequest().body(response);

			}

			Combustivel combustivel = ConversaoUtils.Converter(combustivelDto);
			response.setDados(ConversaoUtils.Converter(this.combustivelService.salvar(combustivel)));

			return ResponseEntity.ok(response);

		} catch (ConsistenciaException e) {

			log.info("Controller: Inconsistência de dados: {}", e.getMessage());
			response.adicionarErro(e.getMensagem());
			return ResponseEntity.badRequest().body(response);

		} catch (Exception e) {

			log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
			response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
			return ResponseEntity.status(500).body(response);
		}
	}
}
