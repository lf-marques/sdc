package com.sdc.api.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.sdc.api.dtos.RegraDto;
import com.sun.istack.NotNull;

public class UsuarioCadastroDto {
	
	public UsuarioCadastroDto() {
		this.cliente = new ClienteDto();
		this.regras = new ArrayList<RegraDto>();
	}
	
	private String id;

	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 5, max = 100, message = "Nome deve conter entre 5 e 100 caracteres.")
	private String nome;

	@NotEmpty(message = "CPF não pode ser vazio.")
	@CPF(message = "CPF inválido.")
	private String cpf;

	@NotEmpty(message = "Senha não pode ser vazia.")
	@Length(min = 8, message = "Senha deve conter no minimo 8 caracteres.")
	private String senha;
	
	@NotEmpty(message = "Tipo não pode ser vazio.")
	private String tipo;

	@Valid
	@NotNull
	private List<RegraDto> regras;
	
	@Valid
	@NotNull
	private ClienteDto cliente;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<RegraDto> getRegras() {
		return regras;
	}

	public void setRegras(List<RegraDto> regras) {
		this.regras = regras;
	}
	
	public ClienteDto getCliente() {
		return this.cliente;
	}
	
	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Usuario[id=" + id + "," 
				+ "nome=" + nome + "," 
				+ "cpf=" + cpf + "," 
				+ "tipo=" + tipo + "," 
				+ cliente.toString() + "]";
	}

}
