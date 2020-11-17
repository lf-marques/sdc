package com.sdc.api.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Endereco;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EnderecoRepositoryTest {
	@Autowired
	EnderecoRepository enderecoRepository;
	@Autowired
	ClienteRepository clienteRepository;

	Endereco enderecoTeste;
	Cliente clienteTeste;

	private void CriarAbastecimentoTestes() throws ParseException {

		clienteTeste = new Cliente();

		clienteTeste.setId(1);
		clienteTeste.setRg("058870980");
		clienteTeste.setSexo("M");
		clienteTeste.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		clienteTeste.setEmail("cliente@gmail.com");

		enderecoTeste = new Endereco();
		List<Endereco> enderecos = new ArrayList<Endereco>();

		enderecoTeste.setId(1);
		enderecoTeste.setCidade("Londrina");
		enderecoTeste.setNumero(358);
		enderecoTeste.setUf("PR");
		enderecoTeste.setRua("Aquela la");
		enderecos.add(enderecoTeste);
		enderecoTeste.setCliente(clienteTeste);

		clienteTeste.setEnderecos(enderecos);

	}

	@Before
	public void setUp() throws Exception {

		CriarAbastecimentoTestes();
		clienteRepository.save(clienteTeste);
		enderecoRepository.save(enderecoTeste);

	}

	@After
	public void tearDown() throws Exception {

		enderecoRepository.deleteAll();
		clienteRepository.deleteAll();

	}

	@Test
	public void testFindByClienteId() {
		List<Endereco> enderecos = enderecoRepository.findByClienteId(clienteTeste.getId());
		assertTrue(!enderecos.isEmpty());
	}

}
