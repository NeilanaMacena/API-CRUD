package br.com.stf.treinamento.mvc.service;

import java.util.List;

import br.com.stf.treinamento.mvc.model.Bairro;
import br.com.stf.treinamento.mvc.repository.BairroRepository;

public class BairroService {
	static BairroRepository bairroRepository = new BairroRepository();

	public List<Bairro> consultar() {
		return bairroRepository.consultar();

	}

	public void inserir(String nome_bairro, Long cep, Long id_cidade) {
		if (nome_bairro == null || nome_bairro.isBlank()) {
			throw new IllegalArgumentException("Preencha corretamente os campos nome  e uf do estado .");
		}
		if (cep == 8) {
			throw new IllegalArgumentException("Cep incorreto. Preencha corretamente");
		}
		boolean ExisteBairro = bairroRepository.consultaSeJaExisteBairroNaMesmaCidade(nome_bairro, id_cidade);
		if (ExisteBairro == true) {
			throw new IllegalArgumentException("Esse bairro já existe nessa cidade.");
		}

		boolean existeIdCidade = bairroRepository.consultaSeIdCidadeExiste(id_cidade);
		if (existeIdCidade == false) {
			throw new IllegalArgumentException("O Id da cidade informado não existe. Tente novamente");
		}
		
		bairroRepository.inserir(nome_bairro.trim(), cep, id_cidade);
	}

	public void atualizar(String nome_bairro, Long cep, Long id_cidade, Long id_bairro) {
		if (nome_bairro == null || nome_bairro.isBlank()) {
			throw new IllegalArgumentException("Preencha corretamente os campos nome  e uf do estado .");
		}
		if (cep == 8) {
			throw new IllegalArgumentException("Cep incorreto. Preencha corretamente");
		}
		boolean existeIdBairro = bairroRepository.consultaSeIdBairroExiste(id_bairro);
		if (existeIdBairro == false) {
			throw new IllegalArgumentException("Id bairro informado não existe. Tente novamente");
		}
		boolean existeIdCidade = bairroRepository.consultaSeIdCidadeExiste(id_cidade);
		if (existeIdCidade == false) {
			throw new IllegalArgumentException("Id cidade informado não existe. Tente novamente");
		}
		boolean ExisteBairro = bairroRepository.consultaSeJaExisteBairroNaMesmaCidade(nome_bairro, id_cidade);
		if (ExisteBairro == true) {
			throw new IllegalArgumentException("Esse bairro já existe nessa cidade.");
		}
		bairroRepository.atualizar(nome_bairro.trim(), cep, id_cidade, id_bairro);
	}

	public void deletar(Long id_bairro) {
		boolean existeIdBairro = bairroRepository.consultaSeIdBairroExiste(id_bairro);
		if (existeIdBairro == false) {
			throw new IllegalArgumentException("Id informado não existe. Tente novamente");
		}
		bairroRepository.deletar(id_bairro);

	}
}
