package produto;

/**
 * Classe que representa um repositório de produtos usando arrays como estrutura
 * sobrejacente. Alguns métodos (atualizar, remover e procurar) ou executam com
 * sucesso ou retornam um erro. Para o caso desde exercício, o erro será
 * representado por uma RuntimeException que não precisa ser declarada na
 * clausula "throws" do mos metodos.
 * 
 * Obs: Note que você deve utilizar a estrutura de dados array e não
 * implementações de array prontas da API Collections de Java (como ArrayList,
 * por exemplo).
 * 
 * @author Adalberto
 *
 */
public class RepositorioProdutoPerecivelArray implements RepositorioProduto {

	/**
	 * A estrutura (array) onde os produtos sao mantidos.
	 */
	private ProdutoPerecivel[] produtos;

	/**
	 * A posicao do ultimo elemento inserido no array de produtos. o valor
	 * inicial é -1 para indicar que nenhum produto foi ainda guardado no array.
	 */
	private int index = -1;

	public RepositorioProdutoPerecivelArray(int size) {
		super();
		this.produtos = new ProdutoPerecivel[size];
	}

	/**
	 * Recebe o codigo do produto e devolve o indice desse produto no array ou
	 * -1 caso ele nao se encontre no array. Esse método é util apenas na
	 * implementacao com arrays por questoes de localizacao. Outras classes que
	 * utilizam outras estruturas internas podem nao precisar desse método.
	 * 
	 * @param codigo
	 * @return
	 */
	private int procurarIndice(int codigo) {
		for (int i = 0; i < produtos.length; i++){
			if(produtos[i].getCodigo() == codigo){
				return i;		
			}
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see produto.RepositorioProduto#existe(int)
	 */
	@Override
	public boolean existe(int codigo) {
		for (int i = 0; i < produtos.length; i++){
			if(produtos[i].getCodigo() == codigo){
				return true;		
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see produto.RepositorioProduto#inserir(produto.ProdutoPerecivel)
	 */
	@Override
	public void inserir(Produto produto) {
		if(index < produtos.length-1){
			produtos[index + 1] = (ProdutoPerecivel) produto;
			index += 1;	
		}
	}

	/* (non-Javadoc)
	 * @see produto.RepositorioProduto#atualizar(produto.ProdutoPerecivel)
	 */
	@Override
	public void atualizar(Produto produto) {
		boolean replaced = false;
		for (int i = 0; i < produtos.length; i++){
			if (produtos[i].getCodigo() == produto.getCodigo()){
				produtos[i] = (ProdutoPerecivel) produto;
				replaced = true;
			}
		}
		if (replaced == false){
			System.out.println("erro");
		}
	}

	/* (non-Javadoc)
	 * @see produto.RepositorioProduto#remover(int)
	 */
	@Override
	public void remover(int codigo) {
		boolean removed = false;
		for (int i=0; i<produtos.length; i++){
			if (produtos[i].getCodigo() == codigo){
				produtos[i] = null;
				removed = true;
			}
			
			//tira "buracos" -- Leva o buraco para o final do array
			for (int j = i; j<produtos.length -1; j++){
				swap(produtos, j, j+1);
			}
		}
		if (removed == false)
			System.out.println("erro");
	}
	
	public void swap(ProdutoPerecivel[] array, int i, int j){
	     ProdutoPerecivel temp = array[i];
	     array[i] = array[j];
	     array[j] = temp;
	}

	/* (non-Javadoc)
	 * @see produto.RepositorioProduto#procurar(int)
	 */
	@Override
	public ProdutoPerecivel procurar(int codigo) {
		for (int i = 0; i < produtos.length; i++){
			if (produtos[i].getCodigo() == codigo){
				return produtos[i];
			}
		}
		System.out.println("erro");
		return null;
	}
}
