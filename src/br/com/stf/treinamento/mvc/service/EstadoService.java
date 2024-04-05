package br.com.stf.treinamento.mvc.service;

import java.util.List;

import br.com.stf.treinamento.mvc.model.Cidade;
import br.com.stf.treinamento.mvc.model.Estado;
import br.com.stf.treinamento.mvc.repository.BairroRepository;
import br.com.stf.treinamento.mvc.repository.CidadeRepository;
import br.com.stf.treinamento.mvc.repository.EstadoRepository;

public class EstadoService {

	static EstadoRepository estadoRepository = new EstadoRepository();

	public List<Estado> consultar() {
		return estadoRepository.consultar();
	}

	public void inserir(String nome_estado, String uf_estado) {
		if (nome_estado == null || (nome_estado.isBlank())) {
			throw new IllegalArgumentException("Nome inválido. Preencha corretamente o campo nome do estado.");
		}
		if (uf_estado == null || uf_estado.isBlank() || !uf_estado.matches("[A-Z]*") || uf_estado.length() > 2) {
			throw new IllegalArgumentException("UF inválida. Preencha corretamente a Uf do estado.");
		}
		boolean existe = estadoRepository.consultaSeNomeExiste(nome_estado);
		if (existe == true) {
			throw new IllegalArgumentException("Esse estado já existe. Tente novamente");
		}
		estadoRepository.inserir(nome_estado.trim(), uf_estado.trim());
	}

	public void atualizar(String nome_estado, String uf_estado, Long id_estado) {
		if (nome_estado == null || nome_estado.isBlank()) {
			throw new IllegalArgumentException("Preencha corretamente os campos nome  e uf do estado .");
		}
		if (uf_estado == null || uf_estado.isBlank() || !uf_estado.matches("[A-Z]*") || uf_estado.length() > 2) {
			throw new IllegalArgumentException("UF inválida. Preencha corretamente a Uf do estado.");
		}
		boolean existe = estadoRepository.consultaSeIdExiste(id_estado);
		if (existe != true) {
			throw new IllegalArgumentException("Esse estado não existe. Tente novamente");
		}
		boolean existeNome = estadoRepository.consultaSeNomeExiste(nome_estado);
		if (existeNome == true) {
			throw new IllegalArgumentException("Esse estado já existe. Tente novamente");
		}

		estadoRepository.atualizar(nome_estado, uf_estado, id_estado);

	}


	public void deletar(Long id_estado) {
		CidadeRepository cidadeRepository = new CidadeRepository();
		List<Cidade> cidades = cidadeRepository.consultaSeIdEstadoExisteNaCidade(id_estado);
		if (cidades.isEmpty() == false) {
			throw new IllegalArgumentException(
					"Esse registro possui dependências em outra tabela. Exclua seus dependentes.\n");
		}

		boolean existe = estadoRepository.consultaSeIdExiste(id_estado);
		if (existe != true) {
			throw new NullPointerException("Id informado não existe. Tente novamente.\n");
		}
		estadoRepository.deletar(id_estado);
	}
	
	public void deletarGeral(Long id_estado) {
		CidadeRepository cidadeRepository = new CidadeRepository();
			List<Cidade> cidades =  cidadeRepository.consultaSeIdEstadoExisteNaCidade(id_estado);
			for (Cidade cidade : cidades) {
				if (cidade.getBairros() != null) {
					Long idCidade = cidade.getId_cidade();
					BairroRepository bairroRepository = new BairroRepository();
					bairroRepository.deletarBairro(idCidade);
				}
			}
			cidadeRepository.deletarCidade(id_estado);
		
		estadoRepository.deletar(id_estado);
	}
	
	public List<Estado> consultaRelacionamento() {
		return estadoRepository.consultaRelacionamento();
	}

}
