package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		//cria auxiliar
		Integer[] auxiliar = new Integer[array.length];
		//passa elementos para o aux
		for(int i = leftIndex; i <= rightIndex; i++){
			auxiliar[i] = array[i];
		}
		
		//pesquisa o maior
		Integer maior = 0;
		for(int i = leftIndex; i <= rightIndex; i++){
			if(array[i].compareTo(maior)>0){
				maior = array[i];
			}
		}
		
		
		
		//cria arrayContadores
		int[] arrayContadores = new int[maior+1]; //+1 pois estamos considerando o 0.
		
		//contagem dos elementos
		for(int i = leftIndex; i <= rightIndex; i++){
			arrayContadores[array[i]]++;
		}
		
		//soma dos prefixos
		for(int i = 1; i < arrayContadores.length; i++){
			arrayContadores[i] += arrayContadores[i-1];
		}
		
		//ordenando o array recebido
		for (int i = rightIndex; i >= leftIndex; i--){
			arrayContadores[auxiliar[i]]--; //Deve se usar o auxiliar ja que a cada iteracao o normal vai se modificando
			array[arrayContadores[auxiliar[i]]] = auxiliar[i];
		}
	}

}
