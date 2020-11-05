package com.sdc.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdc.api.dtos.CartaoDto;
import com.sdc.api.entities.Cartao;
import com.sdc.api.controllers.UsuarioController;
import com.sdc.api.dtos.ClienteDto;
import com.sdc.api.dtos.EnderecoDto;
import com.sdc.api.dtos.UsuarioCadastroDto;
import com.sdc.api.dtos.UsuarioDto;
import com.sdc.api.entities.Usuario;
import com.sdc.api.entities.Cliente;
import com.sdc.api.entities.Endereco;

public class ConversaoUtils {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	public static Usuario Converter(UsuarioCadastroDto usuarioCadastroDto) throws ParseException {

		Usuario usuario = new Usuario();
		
		if (usuarioCadastroDto.getId() != null && usuarioCadastroDto.getId() != "")
			usuario.setId(Integer.parseInt(usuarioCadastroDto.getId()));
		
		usuario.setCpf(usuarioCadastroDto.getCpf());
		usuario.setNome(usuarioCadastroDto.getNome());
		usuario.setSenha(usuarioCadastroDto.getSenha());
		
		return usuario;
	}
	
	public static Cliente ConverterUsuarioCliente(UsuarioCadastroDto usuarioCadastroDto) throws ParseException {
		
		SimpleDateFormat dateParse = new SimpleDateFormat("dd/MM/yyyy");
		
		Usuario usuario = Converter(usuarioCadastroDto);
		Cliente cliente = new Cliente();
		
		cliente.setEmail(usuarioCadastroDto.getCliente().getEmail());
		cliente.setDataNascimento(dateParse.parse(usuarioCadastroDto.getCliente().getDataNascimento()));
		cliente.setRg(usuarioCadastroDto.getCliente().getRg());
		cliente.setSexo(usuarioCadastroDto.getCliente().getSexo());
		
		cliente.setUsuario(usuario);
		return cliente;
	}
	

	
	public static Endereco ConverterEndereco(UsuarioCadastroDto usuarioCadastroDto) throws ParseException {
		
		Endereco endereco = new Endereco();
		Cliente cliente   = ConverterUsuarioCliente(usuarioCadastroDto);		
		
		endereco.setCidade(usuarioCadastroDto.getCliente().getEndereco().getCidade());
		endereco.setNumero(Integer.parseInt(usuarioCadastroDto.getCliente().getEndereco().getNumero()));
		endereco.setRua(usuarioCadastroDto.getCliente().getEndereco().getRua());
		endereco.setUf(usuarioCadastroDto.getCliente().getEndereco().getUf());
		endereco.setCliente(cliente);
		
		return endereco;
	}
	
	public static UsuarioCadastroDto Converter(Usuario usuario) {
		
		UsuarioCadastroDto usuarioCadastroDto 	= new UsuarioCadastroDto();
		
		usuarioCadastroDto.setCpf(usuario.getCpf());
		usuarioCadastroDto.setNome(usuario.getNome());
		usuarioCadastroDto.setSenha(usuario.getSenha());
		
		return usuarioCadastroDto;
	}
	
	public static CartaoDto Converter(Cartao cartao) {

		CartaoDto cartaoDto = new CartaoDto();

		cartaoDto.setId(String.valueOf(cartao.getId()));
		cartaoDto.setNumero(cartao.getNumero());
		cartaoDto.setDataValidade(cartao.getDataValidade().toString());
		cartaoDto.setBloqueado(String.valueOf(cartao.getBloqueado()));
		cartaoDto.setClienteId(String.valueOf(cartao.getCliente().getId()));

		return cartaoDto;
	}
	
	public static Cartao Converter(CartaoDto cartaoDto) throws ParseException {
		Cartao cartao = new Cartao();

		if (cartaoDto.getId() != null && cartaoDto.getId() != "")
			cartao.setId(Integer.parseInt(cartaoDto.getId()));

		cartao.setNumero(cartaoDto.getNumero());
		cartao.setDataValidade(new SimpleDateFormat("MM/yyyy").parse(cartaoDto.getDataValidade()));
		cartao.setBloqueado(Integer.parseInt(cartaoDto.getBloqueado()));

		Cliente cliente = new Cliente();
		cliente.setId(Integer.parseInt(cartaoDto.getClienteId()));

		cartao.setCliente(cliente);

		return cartao;
	}
	
	public static List<CartaoDto> ConverterLista(List<Cartao> lista) {

		List<CartaoDto> lst = new ArrayList<CartaoDto>(lista.size());

		for (Cartao cartao : lista) {
			lst.add(Converter(cartao));
		}

		return lst;
	}
	
	public static Cliente Converter(ClienteDto clienteDto) throws ParseException {

		Usuario usuario = new Usuario();
		Cliente cliente = new Cliente();
		Endereco endereco = new Endereco();
		List<Endereco> enderecos = new ArrayList<Endereco>();

		if (clienteDto.getId( )!= null && clienteDto.getId() != "")
			cliente.setId(Integer.parseInt(clienteDto.getId()));
		
		if (clienteDto.getUsuarioId() != null && clienteDto.getUsuarioId() != "")
			usuario.setId(Integer.parseInt(clienteDto.getUsuarioId()));
		
		if(clienteDto.getEndereco().getId() != null && clienteDto.getEndereco().getId() != "")
			endereco.setId(Integer.parseInt(clienteDto.getEndereco().getId()));
		
		cliente.setEmail(clienteDto.getEmail());
		cliente.setDataNascimento(new SimpleDateFormat("dd/mm/yyyy").parse(clienteDto.getDataNascimento()));
		cliente.setRg(clienteDto.getRg());
		cliente.setSexo(clienteDto.getSexo());
		
		endereco.setCliente(cliente);
		endereco.setCidade(clienteDto.getEndereco().getCidade());
		endereco.setNumero(Integer.parseInt(clienteDto.getEndereco().getNumero()));
		endereco.setRua(clienteDto.getEndereco().getRua());
		endereco.setUf(clienteDto.getEndereco().getUf());
		enderecos.add(endereco);
		
		cliente.setUsuario(usuario);
		cliente.setEnderecos(enderecos);
		return cliente;
	}
	
	public static ClienteDto Converter(Cliente cliente) {

		Endereco endereco = cliente.getEnderecos().get(0);
		ClienteDto clienteDto = new ClienteDto();
		EnderecoDto enderedoDto = new EnderecoDto();
		
		if(cliente.getId() > 0) 
			clienteDto.setId(String.valueOf(cliente.getId()));
			clienteDto.setUsuarioId(String.valueOf(cliente.getUsuario().getId()));

		clienteDto.setEmail(cliente.getEmail());
		clienteDto.setDataNascimento(String.valueOf(cliente.getDataNascimento()));
		clienteDto.setRg(cliente.getRg());
		clienteDto.setSexo(cliente.getSexo());
		
		if(endereco.getId() > 0) {
			enderedoDto.setId(String.valueOf(endereco.getId()));
			enderedoDto.setCidade(endereco.getCidade());
			enderedoDto.setNumero(String.valueOf(endereco.getNumero()));
			enderedoDto.setRua(endereco.getRua());
			enderedoDto.setUf(endereco.getUf());
			enderedoDto.setRua(endereco.getRua());
			
			clienteDto.setEndereco(enderedoDto);
		}

		return clienteDto;
	}
}
