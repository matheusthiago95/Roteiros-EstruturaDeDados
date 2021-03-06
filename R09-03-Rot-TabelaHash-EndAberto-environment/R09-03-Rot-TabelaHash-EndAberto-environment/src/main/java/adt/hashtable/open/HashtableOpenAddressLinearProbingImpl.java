package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends
		AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size,
			HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		int i = 0;;
		while(i < this.table.length){
			int posicao = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, i);
			//Se for null ou deleted, adiciona
			if(this.table[posicao] == null || this.table[posicao] instanceof DELETED){
				this.table[posicao] = element;
				this.elements++;
				break;
			}else{
				i++;
				this.COLLISIONS++;
			}
		}
	}

	@Override
	public void remove(T element) {
		if (element != null){
			int i = 0;
			while (i < this.table.length){
				int posicao = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, i);
				if(this.table[posicao] == null){
					break;
				}else{
					//se achou
					if (this.table[posicao].equals(element)){
						this.table[posicao] = this.deletedElement;
						this.elements--;
						break;
					//Se nao achou
					}else{
						i++;
					}
				}
			}			
		}
		
	}

	@Override
	public T search(T element) {
		T retorno = null;
		int i = 0;
	
		while (i < this.table.length){
			int posicao = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, i);
			
			if(this.table[posicao] == null){
				break;
			
			}else{
				//Se achou o elemento
				if(this.table[posicao].equals(element)){
					retorno = element;
					break;
				//Se ainda nao achou, mas tem elemento para procurar			
				}else{
					i++;
				}
			}
		}
		
		return retorno;
	}

	@Override
	public int indexOf(T element) {
		int retorno = -1;
		int i = 0;
		while ( i < this.table.length){
			int posicao = ((HashFunctionLinearProbing<T>) this.hashFunction).hash(element, i);
			if(this.table[posicao].equals(element)){
				retorno = posicao;
				break;	
			}else{
				i++;
			}
		}
		return retorno;
	}

}
