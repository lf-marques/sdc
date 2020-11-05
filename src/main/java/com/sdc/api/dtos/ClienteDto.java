package com.sdc.api.dtos;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ClienteDto {

	public ClienteDto() {
		this.endereco = new EnderecoDto();
	}

	private String id;
	
	private String usuarioId;

	@NotNull(message = "Cliente: Sexo não pode ser vazio.")
	@Length(min = 1, max = 1, message = "Cliente: Sexo deve conter 1 caractere (M/F)")
	private String sexo;

	@NotEmpty(message = "Cliente: Data nascimnento não pode ser vazia.")
	@Length(min = 10, max = 10, message = "Cliente: Formato data de nascimento: dd/mm/yyyy")
	private String dataNascimento;

	@NotEmpty(message = "Cliente: Rg não pode ser vazio.")
	@Length(min = 9, max = 9, message = "Cliente: Rg deve conter 9 caracteres")
	private String rg;

	@NotEmpty(message = "Cliente: Email não pode ser vazio.")
	private String email;

	@Valid
	@NotNull
	private EnderecoDto endereco;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id= id;
	}
	
	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId= usuarioId;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {

		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Cliente[usuarioId=" + usuarioId + "," 
	            + "sexo=" + sexo + "," 
				+ "rg=" + rg + "," 
	            + "dataNascimento=" + dataNascimento + "," 
	    	    + "email=" + email + "," 
	    	    +  endereco.toString()
	            + "]";
	}
}
