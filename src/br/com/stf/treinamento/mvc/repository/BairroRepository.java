package br.com.stf.treinamento.mvc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.stf.treinamento.mvc.model.Bairro;

public class BairroRepository {

	public List<Bairro> consultar() {
		List<Bairro> bairros = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM bairro order by nome_bairro asc";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Bairro bairro = new Bairro();
				bairro.setId_bairro(resultSet.getLong("id_bairro"));
				bairro.setNome_bairro(resultSet.getString("nome_bairro"));
				bairro.setCep(resultSet.getLong("cep"));
				bairros.add(bairro);
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
		return bairros;
	}

	public boolean consultaSeIdBairroExiste(Long id_bairro) {
		boolean existe = false;
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM bairro where id_bairro = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_bairro);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Long id = resultSet.getLong("id_bairro");
				if (id == id_bairro) {
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

	public boolean consultaSeJaExisteBairroNaMesmaCidade(String nome_bairro, Long id_cidade) {
		boolean existe = false;
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "select * from bairro where nome_bairro ilike ? GROUP BY id_bairro HAVING (id_cidade) = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_bairro);
			ps.setLong(2, id_cidade);
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

	public void inserir(String nome_bairro, Long cep, Long id_cidade) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "insert into bairro (nome_bairro, cep, id_cidade) values (?, ?, ?) ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_bairro);
			ps.setLong(2, cep);
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

	public void atualizar(String nome_bairro, Long cep, Long id_cidade, Long id_bairro) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "update  bairro set nome_bairro = ?, cep = ?, id_cidade = ? where id_bairro = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setString(1, nome_bairro);
			ps.setLong(2, cep);
			ps.setLong(3, id_cidade);
			ps.setLong(4, id_bairro);
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

	public void deletar(Long id_bairro) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "delete from bairro where id_bairro = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_bairro);
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
	public void deletarBairro(Long id_cidade) {
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "delete from bairro where id_cidade = ? ";
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
	public List<Bairro> consultaSeIdCidadeExisteNobairro(Long id_cidade) {
		List<Bairro> bairros = new ArrayList<Bairro>();
		Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "Lana", "");
			String SQL_SELECT = "SELECT * FROM bairro where id_cidade = ? ";
			PreparedStatement ps = conexao.prepareStatement(SQL_SELECT);
			ps.setLong(1, id_cidade);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Bairro bairro = new Bairro();
				bairro.setId_bairro(resultSet.getLong("id_bairro"));
				bairro.setNome_bairro(resultSet.getString("nome_bairro"));
				bairro.setCep(resultSet.getLong("cep"));
				bairros.add(bairro);
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
		return bairros;
	}

	

}
