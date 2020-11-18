package com.sdc.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Cliente;
import com.sdc.api.repositories.CartaoRepository;
import com.sdc.api.repositories.ClienteRepository;
import com.sdc.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CartaoServiceTest {
	
	@MockBean
	private CartaoRepository cartaoRepository;
	@MockBean
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CartaoService cartaoService;

	@Test
	public void testBuscarPorIdExistente() throws ConsistenciaException {
		
		BDDMockito.given(cartaoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Cartao()));

		Optional<Cartao> resultado = cartaoService.buscarPorId(1);

		assertTrue(resultado.isPresent());
	}
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdInexistente() throws ConsistenciaException {

		BDDMockito.given(cartaoRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());

		cartaoService.buscarPorId(1);
	}
	@Test
	public void testBuscarPorCartaoIdComSucesso() throws ConsistenciaException {

		List<Cartao> lstCartao = new ArrayList<Cartao>();
		lstCartao.add(new Cartao());

		BDDMockito.given(cartaoRepository.findByClienteId(Mockito.anyInt())).willReturn((lstCartao));

		Optional<List<Cartao>> resultado = cartaoService.buscarPorClienteId(1);

		assertTrue(resultado.isPresent());
	}
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorCartaoIdSemSucesso() throws ConsistenciaException {
		
		BDDMockito.given(cartaoRepository.findByClienteId(Mockito.anyInt())).willReturn(null);

		cartaoService.buscarPorClienteId(1);
	}
	@Test
	public void testSalvarComSucesso() throws ConsistenciaException {
		
		Cliente cliente = new Cliente();
		Cartao cartao = new Cartao();
		cliente.setId(1);
		cartao.setCliente(cliente);
		
		BDDMockito.given(clienteRepository.findById(Mockito.anyInt())).willReturn(Optional.of(cliente));
		
		BDDMockito.given(cartaoRepository.save(Mockito.any(Cartao.class))).willReturn(new Cartao());
		
		Cartao resultado = cartaoService.salvar(cartao);

		assertNotNull(resultado);
	}
	@Test(expected = ConsistenciaException.class)
	public void testSalvarSemSucesso() throws ConsistenciaException {
		
		Cliente cliente = new Cliente();
		Cartao cartao = new Cartao();
		cliente.setId(0);
		cartao.setCliente(cliente);
		cartao.setNumero("12315645648947987897988789789");
		
		BDDMockito.given(clienteRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
		
		BDDMockito.given(cartaoRepository.save(Mockito.any(Cartao.class))).willReturn(new Cartao());
		
		cartaoService.salvar(cartao);
	}
	@Test
	public void testValidarComSucesso() throws ParseException, ConsistenciaException {
		
		Cartao cartao = new Cartao();
		cartao.setId(1);
		cartao.setBloqueado(0);
		cartao.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2021"));
		
		BDDMockito.given(cartaoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(cartao));
		
		cartaoService.validar(cartao.getId());
	}
	@Test(expected = ConsistenciaException.class)
	public void testValidarSemSucesso() throws ParseException, ConsistenciaException {
		
		Cartao cartao = new Cartao();
		cartao.setId(1);
		cartao.setBloqueado(1);
		cartao.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		
		BDDMockito.given(cartaoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(cartao));
		
		cartaoService.validar(cartao.getId());
	}
}