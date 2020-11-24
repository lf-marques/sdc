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
@Table(name = "cartao")
public class Cartao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "numero", nullable = false, length = 16)
	private String numero;
	
	@Column(name = "data_Validade", nullable = false)
	private Date dataValidade;
	
	@Column(name = "bloqueado", nullable = false, length = 1)
	private int bloqueado;
	
	@JsonBackReference
   	@ManyToOne(fetch = FetchType.EAGER)
   	private Cliente cliente;
   	
   	public int getId() {
   		return this.id;
   	}
   	
   	public void setId(int id) {
   		this.id = id;
   	}
   	
   	public String getNumero() {
   		return this.numero;
   	}
   	
   	public void setNumero(String numero) {
   		this.numero = numero;
   	}
   	
   	public Date getDataValidade() {
   		return this.dataValidade;
   	}
   	
   	public void setDataValidade(Date dataValidade) {
   		this.dataValidade = dataValidade;
   	}
   	
   	public int getBloqueado() {
   		return this.bloqueado;
   	}
   	
   	public void setBloqueado(int bloqueado) {
   		this.bloqueado = bloqueado; 
   	}
   	
   	public Cliente getCliente() {
   		return this.cliente;
   	}
   	
   	public void setCliente(Cliente cliente) {
   		this.cliente= cliente;
   	}
   	
   	@Override
	public String toString() {
		return "Cartao[id=" + id + "," 
	            + "numero=" + numero + "," 
				+ "dataValidade=" + dataValidade+ ","
				+ "bloqueado=" + bloqueado + ","
			    + "cliente=" + cliente.toString() + "]";
   	}
}