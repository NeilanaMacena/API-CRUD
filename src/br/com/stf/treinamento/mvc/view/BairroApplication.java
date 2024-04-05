package br.com.stf.treinamento.mvc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.stf.treinamento.mvc.model.Bairro;
import br.com.stf.treinamento.mvc.service.BairroService;

public class BairroApplication {

	static BairroService bairroService = new BairroService();

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		boolean continuar = true;
		while (continuar) {

			System.out.println("Escolha uma das opções:");

			System.out.println("Para consultar dados digite 1:");
			System.out.println("Para inserir dados digite 2:");
			System.out.println("Para atualizar dados digite 3:");
			System.out.println("Para excluir dados digite 4:");
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
			default:
				break;
			}

		}

	}

	private static void consultar() {
		List<Bairro> bairros = new ArrayList<Bairro>();
		bairros = bairroService.consultar();
		System.out.println(bairros.toString());
		System.out.println("consulta realizada com sucesso!");
	}

	private static void inserir(final Scanner in) {
		while (true) {
			try {
			System.out.println("Digite o nome do bairro:");
			String nome_bairro = in.nextLine();
			System.out.println("Digite o cep:");
			Long cep = Long.valueOf(in.nextLine());
			System.out.println("Digite o id da cidade:");
			Long id_cidade = Long.valueOf(in.nextLine());
			bairroService.inserir(nome_bairro, cep, id_cidade);
			System.out.println("Registro inserido com sucesso!");
			break;
			}catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void atualizar(Scanner in) {
		while (true) {
			try {
				System.out.println("Digite o nome do bairro:");
				String nome_bairro = in.nextLine();
				System.out.println("Digite o cep:");
				Long cep = Long.valueOf(in.nextLine());
				System.out.println("Digite o id da cidade:");
				Long id_cidade = Long.valueOf(in.nextLine());
				System.out.println("Digite o id do bairro que deseja atualizar:");
				Long id_bairro = Long.valueOf(in.nextLine());
				bairroService.atualizar(nome_bairro, cep, id_cidade, id_bairro);
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
				System.out.println("Digite o id do bairro que deseja excluir:");
				Long id_bairro = Long.valueOf(in.nextLine());
				bairroService.deletar(id_bairro);
				System.out.println("Registro esxcluido com sucesso!\n");
				break;
			} catch (NumberFormatException e) {
				System.out.println("Digite apenas números.");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
