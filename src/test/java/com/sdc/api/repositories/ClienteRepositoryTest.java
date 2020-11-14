package com.sdc.api.repositories;

import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;  

	private Cliente clienteTeste;

	private void CriarClienteTestes() throws ParseException {

		clienteTeste = new Cliente();

		clienteTeste.setId(1);
		clienteTeste.setRg("058870980");
		clienteTeste.setSexo("M");
		clienteTeste.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		clienteTeste.setEmail("cliente@gmail.com");

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
	public void testFindByRg() {

		Optional<Cliente> cliente = clienteRepository.findByRg(clienteTeste.getRg());
		assertEquals(clienteTeste.getRg(), cliente.get().getRg());

	}
}