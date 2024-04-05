package br.com.stf.treinamento.mvc.service;

import java.util.List;

import br.com.stf.treinamento.mvc.model.Bairro;
import br.com.stf.treinamento.mvc.model.Cidade;
import br.com.stf.treinamento.mvc.repository.BairroRepository;
import br.com.stf.treinamento.mvc.repository.CidadeRepository;

public class CidadeService {
	
    CidadeRepository cidadeRepository = new CidadeRepository();

	public List<Cidade> consultar() {
		return cidadeRepository.consultar();
	}

	public void inserir(String nome_cidade, Long id_estado) {
		
		if ( nome_cidade == null || nome_cidade.isBlank()  ) {
			throw new IllegalArgumentException("Nome inválida. Preencha corretamente o nome da cidade.\n");
		}
		boolean existeEstado = cidadeRepository.consultaSeIdEstadoExiste(id_estado);
		if (existeEstado != true) {
			throw new IllegalArgumentException("Esse estado não existe. Tente novamente.\n");
		}
		
		boolean ExisteCidade = cidadeRepository.consultaSeExisteCidadeNoMesmoEstado(nome_cidade, id_estado);
		if (ExisteCidade == true ) {
			throw new IllegalArgumentException("Essa cidade já existe");
		}
		
		cidadeRepository.inserir(nome_cidade.trim(), id_estado);
		
	}

	public void atualizar(String nome_cidade, Long id_estado, Long id_cidade) {
		if ( nome_cidade == null || nome_cidade.isBlank() || nome_cidade.matches("[A-z]*")  ) {
			throw new IllegalArgumentException("Preencha corretamente o campo nome  .");
		}
		
		boolean existeIdCidade = cidadeRepository.consultaSeIdCidadeExiste(id_cidade);
		if (existeIdCidade != true) {
			throw new IllegalArgumentException("Essa cidade não existe. Tente novamente.\n");
		}
		boolean existeIdEstado = cidadeRepository.consultaSeIdEstadoExiste(id_estado);
		if (existeIdEstado != true) {
			throw new IllegalArgumentException("Esse estado não existe. Tente novamente.\n");
		}
		cidadeRepository.atualizar(nome_cidade.trim(), id_estado, id_cidade );
	}

	public void deletar(Long id_cidade) {
		   boolean existeLigacao = cidadeRepository.consultaSeExisteRelacaoEntreTabelas(id_cidade);
		   if ( existeLigacao == true) {
			   throw new IllegalArgumentException(
					   "Esse registro possui dependências em outra tabela. Exclua seus dependentes.\n");
		}
		   boolean existeIdCidade = cidadeRepository.consultaSeIdCidadeExiste(id_cidade);
		   if ( existeIdCidade == false) {
			   throw new NullPointerException("Id informado não existe. Tente novamente.\n");
		}
		cidadeRepository.deletar(id_cidade);
	}
	public void deletarGeral(Long id_cidade) {
		BairroRepository bairroRepository = new BairroRepository();
		List<Bairro> bairros = bairroRepository.consultaSeIdCidadeExisteNobairro(id_cidade);
			if (bairros != null) {
				bairroRepository.deletarBairro(id_cidade);
			}
			cidadeRepository.deletar(id_cidade);
	}

	
	
}
