package program1;

import java.util.ArrayList;
//********************
//Handles sorting/processesing of nodes
//********************

public class minHeap {

	private final Object lock;

	private ArrayList< Node > nodes;

	public minHeap (int initCapacity) {
		this.nodes = new ArrayList<>(initCapacity);
		this.lock = new Object();
	}


	private void heapify ( int n, int indexOfNode ) {
		int indexOfSmallestNode = indexOfNode;
		int indexOfLeftChild = 2 * indexOfNode + 1;
		int indexOfRightChild = 2 * indexOfNode + 2;
                
		// If left child is smaller than root
		if ( indexOfLeftChild < n && this.nodes.get(indexOfLeftChild).compareTo(this.nodes.get(indexOfSmallestNode)) > 0) {			
                    indexOfSmallestNode = indexOfLeftChild;
                }

		// If right child is smaller than the smallest so far
		if ( indexOfRightChild < n && this.nodes.get(indexOfRightChild).compareTo(this.nodes.get(indexOfSmallestNode)) > 0) {
			indexOfSmallestNode = indexOfRightChild;
		}

		// If the index of the smallest node is not the root index
		if ( indexOfSmallestNode != indexOfNode ) {
			// Swap them.
			Node swap = this.nodes.get( indexOfNode );
			this.nodes.set(indexOfNode, this.nodes.get(indexOfSmallestNode));
			this.nodes.set( indexOfSmallestNode, swap );

			// Recursively heapify the affected sub-tree
			heapify(n, indexOfSmallestNode);
		}
	}

	public boolean add (Node newProcess) {
		boolean output = false;
		synchronized ( this.lock ) {
			output = this.nodes.add( newProcess );

			// Heapify at the root.
			this.sort();
		}
		return output;
	}

	public void clear () {
		synchronized ( this.lock ) {
			this.nodes.clear();
			this.nodes.clear();
		}
	}

	public void sort () {
		synchronized ( this.lock ) {
			// Build processes (rearrange array)
			for ( int i = this.nodes.size() / 2 - 1; i >= 0; i-- )
				heapify(this.nodes.size(), i );

			// One by one extract an element from processes
			for ( int i = this.nodes.size() - 1; i >= 0; i-- ) {
				// Move current root to end
				Node temp = this.nodes.get( 0 );
				this.nodes.set(0, this.nodes.get( i ) );
				this.nodes.set( i, temp );

				// call max heapify on the reduced processes
				heapify( i, 0 );
			}
		}
	}

	public Node removeHead () throws InterruptedException {
		synchronized ( this.lock ) {
			Node head = this.nodes.remove(0);
	
			this.sort();
			return head;
		}
	}
	public int size () {
		return this.nodes.size();
	}
        
	public boolean isEmpty () {
		return this.size() == 0;
	}
}
