package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if(this.isFull()){
			throw new StackOverflowException();
		}else{
			this.top.insertFirst(element);
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if(this.isEmpty()){
			throw new StackUnderflowException();
		}else{
			T elemento = this.top.toArray()[0];
			this.top.removeFirst();
			return elemento;
		}
	}

	@Override
	public T top() {
		if(!this.isEmpty()){
			return this.top.toArray()[0];
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return this.top.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.top.size() == this.size;
	}

}
