package adt.avltree;

import java.util.ArrayList;
import java.util.Arrays;


import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {
	
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	
	//O "correto"/(sem o erro) foi o enviado, esta nas submissoes
	@Override
	public void fillWithoutRebalance(T[] array) {
		T[] arrayAuxiliar = Util.makeArrayOfComparable(this.size() + array.length);
		ArrayList<T> auxiliar = new ArrayList<T>();
		//Para o caso da AVL ja ter elementos - A intencao e adicionar todos os elementos da AVL e do array na linkedList 
		if(!this.isEmpty()){ 
			while(!this.isEmpty()){
				auxiliar.add(root.getData());
				this.remove(root.getData());
			}
		}
		for(int i = 0; i < array.length; i++){
			auxiliar.add(array[i]);
		}
		int i = 0;
		while (!auxiliar.isEmpty()){
			arrayAuxiliar[i] = auxiliar.remove(i);
			i++;
		}
		
		Arrays.sort(arrayAuxiliar);
		this.fillWithoutRebalanceRecursive(array, 0, arrayAuxiliar.length - 1);
	}
	
	//insere o pivot/mediana para um intervalo dado
	public void fillWithoutRebalanceRecursive(T[] array, int left, int right){
		if(left < right){
			int indiceMediana = pegaIndiceDaMedianaNoIntervalo(array, left, right);
			this.insertSemRebalance(array[indiceMediana]);
			fillWithoutRebalanceRecursive(array, left, indiceMediana-1); //Acha a mediana e insere para a esquerda
			fillWithoutRebalanceRecursive(array, indiceMediana+1, right); //Acha a mediana e insere para a direita
		}else if(left == right){ //Se left == right é pq chegou no caso base e nao precisa chamar mais recursivamente. Ficou dividido em 1 parte
			this.insertSemRebalance(array[left]);
		}
	}
	
	//Metodo que recebe um array ordenado e um intervalo e pega a mediana nesse intervalo - Mediana = Divide os elementos ao meio
	public int pegaIndiceDaMedianaNoIntervalo(T[] array, int i, int j){
		int indiceMediana = Math.round((i+j)/2); //Arredonda sempre para cima
		return indiceMediana;
	}
	
	
	protected void rebalance(BSTNode<T> node) {
		int fatorDeBalanceamento = calculateBalance(node);
		if(Math.abs(fatorDeBalanceamento) > 1){
			BSTNode<T> pivot = node; //Para o caso de for root, temos que guarda-lo
			//Raiz pesa para a esquerda e o filho esquerdo pesa para a esquerda ou esta balanceado = Rotacao para a direita na raiz (LEFT LEFT)
			if(fatorDeBalanceamento > 0 && calculateBalance((BSTNode<T>) node.getLeft()) >= 0){
				pivot = Util.rightRotation(node);
				this.LLcounter++;
				
			//Raiz pesa para a direita e o filho direito pesa para a direita ou esta balanceado = Rotaciona a raiz para a esquerda (RIGHT RIGHT)
			}else if(fatorDeBalanceamento < 0 && calculateBalance((BSTNode<T>) node.getRight()) <= 0){
				pivot = Util.leftRotation(node);
				this.RRcounter++;
			
			//Raiz pesa para a esquerda e filho a esquerda pesa para a direita	(Left Right)	
			}else if(fatorDeBalanceamento > 0 && calculateBalance((BSTNode<T>) node.getLeft()) < 0){
				Util.leftRotation((BSTNode<T>) node.getLeft());
				pivot = Util.rightRotation(node);
				this.LRcounter++;
				
				
			//Raiz pesa para a direita e filho a direita pesa para a esquerda (Right Left)	
			}else if(fatorDeBalanceamento < 0 && calculateBalance((BSTNode<T>) node.getRight()) > 0){
				Util.rightRotation((BSTNode<T>) node.getRight());
				pivot = Util.leftRotation(node);
				this.RLcounter++;
			}
			//Se movemos a raiz da arvore, devemos atualizar o apontador root.
			if(node.equals(this.getRoot())){
				this.root = pivot;
			}
			
		}
	}
	
	//Metodo auxiliar No caso de construir uma AVL com array. Nesse caso, nao podemos realizar o rebalance no insert de AVL. Ela se balanceará naturalmente, mesmo que em um momento, esteja desbalanceada
	//Entao esse insert nao precisa chamar rebalance dentro dele *Só ira ser usado no fillWithoutRebalance. Esse insert é o insert normal de BST.
	public void insertSemRebalance(T element) {
		this.insertSemRebalance(this.root, element);
	}
	
	public void insertSemRebalance(BSTNode<T> node, T element){
		if (node.isEmpty()){ //Se o no é sentinela
			node.setData(element);
			node.setLeft(new BSTNode.Builder().build()); //Left e Right sao definidos como nos sentinela (NIL). Para poder ser possivel setar o parent
			node.setRight(new BSTNode.Builder().build());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		}else{
			if(element.compareTo(node.getData()) > 0){
				this.insertSemRebalance((BSTNode<T>) node.getRight(), element);
			}else if (element.compareTo(node.getData()) < 0){
				this.insertSemRebalance((BSTNode<T>) node.getLeft(), element);
			}
		}
	}
	
	


}
