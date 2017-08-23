package adt.queue;

import adt.stack.Stack;
import adt.stack.StackImpl;
import adt.stack.StackOverflowException;
import adt.stack.StackUnderflowException;

public class QueueUsingStack<T> implements Queue<T> {

	private Stack<T> stack1;
	private Stack<T> stack2;
	
	//A 2a stack e auxiliar

	public QueueUsingStack(int size) {
		stack1 = new StackImpl<T>(size);
		stack2 = new StackImpl<T>(size);
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		try {
			this.stack1.push(element);
		} catch (StackOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	public T dequeue() throws QueueUnderflowException {
		T resultado = null;
		while(!this.stack1.isEmpty()){
			try {
				T element = this.stack1.pop();
				this.stack2.push(element);
			} catch (StackUnderflowException | StackOverflowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		try {
			resultado = this.stack2.pop();
		} catch (StackUnderflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//passa de volta
		while(!this.stack2.isEmpty()){
			try {
				T element = this.stack2.pop();
				this.stack1.push(element);
			} catch (StackUnderflowException | StackOverflowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultado;
	}

	@Override
	public T head() {
		while(!this.stack1.isEmpty()){
			try {
				T element = this.stack1.pop();
				this.stack2.push(element);
			} catch (StackUnderflowException | StackOverflowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		T resultado = this.stack2.top();
		
		//passa de volta
		
		while(!this.stack2.isEmpty()){
			try {
				T elemento = this.stack2.pop();
				this.stack1.push(elemento);
			} catch (StackUnderflowException | StackOverflowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return resultado;
	}

	@Override
	public boolean isEmpty() {
		return this.stack1.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.stack1.isFull();
	}

}
