package adt.rbtree;

import java.util.ArrayList;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T>
		implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeight((RBNode) this.root);
	}
	
	//Nao se conta o elemento passado no parametro mesmo ele sendo preto, mas, se conta os nodes sentinela
	private int blackHeight(RBNode node){
		RBNode esquerda = (RBNode) node.getLeft(); //Começa pela esquerda, ja que nao se conta o elemento passado no parametro.
		int resultado = 0;
		while(esquerda != null){
			if(esquerda.getColour() == Colour.BLACK){
				resultado = resultado + 1;
			}
			esquerda = (RBNode) esquerda.getLeft();
		}
		return resultado;
	}
	
	//Metodo usado para a blackHeight TOTAL da arvore
	private int blackHeight1(RBNode node){
		RBNode aux = node; //Nao conta a raiz
		int resultado = 0;
		while(aux != null){
			if(aux.getColour() == Colour.BLACK){
				resultado = resultado + 1;
			}
			aux = (RBNode) aux.getLeft();
		}
		return resultado;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour()
				&& verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		RBNode[] arrayDeElementos = this.rbPreOrder();
		for(int i = 0; i < arrayDeElementos.length; i++){
			if(arrayDeElementos[i].getColour() == Colour.RED){
				if(((RBNode) arrayDeElementos[i].getLeft()).getColour() == Colour.RED){
					return false;
				}if(((RBNode)arrayDeElementos[i].getRight()).getColour() == Colour.RED){
					return false;
				}
			}
		}
		return true;
		
	}
	
	

	
	

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		return this.blackHeight1((RBNode) this.root.getLeft()) == this.blackHeight1((RBNode) this.root.getRight());
	}
	
		

	@Override
	public void insert(T value) {
		this.insert((RBNode) this.root, value);
	}
	
	//Metodo auxiliar
	private void insert(RBNode<T> node, T element){
		if (node.isEmpty()){ //Se o no é sentinela
			node.setData(element);
			node.setLeft(new RBNode()); //Left e Right sao definidos como nos sentinela (NIL). Para poder ser possivel setar o parent
			node.setRight(new RBNode());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
			node.setColour(Colour.RED);
			fixUpCase1(node);
			
		}else{
			if(element.compareTo(node.getData()) > 0){
				this.insert(((RBNode<T>) node.getRight()), element);
			}else if (element.compareTo(node.getData()) < 0){
				this.insert(((RBNode<T>) node.getLeft()), element);
			}
		}
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		RBNode<T>[] arrayRetorno =  new RBNode[this.size()];
		ArrayList<RBNode> arrayL = new ArrayList<RBNode>();
		rbPreOrderAux((RBNode<T>) this.root, arrayL);
		for(int i = 0; i < arrayL.size(); i++){
			arrayRetorno[i] = arrayL.get(i);
		}
		return arrayRetorno;
	}
	
	public void rbPreOrderAux(RBNode<T> node, ArrayList arrayL){
		if(!node.isEmpty()){
			arrayL.add(node);
			rbPreOrderAux((RBNode) node.getLeft(),  arrayL);
			rbPreOrderAux((RBNode) node.getRight(), arrayL);
		}
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		//Todo no inserido tem cor vermelha, precisa conservar o invariante
		if(node.equals(this.root)){ //Não precisa verificar se a cor do no é vermelha, pois, se ele acabou de ser inserido, tera cor vermelha
			node.setColour(Colour.BLACK);
		}else{
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if( ((RBNode) node.getParent()).getColour() == Colour.BLACK){ //Adicionado preservando a invariante, OK!
			//OK
		}else{ //Mas, se o pai dele é vermelho, quebra invariante
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> tio = getUncle(node);
		RBNode<T> avo = (RBNode)(node.getParent().getParent());
		if(tio.getColour()==Colour.RED){
			((RBNode) node.getParent()).setColour(Colour.BLACK);
			tio.setColour(Colour.BLACK);
			avo.setColour(Colour.RED);
			fixUpCase1(avo);
		}else{ //Se o tio nao for PRETO e não vermelho
			fixUpCase4(node);
		}
		
	}
	
	//Metodo auxiliar responsavel por pegar o tio de um RBNode.
	private RBNode<T> getUncle(RBNode<T> node){ 
		RBNode uncle;
		//Se o pai é filho a esquerda, o tio é o filho a direita do avo e vice-versa
		RBNode<T> avo =(RBNode) node.getParent().getParent();
		RBNode<T> pai = (RBNode) node.getParent();
		if(isLeftChild(pai)){
			uncle = (RBNode) avo.getRight();
		}else{
			uncle = (RBNode) avo.getLeft();
		}
		return uncle;
	}
	
	//Metodo auxiliar que retorna se um no é filho a esquerda. - Usado para pegar o tio de um node
	public boolean isLeftChild(RBNode node){
		boolean retorno = false;
		if(!node.equals(root)){
			BSTNode<T> pai = (BSTNode<T>) node.getParent();
			retorno = pai.getLeft().equals(node);	
		}
		return retorno;
	}

	//O fixUpCase4 verifica se há joelho e se houver, deixar em linha. O next sera o node vermelho q esta tbm e filho de um vermelho
	//fixUpCase4 apenas verifica se ha joelho
	protected void fixUpCase4(RBNode<T> node) { //VERIFICA SE HÁ JOELHO
		RBNode<T> next = node;
		//SE HA JOELHO, DEIXA OS DOIS NOS VERMELHOS EM LINHA E CHAMA O FIXUP CASE5 PARA O NO MAIS ABAIXO, O NOVO NEXT QUE É UM FILHO A DIREITA OU A ESQUERDA
		if(isLeftChild(node) && !isLeftChild((RBNode) node.getParent())){
			Util.rightRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getRight();
		}else if(!isLeftChild(node) && isLeftChild((RBNode) node.getParent())){
			Util.leftRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getLeft();
		}
		fixUpCase5(next); //Node vermelho que é filho de um node vermelho e o seu tio é preto ao invés de ser vermelho (fixUpCase3)
	}

	protected void fixUpCase5(RBNode<T> node) { //O node recebido é o "next" do fixUpCase4
		((RBNode)node.getParent()).setColour(Colour.BLACK);
		((RBNode)(node.getParent().getParent())).setColour(Colour.RED);
		if(isLeftChild(node)){
			Util.rightRotation( (BSTNode) (node.getParent().getParent()));
		}else{
			Util.leftRotation((BSTNode) (node.getParent().getParent()));
		}
	}
	
	
	public int retornaQuantidadeDeNosPretos(){
		return retornaQuantidadeDeNosPretos((RBNode<T>) this.root);
	}
	
	//É como caminhamento em pre order.
	public int retornaQuantidadeDeNosPretos(RBNode<T> node){
		if(node.isEmpty()){ //Nao considera nodes sentinela
			return 0;
		}
		return contaNode(node) + retornaQuantidadeDeNosPretos((RBNode<T>) node.getLeft()) + retornaQuantidadeDeNosPretos((RBNode<T>) node.getRight());
	}
	
	public int contaNode(RBNode<T> node){
		if (!node.isEmpty() && node.getColour() == Colour.BLACK){
			return 1;
		}
		return 0;
	}
}