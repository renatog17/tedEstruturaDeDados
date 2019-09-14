package br.renato.bes.ed20191.ted;

import java.util.Scanner;

public class Loja {

	public static void main(String[] args) {
		inicio(new ListaMultidimensional());

	}

	// static int codCat = 1;
	static int codProd = 1;

	static Scanner sc = new Scanner(System.in);

	public static void inicio(IListaMultidimensional lista) {
		executar(lista); // lista pré definida para testes
		Venda venda = null;
		int menu;
		for (int i = 0; i < 1;) {
			System.out.println(
					"Bem-Vindo! \nO que deseja fazer?" + "\n1 - Cadastrar Categoria." + "\n2 - Cadastrar produto"
							+ "\n3 - Listar" + "\n4 - Vender" + "\n5 - Remover Produto" + "\n6 - Sair");

			menu = sc.nextInt();
			sc.nextLine();
			switch (menu) {
			case 1:
				cadastrarCat(lista);
				break;

			case 2:

				cadastrarPro(lista);
				break;

			case 3:
				listar(lista);
				break;

			case 4:
				if (codProd > 1) {
					venda = vender(lista);
					i++;
				} else {
					System.out.println("Não há produtos cadastrados");
					System.out.println();
				}
				break;

			case 5:
				removerProduto(lista);
				break;

			case 6:
				System.out.println("Volte sempre!");
				break;
			default:
				System.out.println("Opcao invalida");
				break;
			}
		}
		System.out.println("*********Estoque da loja*************:");
		lista.imprimir();
		System.out.println();
		System.out.println("**************RELATÓRIO DE VENDA***************:");
		venda.relatorioVenda.imprimir();
		System.out.println("Valor total da compra:");
		System.out.println(venda.valorTotal);

	}

	private static Venda vender(IListaMultidimensional lista) {
		int continuarVenda = 1;
		Venda venda = new Venda();
		while (continuarVenda == 1) {
			lista.imprimir();
			Categoria categoria;
			int codC;
			do {
				System.out.println("Código categoria:");
				codC = sc.nextInt();
				categoria = lista.buscar(codC);
				if (categoria == null)
					// Verifica se existe alguma categoria com aquele código,
					// se não houver pede para informar novamente
					System.out.println("Categoria inexistente, informe novamente:");
				else if (categoria.inicio == null)
					// se a categoria existir mas não haver nenhum produto nela
					// também será pedido para que informe outra categoria
					System.out.println("Não há produtos cadastrados nessa categoria, informe novamente:");
			} while (categoria == null || categoria.inicio == null);

			int codP;
			Produto produto;
			do {
				System.out.println();
				System.out.println("Código do produto:");
				codP = sc.nextInt();
				produto = lista.buscarProduto(codC, codP);
				if (produto == null)
					System.out.println("Produto inexistente, informe novamente:");
			} while (produto == null);

			if (produto.quantidade == 0) {
				System.out.println("Produto em falta");
			} else {
				System.out.println("Você escolheu o produto " + produto.descricao + ". \nDisponível no estoque: "
						+ produto.quantidade + " unidades \nPreço por unidade: R$" + produto.preco);

				// do while para ler a quantidade de produtos q o usuário quer comprar
				// a quantidade só será aceita quando for menor ou igual que o estoque
				int quantidade;
				do {
					System.out.println("Quantidade que deseja comprar:");
					quantidade = sc.nextInt();
					if (quantidade > produto.quantidade) {
						System.out.println("Não temos todo esse estoque! Insira uma quantidade menor");
					}
				} while (quantidade > produto.quantidade);

				// Descrição do pedido, ele podera aceitar ou não
				System.out.println("Descrição da compra:");
				System.out.println("Produto: " + produto.descricao);
				System.out.println("Quantidade: " + quantidade);
				System.out.println("Preço por unidade: R$" + produto.preco);
				System.out.println("Valor total: R$" + quantidade * produto.preco);
				// produto.quantidade = produto.quantidade-quantidade;
				System.out.println();
				System.out.println("Confirmar comprar? (1-sim, 2-não)");
				int confirmarCompra = sc.nextInt();

				// Se ele tiver aceito o pedido, será adicionado à lista multidimensional
				// "venda"
				// o produto que ele comprou e será descontado do estoque da loja a quantidade q
				// ele comprou
				if (confirmarCompra == 1) {
					if (venda.relatorioVenda.buscar(codC) == null) {
						// é necessário verificar se ele já comprou um produto daquela categoria antes
						// evitando que o registro fique duplicado
						venda.relatorioVenda.inserirCategoria(codC, categoria.descricao);
					}

					Produto produtoVenda = venda.relatorioVenda.buscarProduto(codC, codP);
					if (produtoVenda == null) {
						// o mesmo deve ser feito com produto
						venda.relatorioVenda.inserirProduto(codC, codP, produto.descricao, produto.preco, quantidade);
					} else {
						// se ele já tiver comprado alguma quantidade daquele produto antes
						// basta adicionar ao relatório de venda a quantidade q ele comprou
						// e descontar do estoque da loja
						produtoVenda.quantidade = produtoVenda.quantidade + quantidade;
					}

					produto.quantidade = produto.quantidade - quantidade;
					System.out.println("Compra efetuada com sucesso");
					System.out.println();
					venda.valorTotal = produto.preco * quantidade + venda.valorTotal;
					System.out.println(venda.valorTotal);

				} else {
					System.out.println("Compra descartada");
				}

				System.out.println("Deseja comprar mais alguma coisa?(1-sim, 2-não)");
				continuarVenda = sc.nextInt();

			}
		}
		return venda;

	}

	private static void listar(IListaMultidimensional lista) {
		lista.imprimir();
		// Produto p = lista.buscarProduto(4, 0);

	}

	private static void cadastrarPro(IListaMultidimensional lista) {
		int codCat;
		int codProd;
		String descricao;
		double valor;
		int qtd;

		System.out.println("Informe o codigo da categoria");
		codCat = sc.nextInt();
		Categoria categoria = lista.buscar(codCat);

		if (categoria != null) {
			System.out.println("Informe o código do produto:");
			codProd = sc.nextInt();
			sc.nextLine();
			Produto produto = lista.buscarProduto(codCat, codProd);

			if (produto == null) {
				System.out.println("Informe a descricao do produto");
				descricao = sc.nextLine();

				System.out.println("Informe o valor do produto");
				valor = sc.nextDouble();
				sc.nextLine();

				System.out.println("Informe a quantidade do produto");
				qtd = sc.nextInt();
				sc.nextLine();
				lista.inserirProduto(codCat, codProd, descricao, valor, qtd);
			}else {
				System.out.println("Essa código já pertence a algum produto" + 
						"\nListe para ver os códigos que já são usados");
							System.out.println();
			}
			// codProd++;
		} else {
			System.out.println("Categoria não encontrada. Não foi possível cadastrar o produto");
		}

	}

	private static void cadastrarCat(IListaMultidimensional lista) {
		String descricao;
		int codigo;
		System.out.println("Informe o código da categoria:");
		codigo = sc.nextInt();
		Categoria categoria = lista.buscar(codigo);
		if (categoria == null) {
			sc.nextLine();
			System.out.println("Informe a descricao da categoria:");
			descricao = sc.nextLine();

			lista.inserirCategoria(codigo, descricao);
		} else {
			System.out.println("Essa código já pertence a alguma categoria" + 
		"\nListe para ver os códigos que já são usados");
			System.out.println();
		}
		// codCat++;

	}

	public static void executar(IListaMultidimensional lista) {
		// CATEGORIAS

		lista.inserirCategoria(0, "Limpeza");
		// lista.imprimirCategoria();

		lista.inserirCategoria(1, "Alimento");
		// lista.imprimirCategoria();

		lista.inserirCategoria(2, "Automotivo");
		// lista.imprimirCategoria();

		lista.inserirCategoria(3, "Bebidas");
		// lista.imprimirCategoria();
		// System.out.println();

		// PRODUTOS
		// CAT0 - LIMPEZA

		lista.inserirProduto(0, 0, "Vassoura", 3.0, 100);
		// lista.imprimirProduto(0);

		lista.inserirProduto(0, 1, "Rodo", 3.5, 15);
		// lista.imprimirProduto(0);

		lista.inserirProduto(0, 2, "Pano", 5.0, 500);
		// lista.imprimirProduto(0);

		// System.out.println();

		// CAT1 - ALIMENTO

		lista.inserirProduto(1, 0, "Pao", 1.0, 1000);
		// lista.imprimirProduto(1);

		lista.inserirProduto(1, 1, "Miojo", 0.75, 2000);
		// lista.imprimirProduto(1);

		lista.inserirProduto(1, 2, "Queijo", 2.35, 58);
		// lista.imprimirProduto(1);

		// System.out.println();

		// CAT2 - AUTOMOTIVO

		lista.inserirProduto(2, 0, "Pneu", 100.0, 100);
		// lista.imprimirProduto(2);

		lista.inserirProduto(2, 1, "Limpador", 15.0, 50);
		// lista.imprimirProduto(2);

		lista.inserirProduto(2, 2, "Flanela", 20.0, 200);
		// lista.imprimirProduto(2);

		// System.out.println();

		// CAT3 - BEBIDAS

		lista.inserirProduto(3, 0, "Agua", 2.65, 6000);
		// lista.imprimirProduto(3);

		lista.inserirProduto(3, 1, "Refrigerante", 6.65, 7000);
		// lista.imprimirProduto(3);

		lista.inserirProduto(3, 2, "Suco", 5.25, 650);
		// lista.imprimirProduto(3);

		// System.out.println();

		// lista.imprimir();

		// System.out.println();

		// lista.removerCategoria(2);
		// lista.imprimir();

		// System.out.println();

		// lista.removerProduto(0, 1);
		// lista.imprimir();

	}

	public static void removerProduto(IListaMultidimensional lista) {
		System.out.println("Informe a categoria:");
		int codCat = sc.nextInt();
		System.out.println("Informe o código do produto");
		int codProd = sc.nextInt();
		Categoria categoria = lista.buscar(codCat);
		if (categoria != null)
			lista.removerProduto(codCat, codProd);
		else
			System.out.println("Categoria inexistente. Não foi possível remover o produto");

	}
}
