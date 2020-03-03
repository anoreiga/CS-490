package program1;

import java.util.ArrayList;
//********************
//Handles sorting/processesing of nodes
//********************

public class minHeap {

	private final Object lock;

	private ArrayList< Node > processes;

	public minHeap (int initCapacity) {
		this.processes = new ArrayList<>(initCapacity);
		this.lock = new Object();
	}

 
	private ArrayList< Process > process;

	public minHeap (int initCapacity) {
		this.process = new ArrayList<>(initCapacity);
		this.lock = new Object();
	}


	private void heapify ( int n, int indexOfNode ) {
		int indexOfSmallestNode = indexOfNode;
		int indexOfLeftChild = 2 * indexOfNode + 1;
		int indexOfRightChild = 2 * indexOfNode + 2;
                
		// If left child is smaller than root
		if ( indexOfLeftChild < n && this.processes.get(indexOfLeftChild).compareTo(this.processes.get(indexOfSmallestNode)) > 0) {
		if ( indexOfLeftChild < n && this.process.get(indexOfLeftChild).compareTo(this.process.get(indexOfSmallestNode)) > 0) {
			indexOfSmallestNode = indexOfLeftChild;
		}

		// If right child is smaller than the smallest so far
		if ( indexOfRightChild < n &&
				     this.processes.get(indexOfRightChild).compareTo(this.processes.get(indexOfSmallestNode)) > 0) {
				     this.process.get(indexOfRightChild).compareTo(this.process.get(indexOfSmallestNode)) > 0) {
			indexOfSmallestNode = indexOfRightChild;
		}

		// If the index of the smallest node is not the root index
		if ( indexOfSmallestNode != indexOfNode ) {
			// Swap them.
			Node swap = this.processes.get( indexOfNode );
			this.processes.set(indexOfNode, this.processes.get(indexOfSmallestNode) );
			this.processes.set( indexOfSmallestNode, swap );

			Process swap = this.process.get( indexOfNode );
			this.process.set(indexOfNode, this.process.get(indexOfSmallestNode) );
			this.process.set( indexOfSmallestNode, swap );

			// Recursively heapify the affected sub-tree
			heapify(n, indexOfSmallestNode);
		}
	}

	public boolean add (Node newProcess) {
		boolean output = false;
		synchronized ( this.lock ) {
			output = this.processes.add( newProcess );

	public boolean add (Process newProcess) {
		boolean output = false;
		synchronized ( this.lock ) {
			output = this.process.add( newProcess );
			// Heapify at the root.
			this.sort();
		}
		return output;
	}

	public void clear () {
		synchronized ( this.lock ) {
			this.processes.clear();
			this.process.clear();
		}
	}

	public void sort () {
		synchronized ( this.lock ) {
			// Build processes (rearrange array)
			for ( int i = this.processes.size() / 2 - 1; i >= 0; i-- )
				heapify(this.processes.size(), i );

			// One by one extract an element from processes
			for ( int i = this.processes.size() - 1; i >= 0; i-- ) {
				// Move current root to end
				Node temp = this.processes.get( 0 );
				this.processes.set(0, this.processes.get( i ) );
				this.processes.set( i, temp );

				// call max heapify on the reduced processes

			// Build heap (rearrange array)
			for ( int i = this.process.size() / 2 - 1; i >= 0; i-- )
				heapify(this.process.size(), i );

			// One by one extract an element from heap
			for ( int i = this.process.size() - 1; i >= 0; i-- ) {
				// Move current root to end
				Process temp = this.process.get( 0 );
				this.process.set(0, this.process.get( i ) );
				this.process.set( i, temp );

				// call max heapify on the reduced heap
				heapify( i, 0 );
			}
		}
	}

	public Node removeHead () throws InterruptedException {
		synchronized ( this.lock ) {
			Node head = this.processes.remove(0);
	public Process removeHead () throws InterruptedException {
		synchronized ( this.lock ) {
			Process head = this.process.remove(0);
			this.sort();
			return head;
		}
	}

	public int size () {
		return this.processes.size();
		return this.process.size();
	}

	public boolean isEmpty () {
		return this.size() == 0;
	}
}
