package br.com.stf.treinamento.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class Estado {
	
	private Long id_estado;
	private String nome_estado;
	private String uf_estado;
	private List<Cidade> cidades = new ArrayList<Cidade>();
	
	public Long getId_estado() {
		return id_estado;
	}
	public void setId_estado(Long id_estado) {
		this.id_estado = id_estado;
	}
	public String getNome_estado() {
		return nome_estado;
	}
	public void setNome_estado(String nome_estado) {
		this.nome_estado = nome_estado;
	}
	public String getUf_estado() {
		return uf_estado;
	}
	public void setUf_estado(String uf) {
		this.uf_estado = uf;
	}
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	@Override
	public String toString() {
		return "Id-Estado: "+ id_estado + ", Nome-Estado: " + nome_estado  + ", UF: " + uf_estado  +" => Cidades: "+ cidades +"\n";
				
	}
	
	

}
