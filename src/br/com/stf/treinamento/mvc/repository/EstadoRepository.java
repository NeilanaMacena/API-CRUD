package br.com.stf.treinamento.mvc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.stf.treinamento.mvc.model.Bairro;
import br.com.stf.treinamento.mvc.model.Cidade;
import br.com.stf.treinamento.mvc.model.Estado;

public class EstadoRepository {

	public List<Estado> consultar() {

		List<Estado> estados = new ArrayList<>();
		Connection conexao = null;

		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "select e.id_estado, e.nome_estado, e.uf_estado, ci.id_cidade, ci.nome_cidade \r\n"
					+ "from estado as e \r\n" + "left join cidade as ci \r\n"
					+ "on e.id_estado = ci.id_estado order by e.nome_estado, ci.nome_cidade asc";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Long idEstado = resultSet.getLong("id_estado");
				String nomeEstado = resultSet.getString("nome_estado");
				String uf = resultSet.getString("uf_estado");
				Estado estado = null;
				for (Estado e : estados) {
					if (e.getId_estado() == idEstado) {
						estado = e;
						break;
					}
				}

				if (estado == null) {
					estado = new Estado();
					estado.setId_estado(idEstado);
					estado.setNome_estado(nomeEstado);
					estado.setUf_estado(uf);
					estados.add(estado);
				}
				Cidade cidade = new Cidade();
				cidade.setId_cidade(resultSet.getLong("id_cidade"));
				cidade.setNome_cidade(resultSet.getString("nome_cidade"));
				cidade.setEstado(estado);
				if (cidade.getNome_cidade() != null) {
					estado.getCidades().add(cidade);
				}
			}

		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return estados;
	}

	public void inserir(String nome_estado, String uf_estado) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "insert into estado (nome_estado, uf_estado) values (?, ?) ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_estado);
			ps.setString(2, uf_estado);
			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void atualizar(String nome_estado, String uf_estado, Long id_estado) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "update  estado set nome_estado = ?, uf_estado = ? where id_estado = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_estado);
			ps.setString(2, uf_estado);
			ps.setLong(3, id_estado);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void deletar(Long id_estado) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "delete from estado where id_estado = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_estado);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<Estado> consultaRelacionamento() {
		List<Estado> estados = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "select  ci.id_cidade,  ci.nome_cidade, e.id_estado, e.nome_estado, e.uf_estado, b.id_bairro, b.nome_bairro, b.cep\r\n"
					+ "from estado as e inner join cidade as ci on e.id_estado = ci.id_estado \r\n"
					+ "cross join bairro as b where b.id_cidade = ci.id_cidade\r\n"
					+ "GROUP BY e.id_estado , ci.id_cidade,  ci.nome_cidade, b.id_bairro, b.nome_bairro, b.cep order by e.nome_estado, ci.nome_cidade,\r\n"
			 	    +"b.nome_bairro asc";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Long idEstado = resultSet.getLong("id_estado");
				String nomeEstado = resultSet.getString("nome_estado");
				String uf = resultSet.getString("uf_estado");
				Estado estado = null;
				for (Estado e : estados) {
					if (e.getId_estado() == idEstado) {
						estado = e;
						break;
					}
				}
				if (estado == null) {
					estado = new Estado();
					estado.setId_estado(idEstado);
					estado.setNome_estado(nomeEstado);
					estado.setUf_estado(uf);
					estados.add(estado);
				}
				Long idCidade = resultSet.getLong("id_cidade");
				String nomeCidade = resultSet.getString("nome_cidade");
				Cidade cidade = null;

				for (Cidade c : estado.getCidades()) {
					if (c.getId_cidade() == idCidade) {
						cidade = c;
						break;
					}
				}
				if (cidade == null) {
					cidade = new Cidade();
					cidade.setId_cidade(idCidade);
					cidade.setNome_cidade(nomeCidade);
					estado.getCidades().add(cidade);
				}
				if (!estado.getCidades().contains(cidade)) {
					estado.getCidades().add(cidade);
				}
				for (Cidade c : estado.getCidades()) {
					if (c.getId_cidade() == cidade.getId_cidade()) {
						cidade = c;
						break;
					}
				}
				Bairro bairro = new Bairro();
				bairro.setNome_bairro(resultSet.getString("nome_bairro"));
				bairro.setId_bairro(resultSet.getLong("id_bairro"));
				bairro.setCep(resultSet.getLong("cep"));
				bairro.setCidade(bairro.getCidade());
				if (!cidade.getBairros().contains(bairro)) {
					cidade.getBairros().add(bairro);
				}
			}
		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return estados;
	}
	public boolean consultaSeNomeExiste(String nome_estado) {
		boolean existe = false;
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM estado where nome_estado ilike ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_estado);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				existe = true;
			}
		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return existe;
	}

	public boolean consultaSeIdExiste(Long id_estado) {
		boolean existe = false;
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM estado where id_estado = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_estado);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				existe = true;
			}

		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return existe;
	}

	public boolean consultaSeExisteRelacaoEntreTabelas(Long id_estado) {
		boolean existe = false;
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM cidade where id_estado in (?)";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_estado);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Long id = resultSet.getLong("id_estado");
				if (id == id_estado) {
					existe = true;
				}
			}

		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (conexao != null) {
				try {
					conexao.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return existe;
	}



}