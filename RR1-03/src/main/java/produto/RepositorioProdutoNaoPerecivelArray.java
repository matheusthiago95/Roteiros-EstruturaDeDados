package produto;

/**
 * Classe que representa um repositÃ³rio de produtos usando arrays como estrutura
 * sobrejacente. Alguns mÃ©todos (atualizar, remover e procurar) ou executam com
 * sucesso ou retornam um erro. Para o caso desde exercÃ­cio, o erro serÃ¡
 * representado por uma RuntimeException que nÃ£o precisa ser declarada na
 * clausula "throws" do mos metodos.
 * 
 * Obs: Note que vocÃª deve utilizar a estrutura de dados array e nÃ£o
 * implementaÃ§Ãµes de array prontas da API Collections de Java (como ArrayList,
 * por exemplo).
 * 
 * @author Adalberto
 *
 */
public class RepositorioProdutoNaoPerecivelArray implements RepositorioProduto {
   /**
    * A estrutura (array) onde os produtos sao mantidos.
    */
   private ProdutoNaoPerecivel[] produtos;

   /**
    * A posicao do ultimo elemento inserido no array de produtos. o valor
    * inicial Ã© -1 para indicar que nenhum produto foi ainda guardado no array.
    */
   private int index = -1;

   public RepositorioProdutoNaoPerecivelArray(int size) {
      super();
      this.produtos = new ProdutoNaoPerecivel[size];
   }

   /**
    * Recebe o codigo do produto e devolve o indice desse produto no array ou
    * -1 caso ele nao se encontre no array. Esse mÃ©todo Ã© util apenas na
    * implementacao com arrays por questoes de localizacao. Outras classes que
    * utilizam outras estruturas internas podem nao precisar desse mÃ©todo.
    * 
    * @param codigo
    * @return
    */
   private int procurarIndice(int codigo) {
      for (int i = 0; i < produtos.length; i++) {
         if (produtos[i].getCodigo() == codigo) {
            return i;
         }
      }
      return -1;
   }

   /**
    * Recebe o codigo e diz se tem produto com esse codigo armazenado
    * 
    * @param codigo
    * @return
    */
   public boolean existe(int codigo) {
      for (int i = 0; i < produtos.length; i++) {
         if (produtos[i].getCodigo() == codigo) {
            return true;
         }
      }
      return false;
   }

   /**
    * Insere um novo produto (sem se preocupar com duplicatas)
    */
   public void inserir(Produto produto) {
      if (index < produtos.length - 1) {
         produtos[index + 1] = (ProdutoNaoPerecivel) produto;
         index += 1;
      }

   }

   /**
    * Atualiza um produto armazenado ou retorna um erro caso o produto nao
    * esteja no array. Note que, para localizacao, o cÃ³digo do produto serÃ¡
    * utilizado.
    */
   public void atualizar(Produto produto) {
      boolean replaced = false;
      for (int i = 0; i < produtos.length; i++) {
         if (produtos[i].getCodigo() == produto.getCodigo()) {
            produtos[i] = (ProdutoNaoPerecivel) produto;
            replaced = true;
         }
      }
      if (replaced == false) {
         System.out.println("erro");
      }
   }

   /**
    * Remove produto com determinado codigo, se existir, ou entao retorna um
    * erro, caso contrÃ¡rio. Note que a remoÃ§Ã£o NÃƒO pode deixar "buracos" no
    * array.
    * 
    * @param codigo
    */
   public void remover(int codigo) {
      boolean removed = false;
      for (int i = 0; i < produtos.length; i++) {
         if (produtos[i].getCodigo() == codigo) {
            produtos[i] = null;
            removed = true;
         }

         //tira "buracos" -- Leva o buraco para o final do array
         for (int j = i; j < produtos.length - 1; j++) {
            swap(produtos, j, j + 1);
         }
      }
      if (removed == false)
         System.out.println("erro");
   }

   public void swap(ProdutoNaoPerecivel[] array, int i, int j) {
      ProdutoNaoPerecivel temp = array[i];
      array[i] = array[j];
      array[j] = temp;
   }

   /**
    * Retorna um produto com determinado codigo ou entao um erro, caso o
    * produto nao esteja armazenado
    * 
    * @param codigo
    * @return
    */
   @Override
   public Produto procurar(int codigo) {
      for (int i = 0; i < produtos.length; i++) {
         if (produtos[i].getCodigo() == codigo) {
            return produtos[i];
         }
      }
      System.out.println("erro");
      return null;
   }

}
