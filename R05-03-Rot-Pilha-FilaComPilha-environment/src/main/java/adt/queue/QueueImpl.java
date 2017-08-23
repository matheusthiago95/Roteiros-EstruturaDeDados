package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = -1;
	}

	@Override
	public T head() { //Retorna o elemento mais antigo se a fila tiver elementos
		if(!this.isEmpty()){
			return this.array[0];
		}else{
			return null;
		}
	}

	@Override
	public boolean isEmpty() { //Se o tail estiver como padrão (=-1), a fila esta vazia.
		return this.tail == -1;
	}

	@Override
	public boolean isFull() {
		return this.tail == this.array.length - 1;  //Se o tail(cauda) que é o indice do ultimo elemento da fila estiver na ultima pos do array, a fila esta cheia
	}

	private void shiftLeft() { //Move todos os elementos para a esquerda.
		for(int i = 0; i < this.tail; i++){
			this.array[i] = this.array[i+1];
		}
	}

	@Override //Se a fila nao estiver cheia, adiciona um novo elemento nela
	public void enqueue(T element) throws QueueOverflowException {
		if(element != null){
			if(!this.isFull()){
				this.tail++;
				this.array[tail] = element;
			}else{
				throw new QueueOverflowException();
			}
		}
	}

	@Override //Os elementos a serem removidos em uma FILA sao os mais antigos
	public T dequeue() throws QueueUnderflowException {
		if(!this.isEmpty()){
			T oldest = this.array[0];
			this.shiftLeft();
			tail--;
			return oldest;
		}else{ //Se a fila estiver vazia:
			throw new QueueUnderflowException();
		}
	}
	/*Pense em um método de uma lista que retorna o índice da primeira ocorrência de um
	elemento na lista. O índice varia entre 0 e o tamanho da lista -1. Caso o elemento não esteja
	na lista ele deve retornar -1.
	 indexOf(T elem)*/

}
