package br.com.stf.treinamento.mvc.model;

public class Bairro {
	private Long id_bairro;
	private String nome_bairro;
	private Long cep;
	private Cidade cidade;
	
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public Long getId_bairro() {
		return id_bairro;
	}
	public void setId_bairro(Long id_bairro) {
		this.id_bairro = id_bairro;
	}
	public String getNome_bairro() {
		return nome_bairro;
	}
	public void setNome_bairro(String nome_bairro) {
		this.nome_bairro = nome_bairro;
	}
	public Long getCep() {
		return cep;
	}
	public void setCep(Long cep) {
		this.cep = cep;
	}
	@Override
	public String toString() {
		return "Id-Bairro: " + id_bairro + ", Bairro: "+ nome_bairro + " cep: " + cep+ "\n";
	}
	
	
	
}
