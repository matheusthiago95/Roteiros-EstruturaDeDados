package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place!
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T> {
	
	public void sort(T[] array, int leftIndex, int rightIndex) {
		int i = leftIndex; //Indice do pivot
		
		while(i < rightIndex){
		
			if(array[i].compareTo(array[i+1]) > 0){
				Util.swap(array, i, i+1);
				
				i++; //indice do pivot anda na medida que o pivot anda para frente.
				
				int j = i - 1; //j e como se fosse o pivot da subsequencia a esquerda.
			
				while(j > 0 && array[j].compareTo(array[j-1]) < 0){
		
					Util.swap(array, j, j-1);
					j--;
				}
				
			}else{
				i++; //Se nao entrar na condicao, so anda com o indice do pivot.		
			}
		}
		
	}
}
