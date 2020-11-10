package com.sdc.api.dtos;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class EnderecoDto {

	public EnderecoDto() {
		
	}
	
	private String id;

	@NotEmpty(message = "Endereco: Rua não pode ser vazio.")
	private String rua;

	@NotEmpty(message = "Endereco: Numero não pode ser vazia.")
	private String numero;

	@NotEmpty(message = "Endereco: Cidade não pode ser vazio.")
	private String cidade;

	@NotEmpty(message = "Endereco: Uf não pode ser vazio.")
	@Length(min = 2, max = 2, message = "Endereco: Uf deve conter 2 caracteres.")
	private String uf;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id= id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero= numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return "Endereco[id=" + id+ "," 
	            + "rua=" + rua+ "," 
				+ "numero=" + numero + "," 
	            + "cidade=" + cidade+ "," 
	    	    + "uf=" + uf + "]";
	}

}
