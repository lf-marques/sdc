package com.sdc.api.services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Usuario;
import com.sdc.api.repositories.ClienteRepository;
import com.sdc.api.repositories.UsuarioRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Endereco;
import com.sdc.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
	
	//IMCOMPLETO
	//IMCOMPLETO
	//IMCOMPLETO
	
	@MockBean
	private ClienteRepository clienteRepository;
	
	@MockBean
	private UsuarioRepository usuarioRepository;

 
	@Test
	public void testBuscarPorIdExistente() throws ConsistenciaException {

	}


	/*@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException {

		clienteService.buscarPorId(-2);

	}*/

 
	/*@Test
	public void testBuscarPorRg() throws ConsistenciaException {
		BDDMockito.given(clienteRepository.findByRg(Mockito.anyString())).willReturn(Optional.of(new Cliente()));

		Optional<Cliente> resultado = clienteService.buscarPorRg("99930632");

		assertTrue(resultado.isPresent());
	}*/

	/*
	 * @Test
	public void testSalvar() throws ConsistenciaException {
		
		Cliente cliente = new Cliente();
		Usuario usuario = new Usuario();
		usuario.setId(1);
		cliente.setUsuario(usuario);
		
		BDDMockito.given(usuarioRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Usuario()));
		
		BDDMockito.given(clienteRepository.save(Mockito.any(Cliente.class))).willReturn(new Cliente());

		Cliente resultado = clienteService.salvar(cliente);

		assertNotNull(resultado);
	}
	 */
}

