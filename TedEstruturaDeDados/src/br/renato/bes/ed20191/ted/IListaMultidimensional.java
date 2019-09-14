package br.renato.bes.ed20191.ted;

public interface IListaMultidimensional {
	public Categoria inserirCategoria(int codigo, String descricao);
	public void removerCategoria(int codigo);
	public Categoria buscar(int codigo);
	public void imprimirCategoria();
	public Produto inserirProduto(int codCat, int codigo, String descricao, double preco, int quantidade);
	public void removerProduto(int codCat, int codigo);
	public Produto buscarProduto(int codCat, int codigo);
	public void imprimirProduto(int codCat);
	public void imprimir();;

}
