package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;
	
	public DoubleLinkedListImpl(){
		this.head = new DoubleLinkedListNode<T>();
		this.last = (DoubleLinkedListNode<T>) head;
	}
	
	
	public void insert(T element){
		//Caso a lista esteja vazia
		if(this.isEmpty()){
			DoubleLinkedListNode<T> novoNo = new DoubleLinkedListNode<T>(element, last, last);
			this.setHead(novoNo);
			this.setLast(novoNo);
			
		}else{//Caso a lista nao esteja vazia, insere no final
			DoubleLinkedListNode<T> novoNo = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) last.getNext(), last);
			last.setNext(novoNo);
			this.setLast(novoNo);
		}
	
	}
	
	@Override
	public void insertFirst(T element) {
		//Caso a lista seja vazia
		if(this.isEmpty()){
			DoubleLinkedListNode<T> novoNo = new DoubleLinkedListNode<T>(element, last, last);
			this.setHead(novoNo);
			this.setLast(novoNo);
	
		}else{ //Caso nao esteja vazia
			DoubleLinkedListNode<T> novoNo = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode) head, ((DoubleLinkedListNode)head).getPrevious());
			((DoubleLinkedListNode)head).setPrevious(novoNo);
			this.head = novoNo;
		}
	}

	@Override
	public void removeFirst() {
		//Se a linkedList nao for vazia
		if(!this.isEmpty()){
			//Elemento posterior ao head, logo apos o head
			DoubleLinkedListNode aposHead = (DoubleLinkedListNode) head.getNext();
			//Elemento que vem antes do head.
			DoubleLinkedListNode antesHead = ((DoubleLinkedListNode) head).getPrevious();
			aposHead.setPrevious(antesHead);
			this.setHead(aposHead);
		}
	}

	@Override
	public void removeLast() {
		//Se a lista nao estiver vazia
		if(!this.isEmpty()){
			//Elemento anterior ao last.
			DoubleLinkedListNode<T> anteriorLast = this.getLast().getPrevious();
			//Elemento posterior ao last
			DoubleLinkedListNode<T> posteriorLast = (DoubleLinkedListNode<T>) this.getLast().getNext();
			
			anteriorLast.setNext(posteriorLast);
			posteriorLast.setPrevious(anteriorLast);
			this.setLast(anteriorLast);
		}
	}
	

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}
	
	
	//Só mexe nos apontadores(head e last) se tiver que remover o primeiro ou o ultimo elemento, usa entao o removeFirst  e remove last que faz isso.
	public void remove(T element){
		DoubleLinkedListNode<T> auxiliar = (DoubleLinkedListNode<T>) this.head;
		//Se o elemento a ser removido for o primeiro (head)
		if(auxiliar.getData() == element){
			this.removeFirst();	
		
			//Se o for ter que remover o ultimo
		}if(this.last.getData() == element){
			this.removeLast();
		//Se nao tem que remover nem o primeiro nem o ultimo, nao precisa mexer nos apontadores head e last
		}else{
			while(!auxiliar.isNIL() && auxiliar.getData() != element){
				auxiliar = (DoubleLinkedListNode) (auxiliar.getNext());
			}
			
			//Se o saiu do laco pq é nil, é pq nao achou, se ao contrário, é pq achou o elemento a ser removido, entra no if:		
			if(!auxiliar.isNIL()){
				DoubleLinkedListNode<T> aposAuxiliar = (DoubleLinkedListNode<T>) auxiliar.getNext();
				DoubleLinkedListNode<T> antesAuxiliar = auxiliar.getPrevious();
				aposAuxiliar.setPrevious(antesAuxiliar);
				antesAuxiliar.setNext(aposAuxiliar);
			}
		}
		
	}

}
