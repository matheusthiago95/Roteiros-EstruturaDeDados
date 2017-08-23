package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		Integer[] auxiliar = new Integer[array.length]; //Esse será o array auxiliar, que conterá a resposta final.
		
		//Trecho de código para descobrir qual é o maximo inteiro presente.
		int maxInteiro = 0;
		int minInteiro = 0;
		for(int i = leftIndex; i <= rightIndex; i++){
			if (array[i] >= maxInteiro)
				maxInteiro = array[i];
			if(array[i] <= minInteiro){
				minInteiro = array[i];
			}
		}
		
		//inicializa um array de contadores com o tamanho sendo o maximo inteiro - min inteiro presente no array da entrada +1 por causa de zeros
		//e inicializa cada posicao com 0.
		Integer[] arrayContadores = new Integer[(maxInteiro - minInteiro)+1];
		for (Integer i = 0; i < arrayContadores.length; i++){
			arrayContadores[i] = 0;
		}
		
		int indiceDoZero = 0 - minInteiro; //Onde esta o indice 0 no array
		
		
		//Contagem dos elementos
		for (int i = leftIndex; i <= rightIndex; i++){
			if(array[i]>=0){
				arrayContadores[indiceDoZero + array[i]]+=1;	
			}else{
				arrayContadores[indiceDoZero - ((-1) * array[i])] += 1;   //Exemplo de array: [0 0 0 0 0 0 0 0]
																		  //Indices do array =-5-4-3-2-1 0 1 2    --Indice do 0 = 5.
			}
		}
		//Soma dos prefixos
		for (int i = 1; i < arrayContadores.length; i++){
			arrayContadores[i] += arrayContadores[i-1];
		}
		//Colocando o array recebido como um array ordenado usando o array auxiliar.
		//Percorremos de trás pra frente - ordenacao estavel.
		for (int i = rightIndex; i >= leftIndex; i--){
			if(array[i] >= 0){
				arrayContadores[indiceDoZero + array[i]]--;
				auxiliar[arrayContadores[indiceDoZero + array[i]]] = array[i];		
			}else{
				arrayContadores[indiceDoZero - ((-1) * array[i])]--;
				auxiliar[arrayContadores[indiceDoZero - ((-1) * array[i])]] = array[i];
			}
		}
		//Transcreve os elementos do array auxiliar(ordenado) para o array normal(desordenado).
		for (int i = leftIndex; i <= rightIndex; i++){
			array[i] = auxiliar[i];
		}
	}

}
