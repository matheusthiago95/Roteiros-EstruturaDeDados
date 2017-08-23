package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		int gap = leftIndex-rightIndex + 1; //Numero de posicoes.
		
		boolean houveTrocas = true;
		
		while(houveTrocas && array != null){

			houveTrocas = false;
			gap = (int) (gap / 1.25);
			

			if(gap < 1){
				gap = 1;	
			}
			
			int j = leftIndex;
			
			for (int i = leftIndex+gap; i <= rightIndex; i++){
				if(array[i].compareTo(array[j]) < 0 ){
					Util.swap(array, i, j);
					houveTrocas = true;
				}
				j++;
			}
			
		}
		
	}
}
