package program1;

/**
 * Thread that produces nodes for the consumer threads to consume and process.
 */
public class producerThread implements Runnable {
	
	public int wakeUpCount () {
		return wakeUpCount;
	}

	public Process createNode () {
		Node node = new Node( Utility.getRandomNumber( 1, 100 ), Utility.getRandomNumber( 10, 30 ) );
		nodeCount++;
		return node;
	}

	/**
	 * @return true if the number of nodes generated is >= to the max number of nodes the producer can make. False otherwise.
	 */
	public boolean isFinished () {
		return this.nodeCount >= this.MAX_NUM_OF_NODES_TO_PRODUCE;
	}

	/**
	 * Gets the remaining nodes to produce, based off of the max number of nodes and the current count of nodes generated.
	 *
	 * @return
	 */
	private int getRemainingNodesToProduce () {
		return this.MAX_NUM_OF_NODES_TO_PRODUCE - this.nodeCount;
	}

	/**
	 * Gets a random number of nodes to produce.
	 * <p>
	 * Handles clamping, thus it's in its own function.
	 *
	 * @return a clamped random number of nodes to produce.
	 */
	private int getRandomNumOfNodesToProduce () {
		int possibility = Utility.getRandomNumber( 8, this.MAX_NUM_OF_NODES_TO_PRODUCE / 4 );
		int remainingCount = getRemainingNodesToProduce();
		if ( possibility > remainingCount ) {
			possibility = remainingCount;
		}
		return possibility;
	}

	/**
	 * Waits for the given time in ms.
	 */
	private void idle () {
		try {
			Thread.sleep( IDLE_TIME_IN_MILLISECONDS );
		} catch ( InterruptedException e ) {
			System.out.println( "Producer was interrupted!" );
		}
	}

	/**
	 * Adds nodes to the {@link #processQueue} as it runs.
	 */
	@Override
	public void run () {

		while ( ! isFinished() ) {
			int nodesToProduce = getRandomNumOfNodesToProduce();
			for ( int i = 0; i < nodesToProduce; i++ ) {
				Node producedNode = createNode();
				this.processQueue.add( producedNode );
			}
			System.out.println( String.format( "Producer has produced ~%d new nodes.", nodesToProduce ) );
			System.out.println( "Producer is idling..." );
			idle();
		}

		System.out.println( "Producer has completed its tasks." );
		// Notify consumers that producer has finished.
		flags.setProducerIsDone( true );
	}
}