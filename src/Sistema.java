import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {

	static Scanner input = new Scanner(System.in);
	static boolean logado = false;

	static ArrayList<Produto> produtos = new ArrayList<Produto>();
	static ArrayList<String> clientes = new ArrayList<String>();

	static float saldo = 0;

	public static void main(String[] args) {

		login();
		menu();

	}

	static void menu() {
		String selecao;

		do {
			limparTela();

			System.out.println("--------------MENU--------------");
			System.out.println("1-Mostrar Produtos em estoque");
			System.out.println("2-Mostrar clientes");
			System.out.println("3-Cadastrar novo Cliente");
			System.out.println("4-Cadastrar novo Produto");
			System.out.println("5-Mostrar dinheiro em caixa");
			System.out.println("6-Vender produto");
			System.out.println("7-Sair ");
			System.out.println("..................................");

			System.out.print("Selecione uma opção: ");
			selecao = input.nextLine();

			switch (selecao) {
				case "1":
					mostrarProdutos();
					break;

				case "2":
					mostrarClientes();
					break;
				case "3":
					cadastrarCliente();
					break;

				case "4":
					cadastrarProduto();
					break;

				case "5":
					mostrarSaldo();
					break;

				case "6":
					venderProduto();
					break;

				default:
					esperarEnter();
					break;
			}

		} while (!selecao.equals("7"));

	}

	private static void venderProduto() {

		limparTela();

		mostrarProdutos();

		System.out.print("Insira o id do produto: ");
		int idSelecionado = Integer.parseInt(input.nextLine());

		Produto prodSelecionado = null;

		for (int i = 0; i < produtos.size(); i++) {
			if (produtos.get(i).id == idSelecionado) {
				prodSelecionado = produtos.get(i);
				break;
			}
		}

		if (prodSelecionado == null) {
			System.out.println(" -! Produto não encontrado !- ");

			esperarEnter();
			return;
		}

		System.out.println("Produto selecionado: ");
		mostrarProduto(prodSelecionado);

		int qtdPedida = pedirInteiro("Quantidade requerida: ");

		if (qtdPedida > prodSelecionado.qtdEstoque) {
			System.out.println(" -! Estoque insuficiente !-");

			esperarEnter();
			return;
		}

		double valorTotal = qtdPedida * prodSelecionado.valor;

		System.out.println("\n      	 RECIBO");
		System.out.println("Nome do produto: ..." + prodSelecionado.nome);
		System.out.println("Valor unitário: ....R$" + prodSelecionado.valor);
		System.out.println("Quantidade: ........" + qtdPedida);
		System.out.println("Valor total: .......R$" + valorTotal + "\n");

		System.out.println("Confirmar venda? [S]im | [N]ão: ");
		String confirmacao = input.nextLine();

		if (confirmacao.equals("S")) {
			saldo += valorTotal;
			prodSelecionado.qtdEstoque -= qtdPedida;
			System.out.println(" -> Venda concluída <-");
		} else if (confirmacao.equals("N")) {
			System.out.println(" -! Venda cancelada !-");
		}

		esperarEnter();
	}

	static void mostrarSaldo() {
		limparTela();

		System.out.println("\nSaldo em caixa: R$ " + saldo);

		esperarEnter();

	}

	static void cadastrarCliente() {

		System.out.print("\nInsira o nome do Cliente: ");
		String nome = input.nextLine();

		clientes.add(nome);

		System.out.println("\n -> Cliente cadastrado com sucesso! <- \n");

		esperarEnter();
	}

	static void cadastrarProduto() {
		limparTela();

		int qtdProds = produtos.size();

		int id = produtos.isEmpty() ? 1 : produtos.get(qtdProds - 1).id + 1;

		// 0 | 1 - Feijao
		// 1 | 2 - arroz
		// 2 | 4 - Carne
		// 3 | 5 - Farinha <- Ultimo elemento tem a posição 4 - 1
		// 6 tamanho -^ ^- Adapta a lista 0 indexada
		//
		// 4 elementos

		System.out.print("\nInsira o nome do produto: ");
		String nome = input.nextLine();

		System.out.print("\nInsira o valor do produto: ");
		double valor = Double.parseDouble(input.nextLine());

		System.out.print("\nInsira a quantidade contida em estoque: ");
		int qtdEstoque = Integer.parseInt(input.nextLine());

		Produto prod1 = new Produto(id, nome, valor, qtdEstoque);

		produtos.add(prod1);

		System.out.println("\n -> Produto cadastrado com sucesso! <- \n");

		esperarEnter();
	}

	static void mostrarClientes() {
		limparTela();
		// clientes: ArrayList
		// 0 < nome >
		// 1 < nome >
		// 2 < nome >
		// 3 < nome >
		// 4 < nome >
		// 5 < nome >
		// 6 < nome >
		// 7 < nome >

		System.out.println("\nQuantidade: " + clientes.size());
		System.out.println("\nClientes cadastrados: ");
		System.out.println("\n id | Nome");
		System.out.println("\n --------------------------");
		for (int i = 0; i < clientes.size(); i++) {

			int id = i + 1;
			System.out.println(id + " | " + clientes.get(i));
			// Feijão - R$2.50
		}

		esperarEnter();

	}

	static void mostrarProdutos() {
		limparTela();

		System.out.println("\nQuantidade: " + produtos.size());
		System.out.println("\nProdutos cadastrados: ");

		System.out.println(" Id | Produto                        |   Valor   | Quantidade em estoque");
		System.out.println("------------------------------------------------------------------------");
		for (Produto produto : produtos) {
			mostrarProduto(produto);
			// System.out.println(produto.id + " | " + produto.nome + " | R$" +
			// produto.valor);
		}

		esperarEnter();

	}

	static void mostrarProduto(Produto produto) {

		int idColumnLength = 4;
		int nameColumnLength = 30;
		int valueColumnLength = 6;
		// int qtdColumnLength = 4;

		String idStr = " ".repeat(idColumnLength - Integer.toString(produto.id, 20).length()) + produto.id;

		String nameStr = produto.nome + " ".repeat(nameColumnLength - produto.nome.length());

		String valueStr = String.format("%.2f", produto.valor);
		valueStr = " ".repeat(valueColumnLength - valueStr.length()) + valueStr;

		// String qtdStr = produto.qtdEstoque + " ".repeat(qtdColumnLength -
		// Integer.toString(produto.qtdEstoque).length());

		System.out.println(idStr + " | " + nameStr + " | R$ " + valueStr + " | " + produto.qtdEstoque);
	}

	static void login() {

		String name = "john";
		String pass = "1010";

		do {
			limparTela();

			System.out.println("- - - Logar no Sistema - - -");

			System.out.print("Informe seu nome: ");
			String login = input.nextLine();

			System.out.print("Informe a sua senha: ");
			String senha = input.nextLine();

			if (login.equals(name) &&
					senha.equals(pass)) {
				logado = true;
				System.out.println(" -> Logado com sucesso! <- ");

			} else {
				System.out.println("Nome ou senha incorretos.");
			}

			esperarEnter();

		} while (logado == false);

	}

	static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	static void esperarEnter() {
		System.out.print("\nPressione <enter> para continuar...");
		input.nextLine();
	}

	static int pedirInteiro(String mensagem) {
		while (true) {
			try {
				System.out.print(mensagem);
				int numero = Integer.parseInt(input.nextLine());	
				return numero;
			} catch (Exception e) {
				System.out.println(" -! Algo deu errado. Tente novamente");
			}

		}

	}

}