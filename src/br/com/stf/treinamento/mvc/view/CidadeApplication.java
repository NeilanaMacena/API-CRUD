package br.com.stf.treinamento.mvc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.stf.treinamento.mvc.model.Cidade;
import br.com.stf.treinamento.mvc.service.CidadeService;

public class CidadeApplication {
	static CidadeService cidadeService = new CidadeService();

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		boolean continuar = true;
		while (continuar) {

			System.out.println("Escolha uma das opções:");

			System.out.println("Para consultar dados digite 1:");
			System.out.println("Para inserir dados digite 2:");
			System.out.println("Para atualizar dados digite 3:");
			System.out.println("Para excluir dados digite 4:");
			System.out.println("Para consultar por nome  digite 5:");
			System.out.println("Para finalizar digite 0:");

			Integer opcao = Integer.valueOf(in.nextLine());

			switch (opcao) {
			case 0:
				continuar = false;
				break;
			case 1:
				consultar();
				break;
			case 2:
				inserir(in);
				break;
			case 3:
				atualizar(in);
				break;
			case 4:
				deletar(in);
				break;
			case 5:
//          	consultaPorNome(in);
//          	break;

			default:
				break;
			}

		}

	}

	private static void consultar() {
		List<Cidade> cidades = new ArrayList<Cidade>();
		cidades = cidadeService.consultar();
		System.out.println(cidades.toString());
		System.out.println("consulta realizada com sucesso!\n");
	}

	private static void inserir(final Scanner in) {
		while (true) {
			try {
				System.out.println("Digite o nome da cidade:");
				String nome_cidade = in.nextLine();
				System.out.println("Digite o id_estado:");
				Long id_estado = Long.valueOf(in.nextLine());
				cidadeService.inserir(nome_cidade, id_estado);
				System.out.println("Registro inserido com sucesso!\n");
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void atualizar(Scanner in) {
		while (true) {
			try {
				System.out.println("Digite o nome do cidade:");
				String nome_cidade = in.nextLine();
				System.out.println("Digite o id do estado:");
				Long id_estado = Long.valueOf(in.nextLine());
				System.out.println("Digite o id da cidade que deseja atualizar:");
				Long id_cidade = Long.valueOf(in.nextLine());
				cidadeService.atualizar(nome_cidade, id_estado, id_cidade);
				System.out.println("Registro atualizado com sucesso!\n");
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void deletar(Scanner in) {
		while (true) {
			try {
				while (true) {
					try {

						System.out.println("Digite o id da cidade que deseja excluir:");
						Long id_cidade = Long.valueOf(in.nextLine());
						cidadeService.deletar(id_cidade);
						System.out.println("Registro esxcluido com sucesso!");
						break;
					} catch (NumberFormatException e) {
						System.out.println("Digite apenas números. Tente novamente.");

					} catch (NullPointerException e) {
						System.out.println(e.getMessage());
					}
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());

				System.out.println("Se deseja realmente excluir esse registro: Digite SIM.");
				String resposta = in.nextLine();
				if (resposta.equalsIgnoreCase("SIM")) {
					System.out.println("Comfirme o id da cidade:");
					Long id_cidade = Long.valueOf(in.nextLine());
					cidadeService.deletarGeral(id_cidade);
					System.out.println("Registro esxcluido com sucesso!\n");
					break;
				}
			}
			break;
		}
	}
}