package br.com.stf.treinamento.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class Cidade {

	private Long Id_cidade;
	private String nome_cidade;
	private List<Bairro> bairros = new ArrayList<Bairro>();
	private Estado estado;

	public Long getId_cidade() {
		return Id_cidade;
	}

	public void setId_cidade(Long id_cidade) {
		Id_cidade = id_cidade;
	}

	public String getNome_cidade() {
		return nome_cidade;
	}

	public void setNome_cidade(String nome_cidade) {
		this.nome_cidade = nome_cidade;
	}

	public List<Bairro> getBairros() {
		return bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Id-Cidade: "+ Id_cidade+", Cidade: " +  nome_cidade + ", Bairros: "+ bairros+ "\n";
	}
}
