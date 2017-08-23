package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public QueueDoubleLinkedListImpl(int size) {
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		this.list.insert(element);
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if(!this.isEmpty()){
			T elementoRemoved = this.list.toArray()[0];
			this.list.removeLast();
			return elementoRemoved;
		}else{
			throw new QueueUnderflowException();
		}
	}

	@Override
	public T head() {
		if(!this.isEmpty()){
			return this.list.toArray()[0];	
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return this.list.size() == 0;
	}

	@Override
	public boolean isFull() {
		return this.size == this.list.size();
	}

}
