package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends
		RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data,
			RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}
	
	//Analisar cada nó como se fosse uma lista
	
	public void insert(T element){
		if(element != null){
			//Caso base
			if(this.isEmpty()){
				this.setData(element);
				this.setNext(new RecursiveDoubleLinkedListImpl<T>());  //É como se trocasse o nó sentinela por o novo no, e criasse um novo sentinela mais a direita
				((RecursiveDoubleLinkedListImpl<T>)this.getNext()).setPrevious(this);
				
				//Caso a lista (considerando tudo) esteja vazia(acabou de ser criada), o elemento devera ter na esquerda um nó sentinela, pois a double tem um no NIL nas duas pontas
				if(this.getPrevious() == null){
					this.setPrevious(new RecursiveDoubleLinkedListImpl<T>());
				}
			}else{
				this.next.insert(element);
			}
		}
	}

	public void remove(T element){
		if(element!=null){ //Elementos null serao ignorados
			
			if(!this.isEmpty()){
				
				if(element == this.getData()){
					//Se a lista tiver 1 elemento, remover o elemento é a mesma coisa que remover o primeiro
					if(this.getNext().isEmpty()){
						this.removeFirst();
					
					//Se tiver mais que 1 elemento:	
					}else{
						((RecursiveDoubleLinkedListImpl)this.getNext()).setPrevious(this.getPrevious());
						this.getPrevious().setNext(this.getNext());
					}
				}else{
					this.getNext().remove(element);
				}
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (element != null){ //Elementos null ignorados
			
			//Se a lista esta vazia, dá no mesmo que fazer o insert normal
			if(this.isEmpty()){
				this.setData(element);
				this.setPrevious(new RecursiveDoubleLinkedListImpl<T>());
				this.setNext(new RecursiveDoubleLinkedListImpl<T>());
				this.getPrevious().setNext(this);
				((RecursiveDoubleLinkedListImpl) this.getNext()).setPrevious(this);
			
			//Se a lista nao estiver vazia, insere no começo:	
			}else{
				//O antigo primeiro passa a ser um nó na "segunda posicao" (mudando os apontadores). Fazemos isso criando um no auxiliar, que sera o antigo primeiro:
				RecursiveDoubleLinkedListImpl auxiliar = new RecursiveDoubleLinkedListImpl<T>(this.data, this.getNext(), this);
				((RecursiveDoubleLinkedListImpl)auxiliar.getNext()).setPrevious(auxiliar);
				this.setNext(auxiliar);
				this.setData(element);
				
			}		
		}
	}

	@Override
	public void removeFirst() {
		//A lista nao pode estar vazia
		if(!this.isEmpty()){
			//Se a lista tem apenas um elemento: O no passa a ser um no sentinela.
			if(this.getNext().isEmpty()){
				this.setData(null);
				this.setNext(null);
				this.setPrevious(null);		
			
			}else{ //Se a lista nao tem apenas um no, removemos o primeiro elemento trazendo as infos do proximo para o elemento em questao(a ser removido)
				this.setData(this.getNext().getData());
				this.setNext(this.getNext().getNext());
				//Lembrar que o next ja foi setado/atualizado, entao nessa proxima linha so chamamos uma vez:
				((RecursiveDoubleLinkedListImpl)this.getNext()).setPrevious(this);
			}
		}
	}

	@Override
	public void removeLast() {
		if(!this.isEmpty()){
			//Se a lista tem apenas um elemento, remover o ultimo = remover o primeiro
			if(this.getNext().isEmpty()){
				this.removeFirst();
			}else{
				((RecursiveDoubleLinkedListImpl)this.getNext()).removeLast();
			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
