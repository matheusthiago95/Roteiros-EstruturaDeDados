package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.getHead().isNIL();
	}

	@Override
	public int size() {
		SingleLinkedListNode auxiliar = this.getHead();
		int contador = 0;
		while(!auxiliar.isNIL()){
			auxiliar = auxiliar.getNext();
			contador = contador + 1;
		}
		return contador;
	}

	@Override
	public T search(T element) {
		if(this.isEmpty()){
			return null;
		}else{
			SingleLinkedListNode auxiliar = this.getHead().getNext();
			while(!auxiliar.isNIL() && !auxiliar.getData().equals(element)){
				auxiliar = auxiliar.getNext();
			}
			if(!auxiliar.isNIL()){ //É porque NÃO chegou no node sentinela e achou o elemento
				return element;
			}else{ //É porque percorreu toda a linked e nao achou. Chegou no sentinela.
				return null;	
			}
		}
	}

	
	@Override
	public void insert(T element) {
		if(element != null){
			if(this.isEmpty()){ //Se a lista esta vazia
				this.getHead().setData(element);
				this.getHead().setNext(new SingleLinkedListNode<T>());
			}else{ //Se nao esta vazia
				SingleLinkedListNode<T> node = this.getHead().getNext();
				while(!node.isNIL()){ //Percorre da esq p/ dir ate achar um sentinela
					node = node.getNext();
				}
				node.setData(element);
				node.setNext(new SingleLinkedListNode<T>());
			}
		}
	}

	@Override
	public void remove(T element) {
		if(!this.isEmpty()){
			if(this.getHead().getData().equals(element)){ //Se o no a ser removido for o head, o head anda para a frente.
				this.head = this.getHead().getNext();
			}else{ //Se o no a ser removido nao é o head
				SingleLinkedListNode<T> auxiliar = this.getHead().getNext();
				SingleLinkedListNode<T> anterior = this.getHead();
				while(!auxiliar.isNIL() && !auxiliar.getData().equals(element)){
					anterior = auxiliar;
					auxiliar = auxiliar.getNext();
				}
				//Se achou: Muda os apontadores e ninguem aponta mais para o elemento removido
				if(auxiliar.getData().equals(element)){
					anterior.setNext(auxiliar.getNext());	
				}
			}
		}
	}

	@Override
	public T[] toArray() {
		T[] array = (T[]) new Object[this.size()];
		int indexCount = 0;
		SingleLinkedListNode auxiliar = this.getHead();
		while(!auxiliar.isNIL()){
			array[indexCount] = (T) auxiliar.getData();
			auxiliar = auxiliar.getNext();
			indexCount = indexCount + 1;
		}
		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}
	
	/*Pense em um método de uma lista que retorna o índice da primeira ocorrência de um
	elemento na lista. O índice varia entre 0 e o tamanho da lista -1. Caso o elemento não esteja
	na lista ele deve retornar -1.
	 indexOf(T elem)*/
	
	public int indexOf(T element){
		int retorno = -1;
		if(this.getHead().isNIL()){
			return retorno;
		}else{
			SingleLinkedListNode<T> auxiliar = this.getHead();
			retorno = retorno + 1;
			while(!auxiliar.isNIL() && !auxiliar.getData().equals(element)){
				auxiliar = auxiliar.getNext();
				retorno = retorno + 1;
			}
			//Se saiu do laco porque achou:
			if(!auxiliar.isNIL()){
				return retorno;
			}else{
				return -1;
			}
		}
	}
	
	/*Pense em um método de uma lista que retorna o índice da última ocorrência de um elemento
na lista. O índice varia entre 0 e o tamanho da lista -1. Caso o elemento não esteja na lista ele
deve retornar -1.*/

	
	public int lastIndexOf(T element){
		
		int retornoDefinitivo = -1; //Sera retornado no final, indice da ultima ocorrencia do elemento
		int index = -1;  //Variavel temporaria
		
		if(this.getHead().isNIL()){//Se a lista esta vazia
			return retornoDefinitivo;
		}else{//Se a lista tem elementos
			SingleLinkedListNode<T> auxiliar = this.getHead();
			index = index + 1;
			while(!auxiliar.isNIL()){ //Tem que percorrer toda a linkedList
				if(auxiliar.getData().equals(element)){
					retornoDefinitivo = index; //retornoDefinitivo vai atualizar sempre que, quando percorrendo, achar o elemento
				}
				index = index + 1;
				auxiliar = auxiliar.getNext();
			}
		}
		return retornoDefinitivo;
	}

	
	//Metodo que inverte uma lista encadeada.
	
	public void inverte(){
		T[] array = this.toArray();
		//remove todos
		int tamanho = this.size();
		while(!this.getHead().isNIL()){
			this.remove(this.getHead().getData());
		}
		for(int i = tamanho - 1; i >= 0; i--){
			this.insert(array[i]);
		}
		
	}
	
	public void removeWithIndex(int index){
		T[] array = this.toArray();
		this.remove(array[index]);
	}
}
