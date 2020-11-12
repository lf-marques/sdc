package com.sdc.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "rua", nullable = false, length = 255)
	private String rua;
	
	@Column(name = "numero", nullable = false)
	private int numero;
	
	@Column(name = "cidade", nullable = false, length = 255)
	private String cidade;
	
	@Column(name = "uf", nullable = false, length = 2)
	private String uf;
	
	@JsonBackReference
   	@ManyToOne(fetch = FetchType.EAGER)
   	private Cliente cliente;
   	
	public int getId() {
   		return this.id;
   	}
   	
   	public void setId(int id) {
   		this.id = id;
   	}
   	
   	public String getRua() {
   		return this.rua;
   	}
   	
   	public void setRua(String rua) {
   		this.rua = rua;
   	}
   	
   	public int getNumero() {
   		return this.numero;
   	}
   	
   	public void setNumero(int numero) {
   		this.numero = numero;
   	}
   	
   	public String getCidade() {
   		return this.cidade;
   	}
   	
   	public void setCidade(String cidade) {
   		this.cidade = cidade;
   	}
   	
   	public String getUf() {
   		return this.uf;
   	}
   	
   	public void setUf(String uf) {
   		this.uf = uf;
   	}
   	
   	public Cliente getCliente() {
   		return this.cliente;
   	}
   	
   	public void setCliente(Cliente cliente) {
   		this.cliente = cliente;
   	}
   	
   	@Override
	public String toString() {
		return "Endereco[id=" + id + "," 
	            + "numero=" + numero + "," 
				+ "rua=" + rua+ ","
				+ "cidade=" + cidade + ","
				+ "uf=" + uf + ","
			    + "cliente=" + cliente.toString() + "]";
   	}
}