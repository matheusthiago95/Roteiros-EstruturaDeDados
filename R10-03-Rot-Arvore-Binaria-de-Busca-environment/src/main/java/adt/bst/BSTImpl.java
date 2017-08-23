package adt.bst;

import java.util.ArrayList;

import util.Util;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode.Builder().build();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}
	
	public int height(BSTNode<T> node){
		int alturaEsquerda = 0;
		int alturaDireita = 0;
		if(node.isEmpty()){
			return -1;
		}else{
			alturaEsquerda = 1 + this.height((BSTNode<T>) node.getLeft());
			alturaDireita = 1 + this.height((BSTNode<T>) node.getRight());
		}
		if(alturaEsquerda >= alturaDireita){
			return alturaEsquerda;
		}else{
			return alturaDireita;
		}
		
	}

	@Override
	public BSTNode<T> search(T element) {
		return this.search(this.root, element);
	}
	
	public BSTNode<T> search(BSTNode<T> node, T element){
		//*Verificar se o no é vazio primeiro, pois, se fizer equals para um null, da nullpointer. 
		if( node.isEmpty() || node.getData().equals(element)){ //Se o no é sentinela (NIL) ou se é o elemento procurado, retorna ele. (Caso Base). *Se cair no caso de ser no sentinela, é porque não tem mais aonde pesquisar.
			return node;
		}else{ //Caso recursivo
			if(element.compareTo(node.getData()) > 0){
				return this.search((BSTNode<T>) node.getRight(), element); //Se o elemento pesquisado for maior que o no que atual, pesquisa na subarvore da direita
			}else{ //Se o elemento a ser procurado for menor que o dado armazenado no no atual, pesquisa na subarvore da esquerda
				return this.search((BSTNode<T>) node.getLeft(), element);
			}
		}
	}

	@Override
	public void insert(T element) {
		this.insert(this.root, element);
	}
	
	//Metodo auxiliar
	public void insert(BSTNode<T> node, T element){
		if (node.isEmpty()){ //Se o no é sentinela
			node.setData(element);
			node.setLeft(new BSTNode.Builder().build()); //Left e Right sao definidos como nos sentinela (NIL). Para poder ser possivel setar o parent
			node.setRight(new BSTNode.Builder().build());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		}else{
			if(element.compareTo(node.getData()) > 0){
				this.insert((BSTNode<T>) node.getRight(), element);
			}else if (element.compareTo(node.getData()) < 0){
				this.insert((BSTNode<T>) node.getLeft(), element);
			}
		}
	}

	
	@Override
	public BSTNode<T> maximum() {
		//Se a arvore esta vazia, return null
		if(this.isEmpty()){
			return null;
		}
		return maximoAuxiliar(this.root);
		
	}

	@Override
	public BSTNode<T> minimum() {
		//Se a arvore esta vazia, retorna null
		if(this.isEmpty()){
			return null;
		}
		return minimoAuxiliar(this.root);
		
	}
	
	public BSTNode<T> minimoAuxiliar(BSTNode<T> node){
	
		if(node.getLeft().isEmpty()){ //caso base
			return node;
		}	
		return minimoAuxiliar( (BSTNode<T>) node.getLeft());
		
	}
	
	public BSTNode<T> maximoAuxiliar(BSTNode<T> node){
		if(node.getRight().isEmpty()){ //caso base
			return node;
		}
		return maximoAuxiliar((BSTNode<T>) node.getRight());
	}

	//Sucessor é a menor das chaves maiores
	@Override
	public BSTNode<T> sucessor(T element) {
		//Se a arvore esta vazia, retorna null, para mais a frente nao fazer maximum.getData
		if(this.isEmpty()){
			return null;
		}
		
		BSTNode node = this.search(element);
		T dataMaximo = this.maximum().getData();
		if(node.isEmpty() || element.compareTo(dataMaximo) >= 0){ //Se nao foi encontrado nenhum no, retorna null. Se o no achado é o maior da arvore/maximo, a sua direita é NIL ele nao tem sucessor, retorna null.
			return null;
		}
		if(!node.getRight().isEmpty()){
			return this.minimoAuxiliar((BSTNode<T>) node.getRight());
		}
		return this.getParentGreater(node);
	}
	
	//Metodo que pega um pai ou avô... Maior que o no atual (Menor pai ou avo maior que o no atual/dado no parametro - O primeiro maior que achar. (Usado no sucessor - Menor no maior que o atual) 
	public BSTNode<T> getParentGreater(BSTNode<T> node){

		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while(parent != null && parent.getData().compareTo(node.getData())< 0){
			parent = (BSTNode<T>) parent.getParent();
		}
	
		return parent;
	}

	//Maior dos menores
	@Override
	public BSTNode<T> predecessor(T element) {
		if(this.isEmpty()){ //Se a arvore for vazia, retorna null, para n fazer minimum.GetData mais a frente
			return null;
		}
		
		BSTNode<T> node = this.search(element);
		T minimoData = this.minimum().getData();
		if(node.isEmpty() || element.compareTo(minimoData) <= 0){ //Predecessor é o maior dos menores. Se nao foi achado o no, ou se a esquerda do no é NIL/Sentinela, então nao é possivel achar o maior node dos menores, ja que estamos tratando do minimo
			return null;
		}
		if(!node.getLeft().isEmpty()){
			return this.maximoAuxiliar((BSTNode<T>) node.getLeft());
		}
		return this.getParentSmaller(node);
	}
	
	//Metodo que pega um pai ou avô... Menor que o nó atual(Primeiro pai/avô... menor que o node passado como parametro) - Vai subindo até a raiz para achar
	public BSTNode<T> getParentSmaller(BSTNode<T> node){
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while(parent != null && parent.getData().compareTo(node.getData())>0){
			parent = (BSTNode<T>) parent.getParent();
		}
		return parent;
	}
	

	@Override
	public void remove(T element) {
		BSTNode<T> node = this.search(element); //Nó a ser removido
		if(!node.isEmpty()){ //Nao é possivel remover um no sentinela, entao verifica se o resultado da busca é um no sentinela.
			
			if(node.isLeaf()){ //Se é folha (nao tem filhos): Remove o nó, transformando-o em node sentinela.
				node.setData(null);
				node.setLeft(null);
				node.setRight(null);
			
			}else if(!node.isLeaf() && temApenasUmFilho(node)){ //Caso o node a ser removido NÃO seja folha e tenha apenas um filho
				if(!node.equals(root)){
					if(this.isLeftChild(node)){ //Se o node a ser removido for filho a esquerda:
						if(!node.getLeft().isEmpty()){ //Se o node tem um filho a esquerda, junta com a esquerda do pai
							node.getParent().setLeft(node.getLeft());
							node.getLeft().setParent(node.getParent());
						}else{ //Se a esquerda do node a ser removido for nula e a direita for ocupada por um filho
							node.getParent().setLeft(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					}else{ //Se o node a ser removido tem apenas um filho e o node a ser removido for filho a direita
						if(!node.getLeft().isEmpty()){ //Se o node a ser removido tiver filho a esquerda
							node.getParent().setRight(node.getLeft());
							node.getLeft().setParent(node.getParent());
						}else{ //Se o node a ser removido tiver filho a direita
							node.getParent().setRight(node.getRight());
							node.getRight().setParent(node.getParent());			
						}
					}
				}else{ //Para remover a raiz com um unico filho, a raiz passa a ser o filho que nao for vazio
					if (!node.getLeft().isEmpty()){ //Se a esquerda da raiz é ocupada
						this.root = (BSTNode<T>) node.getLeft();
						this.root.setParent(null);
					}else{//Se a esquerda é vazia/sentinela, entao a direita é ocupada
						this.root = (BSTNode<T>) node.getRight();
						this.root.setParent(null);
					}
				}
				
			}else if(!node.isLeaf() && !temApenasUmFilho(node)){ //Se o node nao é folha e tem dois filhos. Pega o sucessor, guarda em uma variavel. Remove ele, depois seta o no sendo o sucessor. Funciona usando predecessor
				BSTNode<T> sucessor = this.sucessor(node.getData());
				T data = sucessor.getData();
				this.remove(sucessor.getData());
				node.setData(data);
				
				
			}		
		}	
	}
	
	public boolean temApenasUmFilho(BSTNode<T> node){
		boolean retorno = false;
		if(node.getLeft().isEmpty() && !node.getRight().isEmpty()){
			retorno = true;
		}else if(!node.getLeft().isEmpty() && node.getRight().isEmpty()){
			retorno = true;
		}
		return retorno;
	}
	
	
	//Metodo que retorna se um no é filho a esquerda
	public boolean isLeftChild(BSTNode node){
		boolean retorno = false;
		if(!node.equals(root)){
			BSTNode<T> pai = (BSTNode<T>) node.getParent();
			retorno = pai.getLeft().equals(node);	
		}
		return retorno;
	}
	
	//Metodo que retorna se um no é filho a direita
	public boolean isRightChild(BSTNode<T> node){
		boolean retorno = false;
		if(!node.equals(root)){
			BSTNode<T> pai = (BSTNode<T>) node.getParent();
			retorno = pai.getRight().equals(node);
		}
		return retorno;
		
	}
	
	

	@Override
	public T[] preOrder() {
		T[] retorno = Util.makeArrayOfComparable(this.size());
		ArrayList<T> arrayL = new ArrayList<T>();
		preOrderAux(this.root, arrayL);
		for(int i = 0; i < this.size(); i++){
			retorno[i] = arrayL.get(i); 
		}
		return retorno;
	}
	
	//Método que coloca o dado do node no array, dado uma posicao para colocar(indice). -Auxiliar para preOrder, postOrder e InOrder/order
	public void preOrderAux(BSTNode<T> node, ArrayList<T> arrayL){
		//Coloca o no no array
		if(!node.isEmpty()){//Se o no não é sentinela, coloca no array.
			arrayL.add(node.getData());	
			preOrderAux((BSTNode<T>) node.getLeft(), arrayL);		
			preOrderAux((BSTNode<T>) node.getRight(), arrayL);
		}
		
	}
	

	@Override
	public T[] order() {
		T[] retorno = Util.makeArrayOfComparable(this.size());
		ArrayList<T> arrayL = new ArrayList<T>();
		orderAux(this.root, arrayL);
		for(int i = 0; i < this.size(); i++){
			retorno[i] = arrayL.get(i); 
		}
		return retorno;
	}
	
	public void orderAux(BSTNode<T> node, ArrayList<T> arrayL){
		if(!node.isEmpty()){
			orderAux((BSTNode<T>) node.getLeft(), arrayL);
			arrayL.add(node.getData());
			orderAux((BSTNode<T>) node.getRight(), arrayL);  
		}
	}

	@Override
	public T[] postOrder() {
		T[] retorno = Util.makeArrayOfComparable(this.size());
		ArrayList<T> arrayL = new ArrayList<T>();
		this.posOrderAux(root, arrayL);
		for(int i = 0; i < this.size(); i++){
			retorno[i] = arrayL.get(i); 
		}
		return retorno;
		
	}
	
	public void posOrderAux(BSTNode<T> node, ArrayList<T> arrayL){
		if(!node.isEmpty()){
			posOrderAux((BSTNode<T>) node.getLeft(), arrayL);
			posOrderAux((BSTNode<T>) node.getRight(), arrayL);
			arrayL.add(node.getData());
		}
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
