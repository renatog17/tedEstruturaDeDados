package br.renato.bes.ed20191.ted;

public class ListaMultidimensional implements IListaMultidimensional {

	Categoria inicioCat = null;

	public Categoria inserirCategoria(int codigo, String descricao) {
		Categoria novo = new Categoria();
		novo.codigo = codigo;
		novo.descricao = descricao;

		if (inicioCat == null) {
			inicioCat = novo;
		} else {
			Categoria aux = inicioCat;
			while (aux.prox != null) {
				aux = aux.prox;
			}
			aux.prox = novo;
		}

		return novo;
	}

	public void removerCategoria(int codigo) {
		if (inicioCat == null) {
			System.out.println("Lista vazia!");
		} else {
			Categoria ant = null;
			Categoria aux = inicioCat;
			while (aux.prox != null && aux.codigo != codigo) {
				ant = aux;
				aux = aux.prox;
			}
			if (aux.codigo == codigo) {
				if (ant == null) {
					inicioCat = inicioCat.prox;
				} else if (aux.prox == null) {
					ant.prox = null;
				} else {
					ant.prox = aux.prox;
				}
			}
		}

	}

	public Categoria buscar(int codigo) {
		if (inicioCat == null) {
			//System.out.println("Lista vazia!");
		} else {
			Categoria aux = inicioCat;
			while (aux.prox != null && aux.codigo != codigo) {
				aux = aux.prox;
			}
			if (aux.codigo == codigo) {
				return aux;
			}
		}
		return null;
	}

	public void imprimirCategoria() {
		if (inicioCat == null) {
			System.out.println("Lista vazia!");
		} else {
			Categoria aux = inicioCat;
			while (aux != null) {
				System.out.print(aux.codigo + " " + aux.descricao + "; ");
				aux = aux.prox;
			}
		}
		System.out.println("");

	}

	public Produto inserirProduto(int codCat, int codigo, String descricao, double preco, int quantidade) {
		Produto novo = new Produto();
		novo.codigo = codigo;
		novo.descricao = descricao;
		novo.preco = preco;
		novo.quantidade = quantidade;

		if (inicioCat == null) {
			System.out.println("Não existem categorias");
		} else {
			Categoria cat = buscar(codCat);
			if (cat == null) {
				System.out.println("Categoria não encontrada");
			} else {
				if (cat.inicio == null) {
					cat.inicio = novo;
				} else {
					Produto aux = cat.inicio;
					while (aux.prox != null) {
						aux = aux.prox;
					}
					aux.prox = novo;
				}
				return novo;
			}
		}

		return null;
	}

	public void removerProduto(int codCat, int codigo) {
		if (inicioCat == null) {
			System.out.println("Lista vazia!");
		} else {
			Categoria cat = buscar(codCat);
			if (cat == null) {
				System.out.println("Categoria não encontrada");
			} else {
				Produto ant = null;
				Produto aux = cat.inicio;
				while (aux.prox != null && aux.codigo != codigo) {
					ant = aux;
					aux = aux.prox;
				}
				if (aux.codigo == codigo) {
					if (ant == null) {
						cat.inicio = cat.inicio.prox;
					} else if (aux.prox == null) {
						ant.prox = null;
					} else {
						ant.prox = aux.prox;
					}
				}
			}

		}

	}

	public Produto buscarProduto(int codCat, int codigo) {
		if (inicioCat == null) {
			System.out.println("Lista vazia!");
		} else {
			Categoria cat = buscar(codCat);

			if (cat == null) {
				System.out.println("Categoria não encontrada");
			} else {
				if(cat.inicio==null)
					return null;
				
				Produto aux = cat.inicio;
				while (aux.prox != null && aux.codigo != codigo) {
					aux = aux.prox;
				}
				if (aux.codigo == codigo) {
					return aux;
				}

			}

		}
		return null;
	}

	public void imprimirProduto(int codCat) {
		if (inicioCat == null) {
			System.out.println("Categoria vazia!");
		} else {
			Categoria cat = buscar(codCat);

			if (cat == null) {
				System.out.println("Categoria não encontrada");
			} else {
				Produto aux = cat.inicio;
				while (aux != null) {
					System.out.println(aux.descricao + " R$ " + aux.preco + ", codigo - " + aux.codigo + ", estoque - " + aux.quantidade);
					aux = aux.prox;
				}
			}

		}
		System.out.println("");

	}
	
	public void imprimir() {
		if (inicioCat == null) {
			System.out.println("Lista vazia!");
		} else {
			Categoria aux = inicioCat;
			while (aux != null) {
				System.out.println(aux.descricao + ", Codigo categoria - " + aux.codigo);
				imprimirProduto(aux.codigo);
				aux = aux.prox;
				//System.out.println();
			}
		}
		//System.out.println("");

	}
	

}
