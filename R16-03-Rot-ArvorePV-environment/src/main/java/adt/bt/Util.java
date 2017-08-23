package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) { //node é o root
		BSTNode<T> pivot = (BSTNode<T>) node.getRight(); //Pivot = Root.OS
		pivot.getLeft().setParent(node); //Root.OS = pivot.RS
		node.setRight(pivot.getLeft()); //Root.OS = Pivot.RS
		pivot.setLeft(node); //Pivot.RS = root
		pivot.setParent(node.getParent());
		node.setParent(pivot);
		//Olhando para o pai da nova raiz agora para ver se a raiz e um filho a esq ou a dir ou é a raiz da arvore total
		if(pivot.getParent() != null){ //Se o node nao é a raiz da arvore total, vê se ele é filho a direita ou a esquerda
			if(pivot.getParent().getLeft().getData() != null && pivot.getParent().getLeft().getData().equals(node.getData())){ //Primeira condicao para nao dar nullpointer
				pivot.getParent().setLeft(pivot);
			}else if(pivot.getParent().getRight().getData() != null && pivot.getParent().getRight().getData().equals(node.getData())){
				pivot.getParent().setRight(pivot);
			}
		}
		return pivot;
		
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) { //node é a antiga raiz
		BSTNode<T> pivot = (BSTNode<T>) node.getLeft();
		pivot.getRight().setParent(node);
		node.setLeft(pivot.getRight());
		pivot.setRight(node);
		pivot.setParent(node.getParent());
		node.setParent(pivot);
		
		if(pivot.getParent() != null){
			if(pivot.getParent().getLeft().getData() != null && pivot.getParent().getLeft().getData().equals(node.getData())){ //A primeira condicao é para nao dar nullpointer
				pivot.getParent().setLeft(pivot);
			}else if(pivot.getParent().getRight().getData() != null && pivot.getParent().getRight().getData().equals(node.getData())){
				pivot.getParent().setRight(pivot);
			}
		}
		
		return pivot;
		
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
