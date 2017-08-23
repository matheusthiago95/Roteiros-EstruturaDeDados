package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparação não é feita 
 * diretamente com os elementos armazenados, mas sim usando um comparator. 
 * Dessa forma, dependendo do comparator, a heap pode funcionar como uma max-heap 
 * ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = Util.makeArrayOfComparable(index + 1);
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		int maior = position; //indice do maior
		int filhoEsquerda = this.left(position); //indice filho esq
		int filhoDireita = this.right(position); //indice filho dir
		//Acha o maior - Compara posicao e esquerda e depois no outro if compara o maior dos dois anteriores com a dir
		if(filhoEsquerda <= this.index && this.getComparator().compare(this.getHeap()[filhoEsquerda], this.getHeap()[position]) > 0){
			maior = filhoEsquerda;
		}
		if(filhoDireita <= this.index && this.getComparator().compare(this.getHeap()[filhoDireita], this.getHeap()[maior]) > 0){
			maior = filhoDireita;
		}
		if(maior != position){ //Se entra nesse if, significa que devera ser feita uma troca, pois um dos filhos e maior, o maior foi modificado
			Util.swap(getHeap(), position, maior);
			this.heapify(maior);
		}
		
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		this.index = index + 1;
		int auxiliar = index; //Variavel auxiliar que podera ser decrementada. Usada para nao modificar o index
		this.getHeap()[index] = element;
		//Vai trocando com o pai  ate achar a posicao correta
		while (auxiliar > 0 && this.getComparator().compare(this.getHeap()[auxiliar], this.getHeap()[this.parent(auxiliar)]) > 0){
			Util.swap(getHeap(), auxiliar, this.parent(auxiliar));
			auxiliar = this.parent(auxiliar);
		}
	}

	@Override
	public void buildHeap(T[] array) {
		this.heap = array;
		this.index = -1;
		for(int i = 0; i < array.length; i++){
			if(array[i] != null){
				this.index++; //Novo index, pois se a heap ja tem elementos e se quer construir uma nova em cima dela, ela deve ser resetada
			}
		}
		
		int i = this.index / 2; //Variavel auxiliar para o laco
		while(i >= 0){
			this.heapify(i);
			i--;
		}
	}

	@Override
	public T extractRootElement() {
		T elementoRemoved;
		if(this.isEmpty()){
			elementoRemoved = null;
		}else{
			if(this.size() == 1){ //Se so tem a raiz
				elementoRemoved = this.getHeap()[0];
				this.index--;
			}else{ //Se tem 2 ou mais elementos
				elementoRemoved = this.getHeap()[0];
				this.getHeap()[0] = this.getHeap()[index];
				index--;
				this.heapify(0);
			}
		}
		return elementoRemoved;
	}

	@Override
	public T rootElement() {
		if (this.isEmpty()){
			return null;
		}else{
			return this.getHeap()[0];
		}
	}

	
	//Adiciona tudo em uma minHeap (para isso, muda o comparator por um instante) depois remove tudo da min heap adicionando no array de retorno e antes de retornar o array de retorno
	// seta o comparator de volta para o normal
	@Override
	public T[] heapsort(T[] array) {
		this.setComparator(new Comparator<T>(){

			@Override
			public int compare(T o1, T o2) {
				return o2.compareTo(o1);
			}
			
		});
		this.buildHeap(array);
		
		T[] retorno = Util.makeArrayOfComparable(this.size());
		//add no array de retorno
		for (int i = 0; i < retorno.length; i++){
			retorno[i] = this.extractRootElement();
		}
		
		//Seta o comparator para o normal de volta
		this.setComparator(new Comparator<T>(){

			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
			
		});
		return retorno;
	}

	@Override
	public int size() {
		return this.index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

}
