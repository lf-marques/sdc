package com.sdc.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sdc.api.utils.ConsistenciaException;
import com.sdc.api.dtos.ClienteDto;
import com.sdc.api.dtos.EnderecoDto;
import com.sdc.api.utils.ConversaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdc.api.entities.Cartao;
import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Endereco;
import com.sdc.api.entities.Regra;
import com.sdc.api.entities.Usuario;
import com.sdc.api.services.ClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private ClienteService clienteService;
	
	private Cliente CriarClienteTestes() throws ParseException {

		Cliente cliente = new Cliente();

		cliente.setId(1);
		cliente.setSexo("M");
		cliente.setRg("058870980");
		cliente.setEmail("email@email.com");
		cliente.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		
		Endereco enderecoTeste = new Endereco();
		List<Endereco> enderecos = new ArrayList<Endereco>();

		enderecoTeste.setId(1);
		enderecoTeste.setCidade("Londrina");
		enderecoTeste.setNumero(358);
		enderecoTeste.setUf("PR");
		enderecoTeste.setRua("Aquela la");
		enderecos.add(enderecoTeste);
		enderecoTeste.setCliente(cliente);

		cliente.setEnderecos(enderecos);
		
		Cartao cartaoTeste = new Cartao();
		List<Cartao> cartoes = new ArrayList<Cartao>();

		cartaoTeste.setId(1);
		cartaoTeste.setBloqueado(0);
		cartaoTeste.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		cartaoTeste.setNumero("1234567891234567");
		cartoes.add(cartaoTeste);
		cartaoTeste.setCliente(cliente);
		
		cliente.setCartoes(cartoes);
		
		Regra regraTeste = new Regra();
		List<Regra> regras = new ArrayList<Regra>();
		
		regraTeste.setNome("Patrao");
		regraTeste.setDescricao("Esse cara é o pika do sistema");
		regraTeste.setAtivo(true);
		regras.add(regraTeste);
		
		Usuario usuarioTeste = new Usuario();

		usuarioTeste.setId(1);
		usuarioTeste.setNome("Josefino");
		usuarioTeste.setCpf("12345678910");
		usuarioTeste.setSenha("senhalegal1234");
		usuarioTeste.setTipo(2);
		usuarioTeste.setRegras(regras);
		cliente.setUsuario(usuarioTeste);

		return cliente;

	}

	@Test
	@WithMockUser
	public void testBuscarPorIdComSucesso() throws Exception {
		
		Cliente cliente = CriarClienteTestes();

		BDDMockito.given(clienteService.buscarPorId(Mockito.anyInt())).willReturn(Optional.of(cliente));

		mvc.perform(MockMvcRequestBuilders.get("/api/cliente/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.dados.id").value(cliente.getId()))
				.andExpect(jsonPath("$.dados.rg").value(cliente.getRg()))
				.andExpect(jsonPath("$.dados.dataNascimento").value("Wed Jan 01 00:00:00 BRT 2020"))
				.andExpect(jsonPath("$.dados.email").value(cliente.getEmail()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
	@Test
	@WithMockUser
	public void testBuscarPorIdSemSucesso()  throws Exception {
	
		BDDMockito.given(clienteService.buscarPorId((Mockito.anyInt())))
				.willThrow(new ConsistenciaException("Teste inconsistência"));

		mvc.perform(MockMvcRequestBuilders.get("/api/cliente/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.erros").value("Teste inconsistência"));

	}

	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarComSucesso() throws Exception {
		Cliente cliente = CriarClienteTestes();
		ClienteDto objEntrada = ConversaoUtils.Converter(cliente);
		objEntrada.setDataNascimento("01/01/2020");
		
		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(clienteService.salvar(Mockito.any(Cliente.class))).willReturn(cliente);

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
				.andExpect(jsonPath("$.dados.id").value(cliente.getId()))
				.andExpect(jsonPath("$.dados.rg").value(cliente.getRg()))
				.andExpect(jsonPath("$.dados.dataNascimento").value("Wed Jan 01 00:00:00 BRT 2020"))
				.andExpect(jsonPath("$.dados.email").value(cliente.getEmail()))
				.andExpect(jsonPath("$.erros").isEmpty());
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarSemSucesso() throws Exception {

		Cliente cliente = CriarClienteTestes();
		ClienteDto objEntrada = ConversaoUtils.Converter(cliente);
		objEntrada.setDataNascimento("01/01/2020");

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		BDDMockito.given(clienteService.salvar(Mockito.any(Cliente.class)))
				.willThrow(new ConsistenciaException("Teste inconsistência."));

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarSexoEmBranco() throws Exception {
		
		ClienteDto objEntrada = new ClienteDto();
		EnderecoDto endereco = new EnderecoDto();
		endereco.setCidade("Londrina");
		endereco.setNumero("356");
		endereco.setRua("Aquela Rua La");
		endereco.setUf("PR");

		objEntrada.setRg("105887098");
		objEntrada.setDataNascimento("01/01/2020");
		objEntrada.setEmail("email@email.com");
		objEntrada.setEndereco(endereco);
		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Cliente: Sexo não pode ser vazio."));

	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarSexoExcedente() throws Exception {
		
		ClienteDto objEntrada = new ClienteDto();
		EnderecoDto endereco = new EnderecoDto();
		endereco.setCidade("Londrina");
		endereco.setNumero("356");
		endereco.setRua("Aquela Rua La");
		endereco.setUf("PR");

		objEntrada.setRg("105887098");
		objEntrada.setDataNascimento("01/01/2020");
		objEntrada.setEmail("email@email.com");
		objEntrada.setEndereco(endereco);
		objEntrada.setSexo("Masculino");
		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Cliente: Sexo deve conter 1 caractere (M/F)"));

	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarRgEmBranco() throws Exception {
		
		ClienteDto objEntrada = new ClienteDto();
		EnderecoDto endereco = new EnderecoDto();
		endereco.setCidade("Londrina");
		endereco.setNumero("356");
		endereco.setRua("Aquela Rua La");
		endereco.setUf("PR");

		objEntrada.setDataNascimento("01/01/2020");
		objEntrada.setEmail("email@email.com");
		objEntrada.setEndereco(endereco);
		objEntrada.setSexo("M");
		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Cliente: Rg não pode ser vazio."));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarRgExcedente() throws Exception {
		
		ClienteDto objEntrada = new ClienteDto();
		EnderecoDto endereco = new EnderecoDto();
		endereco.setCidade("Londrina");
		endereco.setNumero("356");
		endereco.setRua("Aquela Rua La");
		endereco.setUf("PR");

		objEntrada.setSexo("M");
		objEntrada.setRg("10588709888545");
		objEntrada.setDataNascimento("01/01/2020");
		objEntrada.setEmail("email@email.com");
		objEntrada.setEndereco(endereco);
		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Cliente: Rg deve conter 9 caracteres"));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarEmailEmBranco() throws Exception {
		
		ClienteDto objEntrada = new ClienteDto();
		EnderecoDto endereco = new EnderecoDto();
		endereco.setCidade("Londrina");
		endereco.setNumero("356");
		endereco.setRua("Aquela Rua La");
		endereco.setUf("PR");

		objEntrada.setSexo("M");
		objEntrada.setRg("058870988");
		objEntrada.setDataNascimento("01/01/2020");
		objEntrada.setEndereco(endereco);
		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Cliente: Email não pode ser vazio."));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarDataNascimentoEmBranco() throws Exception {
		
		ClienteDto objEntrada = new ClienteDto();
		EnderecoDto endereco = new EnderecoDto();
		endereco.setCidade("Londrina");
		endereco.setNumero("356");
		endereco.setRua("Aquela Rua La");
		endereco.setUf("PR");

		objEntrada.setSexo("M");
		objEntrada.setRg("105887098");
		objEntrada.setEmail("email@email.com");
		objEntrada.setEndereco(endereco);
		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Cliente: Data nascimnento não pode ser vazia."));
	}
	@Test
	@WithMockUser(roles = "CLIENTE")
	public void testSalvarDataNascimentoFormatoInvalido() throws Exception {
		
		ClienteDto objEntrada = new ClienteDto();
		EnderecoDto endereco = new EnderecoDto();
		endereco.setCidade("Londrina");
		endereco.setNumero("356");
		endereco.setRua("Aquela Rua La");
		endereco.setUf("PR");
		
		objEntrada.setEmail("email@email.com");
		objEntrada.setDataNascimento("01/01/20");
		objEntrada.setSexo("M");
		objEntrada.setRg("058870988");
		objEntrada.setEndereco(endereco);
		

		String json = new ObjectMapper().writeValueAsString(objEntrada);

		mvc.perform(MockMvcRequestBuilders.post("/api/cliente").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros").value("Cliente: Formato data de nascimento: dd/mm/yyyy"));
	}
}
