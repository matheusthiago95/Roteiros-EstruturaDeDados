package adt.hashtable.closed;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import util.Util;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

   /**
    * A hash table with closed address works with a hash function with closed
    * address. Such a function can follow one of these methods: DIVISION or
    * MULTIPLICATION. In the DIVISION method, it is useful to change the size
    * of the table to an integer that is prime. This can be achieved by
    * producing such a prime number that is bigger and close to the desired
    * size.
    * 
    * For doing that, you have auxiliary methods: Util.isPrime and
    * getPrimeAbove as documented bellow.
    * 
    * The length of the internal table must be the immediate prime number
    * greater than the given size. For example, if size=10 then the length must
    * be 11. If size=20, the length must be 23. You must implement this idea in
    * the auxiliary method getPrimeAbove(int size) and use it.
    * 
    * @param desiredSize
    * @param method
    */

   @SuppressWarnings({ "rawtypes", "unchecked" })
   public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
      int realSize = desiredSize;

      if (method == HashFunctionClosedAddressMethod.DIVISION) {
         realSize = this.getPrimeAbove(desiredSize); // real size must the
         // the immediate prime
         // above
      }
      initiateInternalTable(realSize);
      HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
      this.hashFunction = function;
   }

   // AUXILIARY
   /**
    * It returns the prime number that is closest (and greater) to the given
    * number. You can use the method Util.isPrime to check if a number is
    * prime.
    */
   int getPrimeAbove(int number) {
      int i = number;
      while (!Util.isPrime(i)){
    	  i++;
      }
      return i;
   }

   @Override
   public void insert(T element) {
	   if(element != null){
		   int indice = ((HashFunctionClosedAddress<T>)this.hashFunction).hash(element);
		   //A table é um array do tipo Object, mas queremos acessar as linkedLists dela;
		  if(this.table[indice] == null){
			  this.table[indice] = new LinkedList<T>();
			  ((LinkedList<T>) this.table[indice]).add(element);
			  this.elements++;
		  }else{
			  ((LinkedList<T>) this.table[indice]).add(element);
			  this.elements++;
			  this.COLLISIONS++;
		  }
	   }
   }
   
 
   //Retorna linkedList de determinada posicao (indice) da tabela. (Inicialmente a tabela é de object)
   public LinkedList<T> retornaLinkedList(int indice){
	   LinkedList<T> listaDaPosicao = (LinkedList<T>)(this.table[indice]);
	   return listaDaPosicao;
   }

   @Override
   public void remove(T element) {
	   if(element != null){
		   int indice = ((HashFunctionClosedAddress<T>)this.hashFunction).hash(element); 
		   LinkedList<T> listaDaPosicao = retornaLinkedList(indice); 
		   if(listaDaPosicao != null){
			   boolean removeu = listaDaPosicao.remove(element);
			   if(removeu = true){
				   elements--;
			   }	   
		   }
	   }
   }

   @Override
   public T search(T element) {
      T retorno = null;
      int indice = ((HashFunctionClosedAddress<T>)this.hashFunction).hash(element);
      LinkedList<T> listaDaPosicao = retornaLinkedList(indice);
      if(listaDaPosicao != null && listaDaPosicao.contains(element)){
    	  retorno = element;
      }
      return retorno;
      
   }

   @Override
   public int indexOf(T element) {
      int retorno = -1;
      int indice = ((HashFunctionClosedAddress<T>)this.hashFunction).hash(element);
      LinkedList<T> listaDaPosicao = retornaLinkedList(indice);
      if(listaDaPosicao != null && listaDaPosicao.contains(element)){
    	  retorno = indice;
    	 
      }
      return retorno;
   }

}
