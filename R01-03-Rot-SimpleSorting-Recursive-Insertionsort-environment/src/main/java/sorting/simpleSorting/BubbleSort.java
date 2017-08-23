package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * The bubble sort algorithm iterates over the array multiple times, pushing big
 * elements to the right by swapping adjacent elements, until the array is
 * sorted.
 */
public class BubbleSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		boolean houveTrocas = true;
		while (houveTrocas){
			houveTrocas = false;
			//Se varrer o array todo e nao trocar, e pq ja esta ordenado. Sai do while...
			for (int i = leftIndex; i < rightIndex; i++){
				if (array[i].compareTo(array[i+1])>0){
					Util.swap(array, i, i+1);
					houveTrocas = true;
				}
			}
			
			
		}
		
	}
}
