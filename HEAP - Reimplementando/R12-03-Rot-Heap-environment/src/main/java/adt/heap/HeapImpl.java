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
		int esq = this.left(position);
		int dir = this.right(position);
		int maior = position;
		
		//esq e dir precisam existir, checa isso
		if(esq <= this.index && this.comparator.compare(this.heap[esq], this.heap[position]) > 0){
			maior = esq;
		}
		if(dir <= this.index && this.comparator.compare(this.heap[dir], this.heap[maior]) > 0){
			maior = dir;
		}
		if(maior != position){
			Util.swap(heap, position, maior);
			this.heapify(maior);
		}
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		// /////////////////////////////////////////////////////////////////
		if(element != null){
			this.index++;
			this.heap[index] = element;
			int auxiliar = this.index; // p/ n alterar o index. var auxiliar
			
			//Vai subindo trocando enquanto for necess�rio
			while(auxiliar > 0 && this.comparator.compare(this.heap[auxiliar], this.heap[this.parent(auxiliar)]) > 0){
				Util.swap(heap, auxiliar, this.parent(auxiliar));
				auxiliar = this.parent(auxiliar);
			}			
		}
	}
	
	//Inserir em uma heap onde os nodes da direita sao maiores que o da esquerda
	public void insert1(T element){
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
				if (index == heap.length - 1) {
					heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
				}
				// /////////////////////////////////////////////////////////////////
				if(element != null){
					this.index++;
					this.heap[index] = element;
					int auxiliar = this.index; // p/ n alterar o index. var auxiliar
					
					//Vai subindo trocando enquanto for necess�rio, faz essa troca primeiro, pois ela ja garante que se for trocar com a direita, o irmao a esquerda vai ser menor obrigatoriamente
					while(auxiliar > 0 && this.comparator.compare(this.heap[auxiliar], this.heap[this.parent(auxiliar)]) > 0){
						Util.swap(heap, auxiliar, this.parent(auxiliar));
						auxiliar = this.parent(auxiliar);
					}
					
					//Se o node adicionado for filho a direita depois do insert normal, v� se ele � menor que o irmao (filho a esquerda do pai)
					if(auxiliar > 0 && this.heap[this.right(this.parent(auxiliar))].compareTo(this.heap[auxiliar]) == 0){
						if(this.comparator.compare(this.getHeap()[auxiliar], this.getHeap()[this.left(this.parent(auxiliar))]) < 0){
							Util.swap(this.getHeap(), auxiliar, this.left(this.parent(auxiliar)));
						}
					}
				}
	}
	
	
	//Retorna o array da heap alternado. Retirando-se o maior, depoiso menor, o maior, depois o menor...
	@Override
	public T[] retornaArrayAlternado(){
		
		//Para o caso de recebermos uma min heap
		this.setComparator(new Comparator<T>(){

			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
			
		});
		
		T[] array = this.toArray();
		this.buildHeap(array);
 		
		T[] arrayRetorno = Util.makeArrayOfComparable(this.size());
		for(int i = 0; i < arrayRetorno.length; i++){
			if(i % 2 == 0){ //Se o numero � par:
				arrayRetorno[i] = this.extractRootElement();
			}
		}
		
		//Coloca todos os elementos "maiores" nos indices par(toda vez q for extrai um maior, ele vai pro indice par)
		
		//A heap agora passa a ser uma min heap
		
		this.setComparator(new Comparator<T>(){

			@Override
			public int compare(T o1, T o2) {
				return o2.compareTo(o1);
			}
			
		});
		
		array = this.toArray();
		this.buildHeap(array);
		
		
		for(int i = 0; i < arrayRetorno.length; i++){
			if(arrayRetorno[i] == null){
				arrayRetorno[i] = this.extractRootElement();				}
			}
		
		return arrayRetorno;
		
	}

	@Override
	public void buildHeap(T[] array) {
		this.heap = array;
		this.index = -1;
		for(int i = 0; i < array.length; i++){ //seguranca.
			if(array[i] != null){
				this.index++;
			}
		}
		
		for(int i = index / 2; i >= 0; i--){
			this.heapify(i);
		}
	}

	@Override
	public T extractRootElement() {
		T elementoRemoved;
		if(this.isEmpty()){
			return null;
		}else{
			elementoRemoved = this.getHeap()[0];
			this.getHeap()[0] = this.getHeap()[this.index];
			this.index--;
			this.heapify(0);
		}
		return elementoRemoved;
	}

	@Override
	public T rootElement() {
		if(!this.isEmpty()){
			return this.getHeap()[0];
		}
		return null;
	}

	@Override
	public T[] heapsort(T[] array) {
		//Assumi que o array passado no parametro nao tem elementos null, ou seja, esta completo
		this.setComparator(new Comparator<T>(){ //Inverte o comparator para funcionar como min heap

			@Override
			public int compare(T o1, T o2) {
				return o2.compareTo(o1);
			}
			
		});
		
		this.buildHeap(array);
		//fazer remocoes antes, pois o extractRootElement usa o heapify
		T[] arrayRetorno = Util.makeArrayOfComparable(array.length);
		for(int i = 0; i < arrayRetorno.length; i++){
			arrayRetorno[i] = this.extractRootElement();
		}
		
		this.setComparator(new Comparator<T>(){

			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
			
		});
		
		return arrayRetorno;
		
		
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
