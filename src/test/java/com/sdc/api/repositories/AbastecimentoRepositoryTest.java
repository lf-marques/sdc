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

import com.sdc.api.entities.Abastecimento;
import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Combustivel;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AbastecimentoRepositoryTest {
	@Autowired
	AbastecimentoRepository abastecimentoRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	CartaoRepository cartaoRepository;
	@Autowired
	CombustivelRepository combustivelRepository;

	Abastecimento abastecimentoTeste;
	Cliente clienteTeste;
	Cartao cartaoTeste;
	Combustivel combustivelTeste;

	private void CriarAbastecimentoTestes() throws ParseException {
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

		combustivelTeste = new Combustivel();

		combustivelTeste.setId(1);
		combustivelTeste.setTipo(1);
		combustivelTeste.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		combustivelTeste.setTitulo("Gososa");
		combustivelTeste.setValor(200.00);

		abastecimentoTeste = new Abastecimento();

		abastecimentoTeste.setId(1);
		abastecimentoTeste.setPlaca("acbd1234");
		abastecimentoTeste.setCombustivel(combustivelTeste);
		abastecimentoTeste.setValor(250.00);
		abastecimentoTeste.setConfirmacaoAbastecimento(1);
		abastecimentoTeste.setConfirmacaoPagamento(1);
		abastecimentoTeste.setDataCriacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		abastecimentoTeste.setCartao(cartaoTeste);

	}

	@Before
	public void setUp() throws Exception {

		CriarAbastecimentoTestes();
		clienteRepository.save(clienteTeste);
		cartaoRepository.save(cartaoTeste);
		combustivelRepository.save(combustivelTeste);
		abastecimentoRepository.save(abastecimentoTeste);

	}

	@After
	public void tearDown() throws Exception {

		abastecimentoRepository.deleteAll();
		combustivelRepository.deleteAll();
		cartaoRepository.deleteAll();
		clienteRepository.deleteAll();

	}

	@Test
	public void testFindClienteById() {
		List<Abastecimento> abastecimentos = abastecimentoRepository.findByClienteId(clienteTeste.getId());
		assertTrue(!abastecimentos.isEmpty());
	}

}