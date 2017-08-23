package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.
  
	// AUXILIARY        
	protected int calculateBalance(BSTNode<T> node) { //(balance) > 0: pesa para a esquerda // == 0: Nao pesa pra nenhum // < 0: Pesa para a direita
		if(!node.isEmpty() || node != null){
			return height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
		}
		return 0;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int fatorDeBalanceamento = calculateBalance(node);
		if(Math.abs(fatorDeBalanceamento) > 1){
			BSTNode<T> pivot = node; //Para o caso de for root, temos que guarda-lo
			//Raiz pesa para a esquerda e o filho esquerdo pesa para a esquerda ou esta balanceado = Rotacao para a direita na raiz (LEFT LEFT)
			if(fatorDeBalanceamento > 0 && calculateBalance((BSTNode<T>) node.getLeft()) >= 0){
				pivot = Util.rightRotation(node);
				
			//Raiz pesa para a direita e o filho direito pesa para a direita ou esta balanceado = Rotaciona a raiz para a esquerda (RIGHT RIGHT)
			}else if(fatorDeBalanceamento < 0 && calculateBalance((BSTNode<T>) node.getRight()) <= 0){
				pivot = Util.leftRotation(node);
			
			//Raiz pesa para a esquerda e filho a esquerda pesa para a direita	(Left Right)	
			}else if(fatorDeBalanceamento > 0 && calculateBalance((BSTNode<T>) node.getLeft()) < 0){
				Util.leftRotation((BSTNode<T>) node.getLeft());
				pivot = Util.rightRotation(node);
				
				
			//Raiz pesa para a direita e filho a direita pesa para a esquerda (Right Left)	
			}else if(fatorDeBalanceamento < 0 && calculateBalance((BSTNode<T>) node.getRight()) > 0){
				Util.rightRotation((BSTNode<T>) node.getRight());
				pivot = Util.leftRotation(node);
			}
			//Se movemos a raiz da arvore, devemos atualizar o apontador root.
			if(node.equals(this.getRoot())){
				this.root = pivot;
			}
			
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while(parent != null){
			rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
	}
	
	//Metodo auxiliar
	public void insert(BSTNode<T> node, T element){
		if (node.isEmpty()){ //Se o no é sentinela
			node.setData(element);
			node.setLeft(new BSTNode.Builder().build()); //Left e Right sao definidos como nos sentinela (NIL). Para poder ser possivel setar o parent
			node.setRight(new BSTNode.Builder().build());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		}else{
			if(element.compareTo(node.getData()) > 0){
				this.insert((BSTNode<T>) node.getRight(), element);
			}else if (element.compareTo(node.getData()) < 0){
				this.insert((BSTNode<T>) node.getLeft(), element);
			}
			
			rebalance(node); //*Pilha de recursao
		}
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> node = this.search(element); //Nó a ser removido
		if(!node.isEmpty()){ //Nao é possivel remover um no sentinela, entao verifica se o resultado da busca é um no sentinela.
			
			if(node.isLeaf()){ //Se é folha (nao tem filhos): Remove o nó, transformando-o em node sentinela.
				node.setData(null);
				node.setLeft(null);
				node.setRight(null);
				rebalanceUp(node); //Vai subindo ate chegar no no desbalanceado
			}else if(!node.isLeaf() && temApenasUmFilho(node)){ //Caso o node a ser removido NÃO seja folha e tenha apenas um filho
				if(!node.equals(root)){
					if(this.isLeftChild(node)){ //Se o node a ser removido for filho a esquerda:
						if(!node.getLeft().isEmpty()){ //Se o node tem um filho a esquerda, junta com a esquerda do pai
							node.getParent().setLeft(node.getLeft());
							node.getLeft().setParent(node.getParent());
						}else{ //Se a esquerda do node a ser removido for nula e a direita for ocupada por um filho
							node.getParent().setLeft(node.getRight());
							node.getRight().setParent(node.getParent());
						}
					}else{ //Se o node a ser removido tem apenas um filho e o node a ser removido for filho a direita
						if(!node.getLeft().isEmpty()){ //Se o node a ser removido tiver filho a esquerda
							node.getParent().setRight(node.getLeft());
							node.getLeft().setParent(node.getParent());
						}else{ //Se o node a ser removido tiver filho a direita
							node.getParent().setRight(node.getRight());
							node.getRight().setParent(node.getParent());			
						}
					}
				}else{ //Para remover a raiz com um unico filho, a raiz passa a ser o filho que nao for vazio
					if (!node.getLeft().isEmpty()){ //Se a esquerda da raiz é ocupada
						this.root = (BSTNode<T>) node.getLeft();
						this.root.setParent(null);
					}else{//Se a esquerda é vazia/sentinela, entao a direita é ocupada
						this.root = (BSTNode<T>) node.getRight();
						this.root.setParent(null);
					}
				}

			rebalanceUp(node);
			}else if(!node.isLeaf() && !temApenasUmFilho(node)){ //Se o node nao é folha e tem dois filhos. Pega o sucessor, guarda em uma variavel. Remove ele, depois seta o no sendo o sucessor. Funciona usando predecessor
				BSTNode<T> sucessor = this.sucessor(node.getData());
				T data = sucessor.getData();
				this.remove(sucessor.getData());
				node.setData(data);
				
				
			}		
		}	
	}
	
	
}
