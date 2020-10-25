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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_usuario", nullable = false)
	private int idUsuario;
	
	@Column(name = "sexo", nullable = false, length = 1)
	private String sexo;
	
	@Column(name = "data_Nascimento", nullable = false)
	private Date dataNascimento;
	
	@Column(name = "rg", nullable = false, length = 9)
	private String rg;
	
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Usuario usuario;

   	@JsonManagedReference
   	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<Cartao> cartoes;
   	
   	public String getSexo() {
   		return this.sexo;
   	}
   	
   	public void setSexo(String sexo) {
   		this.sexo = sexo;
   	}
   	
   	public Date getDataNascimento() {
   		return this.dataNascimento;
   	}
   	
   	public void setDataNascimento(Date dataNascimento) {
   		this.dataNascimento = dataNascimento;
   	}
   	
   	public String getRg() {
   		return this.rg;
   	}
   	
   	public void setRg(String rg) {
   		this.rg = rg;
   	}
   	
   	public String getEmail() {
   		return this.email;
   	}
   	
   	public void setEmail(String email) {
   		this.email= email; 
   	}
   	
   	public Usuario getUsuario() {
   		return this.usuario;
   	}
   	
   	public void setUsuario(Usuario usuario) {
   		this.usuario = usuario;
   	}
   	
   	public List<Cartao> getCartoes() {
   		return this.cartoes;
   	}
   	
   	public void setCartoes(List<Cartao> cartoes) {
   		this.cartoes = cartoes;
   	}
}