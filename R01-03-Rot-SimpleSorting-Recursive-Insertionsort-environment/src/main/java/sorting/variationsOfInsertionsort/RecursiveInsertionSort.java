package sorting.variationsOfInsertionsort;

import sorting.AbstractSorting;

public class RecursiveInsertionSort<T extends Comparable<T>> extends
		AbstractSorting<T> {

	/**
	 * Implementacaoo RECURSIVA do insertion sort. Para isso, tente definir o 
	 * caso base do algoritmo e depois o caso recursivo, que reduz o problema 
	 * para uma entrada menor em uma chamada recursiva. Seu algoritmo deve 
	 * ter complexidade quadratica O(n^2).
	 * 
	 * Restrições:
	 *  - voce nao pode utilizar arry auxiliar
	 *  - voce pode utilizar variaveis temporarias
	 *  - voce nao pode declarar novos atributos na classe
	 *  - para as trocas no array, utilize o metodo Util.swap
	 */
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex==rightIndex || array.length==0){
			//Nao faz nada, caso base
		}else{
			sort(array, leftIndex, rightIndex-1);
			//Chama o insertion ate chegar ao caso base. 
			T key = array[rightIndex];
			int j = rightIndex - 1;
			
			while (j>=0 && array[j].compareTo(key)>0){
				array[j+1] = array[j]; //Afastando o maior para a direita.
				j = j - 1; //Vai comparar com outro para afastar p/ direita se necessario, por isso o decremento.
			}
			array[j+1] = key;
			
			
		}
		
			
		
	}

}
