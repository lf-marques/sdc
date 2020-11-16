package com.sdc.api.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CartaoRepositoryTest {
	@Autowired
	CartaoRepository cartaoRepository;
	@Autowired
	ClienteRepository clienteRepository;
	
	Cartao cartaoTeste;
	Cliente clienteTeste;
	
	private void CriarCartaoTestes() throws ParseException {

		clienteTeste = new Cliente();
		
		clienteTeste.setId(1);
		clienteTeste.setRg("058870980");
		clienteTeste.setSexo("M");
		clienteTeste.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		clienteTeste.setEmail("cliente@gmail.com");

		cartaoTeste = new Cartao();
		List<Cartao> cartoes = new ArrayList<Cartao>();

		cartaoTeste.setId(1);
		cartaoTeste.setBloqueado(0);
		cartaoTeste.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		cartaoTeste.setNumero("1234567891234567");
		cartoes.add(cartaoTeste);
		cartaoTeste.setCliente(clienteTeste);
		
		clienteTeste.setCartoes(cartoes);

	}

	@Before
	public void setUp() throws Exception {

		CriarCartaoTestes();
		clienteRepository.save(clienteTeste);
		cartaoRepository.save(cartaoTeste);

	}

	@After
	public void tearDown() throws Exception {

		cartaoRepository.deleteAll();

	}
	@Test
	public void testFindByNumero() {
		Optional<Cartao> cartao = cartaoRepository.findByNumero(cartaoTeste.getNumero());
		assertEquals(cartao.get().getNumero(), cartaoTeste.getNumero());
	}
	@Test
	public void testFindByClienteId() {
		List<Cartao> cartoes = cartaoRepository.findByClienteId(clienteTeste.getId());
		assertTrue(!cartoes.isEmpty());
	}
}
