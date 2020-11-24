package com.sdc.api.services;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
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
import com.sdc.api.entities.Endereco;
import com.sdc.api.repositories.ClienteRepository;
import com.sdc.api.repositories.EnderecoRepository;
import com.sdc.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EnderecoServiceTest {

	@MockBean
	private EnderecoRepository enderecoRepository;

	@MockBean
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoService enderecoService;

	private Endereco enderecoTeste;

	private void CriarEnderecoTestes() throws ParseException {

		enderecoTeste = new Endereco();

		enderecoTeste.setId(1);
		enderecoTeste.setCidade("Cidade");
		enderecoTeste.setUf("Uf");
		enderecoTeste.setRua("Rua");
		enderecoTeste.setNumero(1);

	}

	@Before
	public void setUp() throws Exception {

		CriarEnderecoTestes();
		enderecoRepository.save(enderecoTeste);

	}

	@After
	public void tearDown() throws Exception {

		enderecoRepository.deleteAll();

	}

	@Test
	public void testSalvarComSucesso() throws ConsistenciaException {
		
		Endereco endereco = new Endereco();
		Cliente cliente = new Cliente();
		cliente.setId(1);
		endereco.setCliente(cliente);

		BDDMockito.given(clienteRepository.findById(Mockito.anyInt()))
        .willReturn(Optional.of(new Cliente()));
		
		BDDMockito.given(enderecoRepository.save(Mockito.any(Endereco.class)))
        .willReturn(new Endereco());
		
		Endereco resultado = enderecoService.salvar(endereco);

		assertNotNull(resultado);
	}
	
	@Test(expected = ConsistenciaException.class)
	public void testSalvarSemSucesso() throws ConsistenciaException {
		
		Endereco endereco = new Endereco();
		Cliente cliente = new Cliente();
		cliente.setId(1);
		endereco.setCliente(cliente);
		
		enderecoService.salvar(endereco);
	}

}