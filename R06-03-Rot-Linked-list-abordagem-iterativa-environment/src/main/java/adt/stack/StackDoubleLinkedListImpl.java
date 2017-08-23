package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class StackDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if(!(this.size == top.size())){
			this.top.insertFirst(element);	
		}else{
			throw new StackOverflowException();
		}

	}

	@Override
	public T pop() throws StackUnderflowException {
		if(!this.top.isEmpty()){
			T element = this.top.toArray()[0];
			this.top.removeFirst();
			return element;
		}else{
			throw new StackUnderflowException();
		}
	}

	@Override
	public T top() {
		if(!this.top.isEmpty()){
			T element = this.top.toArray()[0];
			return element;
		}else{
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		return this.top.size() == 0;
	}

	@Override
	public boolean isFull() {
		return this.top.size() == this.size;
	}

}
