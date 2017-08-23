package adt.btree;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	//Considerar que ela sempre esta equilibrada
	private int height(BNode<T> node) {
		if(this.isEmpty()){ //Se a arvore é vazia, retorna -1
			return -1;
		}else if(node.isLeaf()){
			return 0; //Pelo fato da raiz estar na altura 0, entao a folha retorna 0 para compensar que a no caso da raiz foi contado +1
		}else{
			return 1 + height(node.getChildren().get(0));
		}
	}

	@Override
	public BNode<T>[] depthLeftOrder() {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public int size() {
		return this.size(this.root);
	}
	
	//Recebe um no e conta os elementos do no e chama a si mesmo para a todos os filhos. condicao de parada somente se o no for vazio
	public int size(BNode node){
		int tamanho = 0;
		if(node.isEmpty()){
			return tamanho;
		}else{
			tamanho += sizeOfNode(node);
			for(int i = 0; i < node.getChildren().size(); i++){
				tamanho += size((BNode) node.getChildren().get(i));
			}
		}
		return tamanho;
	}
	
	//Metodo auxiliar que retorna o tamanho de um node
	public int sizeOfNode(BNode node){
		return node.getElements().size();
	}

	@Override
	public BNodePosition<T> search(T element) {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void insert(T element) {
		this.insert(element, root);

	}
	
	
	public void insert(T element, BNode node){ 
		if(node.isLeaf()){ //Se o node é folha, adiciona o elemento nela normalmente e depois trata
			node.addElement(element);
			//Um node tem no maximo (ordem - 1 = k) k chaves.
			if(node.getElements().size()  > this.order - 1){
				this.split(node);
			}
		}else{ //Se o node nao for folha, devemos chamar o insert para um bnode que seja filho deste node, para achar o bnode correto, percorremos a linked
			//Acha o indice do elemento que é maior que o elemento a ser adicionado no node para relacionar com o children onde iremos chamar o insert recursivamente
			int indiceDoChildren = node.size();
			for(int i = 0; i < node.size(); i++){
				if (node.getElementAt(i).compareTo(element) > 0){
					indiceDoChildren = i;
					break; //Da um break no for, pois senao vai ficar atualizando os indices e queremos o primeiro elemento maior
				}
			}
			this.insert(element, (BNode) node.getChildren().get(indiceDoChildren));
		}
	}

	private void split(BNode<T> node) {
		//Ordem = numero maximo de filhos. Um node pode ter no maximo m - 1 chaves, onde m é a ordem
		if(node.getElements().size() > this.order - 1){
			int indiceMediana = node.getElements().size()/2; //Como as chaves estao ordenadas - Mediana e o elemento central
			this.promote(node); //Adiciona a mediana no node pai
			//Novos nodes para valores menores e maiores que a mediana respectivamente
			BNode nodeEsquerdo = new BNode(this.order);
			BNode nodeDireito = new BNode(this.order);
			for(int i = 0; i < node.size(); i++){
				if(node.getElementAt(i).compareTo(node.getElementAt(indiceMediana)) > 0){
					nodeDireito.addElement(node.getElementAt(i));
				}else if(node.getElementAt(i).compareTo(node.getElementAt(indiceMediana)) < 0){
					nodeEsquerdo.addElement(node.getElementAt(i));
				}
			}
			//Indice do node no nó pai (ou seja, o indice dele no node pai depois de ter sido promovido)
			int indiceDoNodeJaPromovido = node.getParent().indexOfChild(node);
			BNode parent = node.getParent();
			parent.removeChild(node); 
			parent.addChild(indiceDoNodeJaPromovido, nodeEsquerdo);
			parent.addChild(indiceDoNodeJaPromovido+1, nodeDireito);
			if(parent.getElements().size() >= this.order - 1){
				this.split(parent);
			}
			
		}
		
		
		
	}

	private void promote(BNode<T> node) {
		int indiceMediana = node.getElements().size() / 2;
		//Caso o no que esteja com overflow seja a raiz
		if(node.getParent() == null){
			node.setParent(new BNode(this.order));
		}
		node.getParent().addElement(node.getElementAt(indiceMediana));
		
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
