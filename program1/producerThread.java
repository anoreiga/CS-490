package program1;

import java.util.*;
/**
 * Thread that produces nodes for the consumer threads to consume and process.
 */
public class producerThread implements Runnable {
	/**
	 * The maximum number of nodes to produce.
	 */
	private final int maxNodes = 75;
	/**
	 * The time to wait in idle.
	 */
	private final int idleTime = 66;
	/**
	 * The counter for the amount of nodes created.
	 */
	private int nodeCount;
	/**
	 * The counter for the amount of times producer thread has awoken.
	 */
	private int wakeUpCount;
	/**
	 * The queue for the processes to produce to.
	 */
	private minHeap minHeap;

	/**
	 * Communicates the flags between producer and consumer threads.
	 */
	private ThreadFlags flags;

	/**
	 * Creates a new producer thread with the given shared resource.
	 *
	 * @param processQueue The queue that holds all processes to add to.
	 * @param fc           The way for threads to communicate the flags they share.
	 */
	public producerThread ( minHeap minHeap, ThreadFlags tf ) {
		this.minHeap = minHeap;
		this.flags = tf;

		this.wakeUpCount = 0;
		this.nodeCount = 0;

	}

	/**
	 * @return The flags that threads use to communicate between each other.
	 */
	public ThreadFlags getFlags () {
		return flags;
	}

	/**
	 * Gets the amount of nodes created.
	 *
	 * @return the node count.
	 */
	public int getNodeCount () {
		return nodeCount;
	}

	/**
	 * Gets the amount of times producer has awoken.
	 *
	 * @return the wake up count.
	 */
	public int wakeUpCount () {
		return wakeUpCount;
	}

	/**
	 * Creates new instance of node.
	 * Generates random numbers to set priority, process id, and time slice
	 *
	 * @return the created node.
	 */
	public Node createNode () {
		Node node = new Node( Functions.getNuminRange( 1, 100 ), Functions.getNuminRange( 10, 30 ) );
		nodeCount++;
		return node;
	}

	/**
	 * @return true if the number of nodes generated is >= to the max number of nodes the producer can make. False otherwise.
	 */
	public boolean isFinished () {
		return this.nodeCount >= this.maxNodes;
	}

	/**
	 * Gets the remaining nodes to produce, based off of the max number of nodes and the current count of nodes generated.
	 *
	 * @return
	 */
	private int getRemainingNodesToProduce () {
		return this.maxNodes - this.nodeCount;
	}

	/**
	 * Gets a random number of nodes to produce.
	 * <p>
	 * Handles clamping, thus it's in its own function.
	 *
	 * @return a clamped random number of nodes to produce.
	 */
	private int getRandomNumOfNodesToProduce () {
		int possibility = Functions.getNuminRange(8, this.maxNodes / 4 );

        private final long wait_miliseconds = 33;
        private final int produce_nodes = 75;
        private int processCount;
        private int wakeCount;
	public int wakeUpCount () {
		return wakeCount;
	}

	public Process createNode () {
		Process process = new Process( Utility.getRandomNumber( 1, 100 ), Utility.getRandomNumber( 10, 30 ) );
		processCount++;
		return process;
	}

	public boolean isFinished () {
		return this.processCount >= this.produce_nodes;
	}

	private int getRemainingNodesToProduce () {
		return this.produce_nodes - this.processCount;
	}

	private int getRandomNumOfNodesToProduce () {
                //TODO: add random numbers
                
		int possibility = Utility.getRandomNumber(8, this.produce_nodes / 4 );
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
			Thread.sleep(idleTime );

   private void idle () {
		try {
			Thread.sleep( wait_miliseconds );
  
		} catch ( InterruptedException e ) {
			System.out.println( "Producer was interrupted!" );
		}
	}

        //add nodes to heap as it runs
	@Override
	public void run () {

		while ( ! isFinished() ) {
			int nodesToProduce = getRandomNumOfNodesToProduce();
			for ( int i = 0; i < nodesToProduce; i++ ) {

				Node producedNode = createNode();
				this.minHeap.add( producedNode );

				Process producedNode = createNode();
				//this.minHeap.add( producedNode );
			}
			System.out.println( String.format( "Producer has produced ~%d new nodes.", nodesToProduce ) );
			System.out.println( "Producer is idling..." );
			idle();
		}

		System.out.println( "Producer has completed its tasks." );
		// Notify consumers that producer has finished.

		flags.producerComplete(true);

		//flags.setProducerIsDone( true );

	}
}