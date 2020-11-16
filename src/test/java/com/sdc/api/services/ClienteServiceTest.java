package com.sdc.api.services;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Endereco;
import com.sdc.api.entities.Usuario;
import com.sdc.api.repositories.ClienteRepository;
import com.sdc.api.utils.ConsistenciaException;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
	
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	private Cliente clienteTeste;
	
	private void CriarClienteTestes() throws ParseException {
		
		clienteTeste = new Cliente();
		
		clienteTeste.setSexo("M");
		clienteTeste.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2000"));
		clienteTeste.setRg("111111111");
		clienteTeste.setEmail("emailTeste@email.com");

	}
	
	
	@Before
	public void setUp() throws Exception {
		
		CriarClienteTestes();
		clienteRepository.save(clienteTeste);
		
	}
	
	@After
	public void tearDown() throws Exception {
		
		clienteRepository.deleteAll();
		
	}

	@Test
	public void testBuscarPorId() throws ConsistenciaException {
		BDDMockito.given(clienteRepository.findById(Mockito.anyInt()))
		.willReturn(Optional.of(new Cliente()));
	
	Optional<Cliente> resultado = clienteService.buscarPorId(1);
	
	assertTrue(resultado.isPresent());
	
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException {	
			
	clienteService.buscarPorId(-2);
		
	}

	

	/*@Test
	public void testSalvar() {
		fail("Not yet implemented");
	}*/

}
