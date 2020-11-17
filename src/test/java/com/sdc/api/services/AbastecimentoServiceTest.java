package com.sdc.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.sdc.api.entities.Abastecimento;
import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Combustivel;
import com.sdc.api.repositories.AbastecimentoRepository;
import com.sdc.api.repositories.CartaoRepository;
import com.sdc.api.repositories.CombustivelRepository;
import com.sdc.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AbastecimentoServiceTest {
	
	@MockBean
	private CombustivelRepository combustivelRepository;
	@MockBean
	private AbastecimentoRepository abastecimentoRepository;
	@MockBean
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private AbastecimentoService abastecimentoService;
	
	
	@Test
	public void testBuscarPorTipoExistente() throws ConsistenciaException {
		
		BDDMockito.given(abastecimentoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Abastecimento()));

		Optional<Abastecimento> resultado = abastecimentoService.buscarPorId(1);

		assertTrue(resultado.isPresent());
	}
	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorIdNaoExistente() throws ConsistenciaException {

		BDDMockito.given(abastecimentoRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());

		abastecimentoService.buscarPorId(1);
	}
	@Test
	public void testSalvarComSucesso() throws ConsistenciaException, ParseException {
		
		Abastecimento abastecimento = new Abastecimento();
		Combustivel combustivel = new Combustivel();
		Cartao cartao = new Cartao();
		
		cartao.setId(1);
		cartao.setBloqueado(0);
		cartao.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2021"));
		combustivel.setId(1);
		abastecimento.setCombustivel(combustivel);
		abastecimento.setCartao(cartao);
		
		BDDMockito.given(cartaoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(cartao));
		
		BDDMockito.given(combustivelRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Combustivel()));
		
		BDDMockito.given(abastecimentoRepository.save(Mockito.any(Abastecimento.class))).willReturn(new Abastecimento());

		Abastecimento resultado = abastecimentoService.salvar(abastecimento);

		assertNotNull(resultado);
	}
	@Test(expected = ConsistenciaException.class)
	public void testSalvarSemSucesso() throws ConsistenciaException, ParseException {
		
		Abastecimento abastecimento = new Abastecimento();
		Combustivel combustivel = new Combustivel();
		Cartao cartao = new Cartao();
		
		cartao.setId(1);
		cartao.setBloqueado(1);
		cartao.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		combustivel.setId(1);
		abastecimento.setCombustivel(combustivel);
		abastecimento.setCartao(cartao);
		
		BDDMockito.given(cartaoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(cartao));
		
		BDDMockito.given(combustivelRepository.findById(Mockito.anyInt())).willReturn(Optional.of(new Combustivel()));
		
		BDDMockito.given(abastecimentoRepository.save(Mockito.any(Abastecimento.class))).willReturn(new Abastecimento());

		abastecimentoService.salvar(abastecimento);
	}
}
