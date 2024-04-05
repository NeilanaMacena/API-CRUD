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

public class CidadeRepository {

	public List<Cidade> consultar() {

		List<Cidade> cidades = new ArrayList<>();
		Connection conexao = null;

		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT ci.id_cidade, ci.nome_cidade, b.id_bairro, b.nome_bairro, b.cep FROM cidade as ci LEFT JOIN \r\n"
					+ "bairro as b on ci.id_cidade = b.id_cidade order by ci.nome_cidade, b.nome_bairro asc";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Long idCidade = resultSet.getLong("id_cidade");
				String nomeCidade = resultSet.getString("nome_cidade");
				// String uf = resultSet.getString("uf_estado");
				Cidade cidade = null;
				for (Cidade c : cidades) {
					if (c.getId_cidade() == idCidade) {
						cidade = c;
						break;
					}
				}

				if (cidade == null) {
					cidade = new Cidade();
					cidade.setId_cidade(idCidade);
					cidade.setNome_cidade(nomeCidade);
					// cidade.setUf_estado(uf);
					cidades.add(cidade);
				}
				Bairro bairro = new Bairro();
				bairro.setId_bairro(resultSet.getLong("id_bairro"));
				bairro.setNome_bairro(resultSet.getString("nome_bairro"));
				bairro.setCep(resultSet.getLong("cep"));
				bairro.setCidade(cidade);
				if (bairro.getNome_bairro() != null) {
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
		return cidades;
	}

	public void inserir(String nome_cidade, Long id_estado) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "insert into cidade (nome_cidade, id_estado) values (?, ?) ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_cidade);
			ps.setLong(2, id_estado);
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

	public void atualizar(String nome_cidade, Long id_estado, Long id_cidade) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "update  cidade set nome_cidade = ?, id_estado = ? where id_cidade = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_cidade);
			ps.setLong(2, id_estado);
			ps.setLong(3, id_cidade);
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

	public void deletar(Long id_cidade) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "delete from cidade where id_cidade = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_cidade);
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

	public void deletarCidade(Long id_estado) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "delete from cidade where id_estado = ? ";
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

	public boolean consultaSeIdEstadoExiste(Long id_estado) {
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

	public List<Cidade> consultaSeIdEstadoExisteNaCidade(Long id_estado) {
		List<Cidade> cidades = new ArrayList<Cidade>();
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM cidade where id_estado = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_estado);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Cidade cidade = new Cidade();
				cidade.setId_cidade(resultSet.getLong("id_cidade"));
				cidade.setNome_cidade(resultSet.getString("nome_cidade"));
				cidades.add(cidade);
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
		return cidades;
	}

	public boolean consultaSeIdCidadeExiste(Long id_cidade) {
		boolean existe = false;
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM cidade where id_cidade = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_cidade);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Long id = resultSet.getLong("id_cidade");
				if (id == id_cidade) {
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

	public boolean consultaSeNomeExiste(String nome_cidade) {
		boolean existe = false;
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM cidade where nome_cidade ilike ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_cidade);
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

	public boolean consultaSeExisteRelacaoEntreTabelas(Long id_cidade) {
		boolean existe = false;
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM bairro where id_cidade in (?)";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_cidade);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Long id = resultSet.getLong("id_cidade");
				if (id == id_cidade) {
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

	public boolean consultaSeExisteCidadeNoMesmoEstado(String nome_cidade, Long id_estado) {
		boolean existe = false;
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "select * from cidade where nome_cidade ilike ? GROUP BY id_cidade HAVING (id_estado) = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_cidade);
			ps.setLong(2, id_estado);
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

}
