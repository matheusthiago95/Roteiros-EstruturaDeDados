package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data,
			RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	//Tratar cada no como se fosse uma lista
	//Caso base - Lista vazia // Caso recursivo - Lista nao vazia
	
	@Override
	public boolean isEmpty() {
		return this.data == null;
	}

	@Override
	public int size() {
		if(this.isEmpty()){ //Caso base
			return 0;
		}else{
			return 1 + this.getNext().size(); //Caso recursivo
		}
	}

	@Override
	public T search(T element) {	
		//Caso base
		if(this.isEmpty()){
			return null;
			
		}else{ //Caso recursivo
			if(this.getData() == element){
				return this.getData();
			}else{
				return this.getNext().search(element);
			}
		}
	}

	@Override
	public void insert(T element) {
	
		//Elementos null devem ser ignorados
		if(element!=null){
			
			//Tratar cada nó como se fosse uma lista.
			//Se for sentinela, add, senao, chama insert para o prox
			if(this.isEmpty()){
				this.setData(element);
				this.setNext(new RecursiveSingleLinkedListImpl<T>());
		
			}else{
				this.getNext().insert(element);
			}
			
		}
	}

	@Override
	public void remove(T element) {
		//Se o no atual e null, nao faz nada
		if(this.isEmpty()){
			
		}else{
			//Se nao é no sentinela, e é o elemento a ser removido:
			if(this.getData() == element){
				this.setData(this.getNext().getData());
				this.setNext(this.getNext().getNext());
				
				
			}else{//Se nao é o elemento a ser removido, tenta realizar o processo para o proximo no
				this.getNext().remove(element);
			}
		}
	}

	@Override
	public T[] toArray() {
		int count = 0; //Contador para adicionar o elemento na posicao correta.
		T[] array = (T[]) new Object[this.size()];
		this.toArray(array, this, count);
		return array;
		
	}
	
	//Vai adicionando os elementos no array ate encontrar um elemento null. Recebe a posicao a ser add, para nao ficar resetanto a posicao no array.
	public void toArray(T[] array, RecursiveSingleLinkedListImpl<T> no, int posicao){	
		
		//Se e um nó sentinela, nao adiciona nada - Caso base
		if(no.isEmpty()){
			
		}else{ //Se nao é um no sentinela, adiciona no array e incrementa o count e chama o metodo para o proximo.
			//Enquanto o elemento nao for null, vai adicionando no array
			array[posicao] = no.getData();
			posicao = posicao + 1; //Atualiza a posicao no array a ser adicionado um elemento
			toArray(array, no.getNext(), posicao);
		}
		
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
