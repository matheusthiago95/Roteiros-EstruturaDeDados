package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	
	@Override
	public void insert(int key, T newValue, int height) {
		//Cada indice do array de update armazena um node e o nivel desse node que deve ser alterado
		SkipListNode[] nodesToBeUpdated = new SkipListNode[this.maxHeight]; //Array que guarda os ponteiros que devem provavelmente ser atualizados com adicao do novo node. Cada indice e a referencia de cada nivel q precida ser atualizada
		//Caminha por nivel enquanto achar um node com valor menor que o node a ser inserido - De cima para baixo
		SkipListNode<T> auxiliar = this.root; //Variavel auxiliar para percorrer a SkipList
		for(int i = this.maxHeight - 1; i >= 0; i--){
			while(auxiliar.getForward(i).getKey() < key){
				auxiliar = auxiliar.getForward(i); 
			}
			//Guarda os ponteiros a serem atualizados
			nodesToBeUpdated[i] = auxiliar; //Se o proximo no nao for menor, entao esse nivel desse node provavelmente devera ser atualizado, entao guardamos ele.		
		}
		//Ha a possibilidade de atualizar um node ja existente ou adicionar um novo node. Defini-se o auxiliar agora, como sendo auxiliar.forward[1]
		//Sendo entao o auxiliar agora o node a ser atualizado se ele ja existir ou o node APOS o node que foi adicionado
		auxiliar = auxiliar.getForward(0); //Faz isso para o caso de o no ja existir e ter de atualiza-lo
		if(auxiliar.getKey() == key){ //Caso de atualizacao
			auxiliar.setValue(newValue);
		}else{ //Caso em que de fato se vai adicionar um node
			SkipListNode<T> novoNode = new SkipListNode<T>(key, height, newValue);
			//Arrumando os apontadores que o node aponta e os que apontam para o node
			for(int i = height - 1; i >= 0; i--){
				novoNode.forward[i] = nodesToBeUpdated[i].getForward(i);
				nodesToBeUpdated[i].forward[i] = novoNode;	 	
			}
		}
	}
	
	//Obs*: O array de update guarda o node e o nivel onde o apontador do no vai sofrer alteração. O nivel onde o apontador do no vai sofrer alteracao é o indice do node no array de update

	@Override
	public void remove(int key) {
		SkipListNode<T>[] nodesToBeUpdated = new SkipListNode[this.maxHeight]; //array que guarda os nodes que POSSIVELMENTE terao apontadores forward modificados pela remoção. Alem de guardar o node, guarda o local que ele esta sendo alterado(indice do array)
		SkipListNode<T> auxiliar = this.root; //Variavel auxiliar que vai percorrer os nos e achar quem deve ser atualizado
		for(int i = this.maxHeight - 1; i >= 0; i--){ //Cada i significa o nivel que se esta percorrendo
			while(auxiliar.getForward(i).getKey() < key){ //Vai percorrendo o nivel ate achar um no maior ou igual (key) ao node a ser removido
				auxiliar = auxiliar.getForward(i);
			}
			nodesToBeUpdated[i] = auxiliar; //O indice onde o no é armazenado tambem é o indice onde ele sera atualizado o apontador.
		}
		//Se o node a ser removido existe //No final do for, o auxiliar vai ser o node anterior ao node a ser removido se o node a ser removido existir de fato
		if(auxiliar.getForward(0).getKey() == key){
			SkipListNode<T> nodeToBeRemoved = auxiliar.getForward(0); //Ja que o auxiliar é o anterior ao no a ser removido
			//Alterando os ponteiros que serao afetados pela remocao do node
			for(int i = 0; i < nodeToBeRemoved.forward.length; i++){ //Uma alternativa ao invés do for com break mostrado em sala
				nodesToBeUpdated[i].forward[i] = nodeToBeRemoved.forward[i];
			}
		}
	}

	@Override
	public int height() {
		SkipListNode<T> auxiliar = this.root.getForward(0);
		int altura = 0;
		while (auxiliar.getValue() != null){
			if(auxiliar.getForward().length > altura){
				altura = auxiliar.getForward().length;
			}
			auxiliar = auxiliar.getForward(0);
		}
		return altura;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> auxiliar = this.root;
		for(int i = this.maxHeight - 1; i >= 0; i--){ //for para descer os niveis
			while(auxiliar.getForward(i).getKey() < key){ //while para caminhar pelo nivel enquanto os nodes tiverem keys menores. Se tiver key maior, desce de nivel
				auxiliar = auxiliar.getForward(i);
			}
		}
		auxiliar = auxiliar.getForward(0); //Anda mais uma casa para a direita, para depois verificar se e mesmo o elemento. Aqui, ja estamos no ultimo nivel, ja descemos tudo no while
		if(auxiliar.getKey() == key){
			return auxiliar;
		}//Senao, o no nao existe
		return null;
	}

	@Override
	public int size() {
		int size = 0;
		SkipListNode<T> auxiliar = root.getForward(0); //Variavel auxiliar que vai percorrendo todos os nos contando +1 ate chegar no node NIL. Percorre no lvl 0, no menor level.
		while(!auxiliar.equals(this.NIL)){
			size++;
			auxiliar = auxiliar.getForward(0);
		}
		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode<T>[] array = new SkipListNode[this.size() + 2]; //array de retorno
		SkipListNode<T> auxiliar = this.root;
		//Variavel para o while
		int i = 0; //Variavel para add no array
		while(i <= (this.size() + 2) -1){ //-1 Por causa do 0, ou seja, so vai no maximo ate o indice 6 do array se this.size + 2 = 7.
			array[i] = auxiliar;
			auxiliar = auxiliar.getForward(0);
			i++;
		}
		return array;
	}
	
	public void imprimePorAlturaEmOrdemCrescente(){
		for(int i = this.maxHeight - 1; i >= 0; i--){ //For para percorrer por altura
			SkipListNode<T> auxiliar = this.root.getForward(i);
			while(!auxiliar.equals(NIL)){
				if(auxiliar.height() == i + 1){
					System.out.print(auxiliar.getValue() + " ");
				}
				auxiliar = auxiliar.getForward(i);
			}
		}
	}
	


}
