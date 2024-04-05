package br.com.stf.treinamento.mvc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.stf.treinamento.mvc.model.Estado;
import br.com.stf.treinamento.mvc.service.EstadoService;

public class EstadoApplication {

	static EstadoService estadoService = new EstadoService();

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		boolean continuar = true;
		while (continuar) {

			System.out.println("Escolha uma das opções:");

			System.out.println("Para consultar dados digite 1:");
			System.out.println("Para inserir dados digite 2:");
			System.out.println("Para atualizar dados digite 3:");
			System.out.println("Para excluir dados digite 4:");
			System.out.println("Para consulta relacionada  digite 5:");

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
				consultarRelacionamento(in);
				break;
			default:
				break;
			}

		}
		in.close();
	}

	private static void consultar() {
		List<Estado> estados = new ArrayList<Estado>();
		estados = estadoService.consultar();
		System.out.println(estados.toString());
		System.out.println("consulta realizada com sucesso!");

	}

	private static void consultarRelacionamento(Scanner in) {
		List<Estado> estados = new ArrayList<Estado>();
		estados = estadoService.consultaRelacionamento();
		System.out.println(estados.toString());
		System.out.println("consulta realizada com sucesso!");
	}

	private static void inserir(final Scanner in) {
		while (true) {
			try {
				System.out.println("Digite o nome do estado:");
				String nome_estado = in.nextLine();
				System.out.println("Digite o uf do estado:");
				String uf_estado = in.nextLine();
				estadoService.inserir(nome_estado, uf_estado);
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
				System.out.println("Digite o nome do estado:");
				String nome_estado = in.nextLine();
				System.out.println("Digite o uf do estado:");
				String uf_estado = in.nextLine();
				System.out.println("Digite o id do estado:");
				Long id_estado = Long.valueOf(in.nextLine());
				estadoService.atualizar(nome_estado, uf_estado, id_estado);
				System.out.println("Registro atualizado com sucesso!");
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
						System.out.println("Digite o id do estado que deseja excluir:");
						Long id_estado = Long.valueOf(in.nextLine());
						  estadoService.deletar(id_estado);
						  System.out.println("Registro esxcluido com sucesso!\n");
					     break;
					} catch (NumberFormatException e) {
						System.out.println("Digite apenas número. Tente novamente.\n");

					} catch (NullPointerException e) {
						System.out.println(e.getMessage());
					}
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			System.out.println("Se deseja realmente excluir esse registro: Digite SIM.\n");
			String resposta = in.nextLine();
			if (resposta.equalsIgnoreCase("SIM")) {
				System.out.println("Comfirme o id do estado:");
				Long id_estado = Long.valueOf(in.nextLine());
				estadoService.deletarGeral(id_estado);
				System.out.println("Registro esxcluido com sucesso!\n");
				break;
			  }
			}
			break;
		}
	}

}