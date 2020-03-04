package program1;

import sun.security.pkcs11.wrapper.Functions;
/**
 * Thread that produces nodes for the consumer threads to consume and process.
 */
public class producerThread implements Runnable {
     
        private final long idleWait = 33;
        private final int maxNodes = 75;
        private int nodeCount;
        private int wakeCount;
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
	public producerThread ( minHeap minHeap, ThreadFlags TF ) {
		this.minHeap = minHeap;
		this.flags = TF;

		this.wakeCount = 0;
		this.nodeCount = 0;
	}

	public ThreadFlags getFlags () {
		return flags;
	}


	public int getNodeCount () {
		return nodeCount;
	}

        //returns the wake up count
	public int getWake () {
		return wakeCount;
	}

        //creates an instance of a Node 
	public Node createNode () {
		Node node = new Node(RandomNumber.getRandomNumber(1, 100), RandomNumber.getRandomNumber(10, 30));
		nodeCount++;
		return node;
	}

	public boolean isFinished() {
		return this.nodeCount >= this.maxNodes;
	}

	private int getRemainingNodes() {
		return this.maxNodes - this.nodeCount;
	}
        
        //
	private int getRandomNodes () {
		int possibility = RandomNumber.getRandomNumber(8, this.maxNodes / 4 );
                int nodesRemaining = getRemainingNodes(); 
                if (possibility > nodesRemaining) {
                    possibility = nodesRemaining;
                }
                return possibility;
        }

	//makes the thread wait for the given time in miliseconds
	private void idle() 
        {
		try 
                {
			Thread.sleep(idleWait);
		} catch(InterruptedException e) 
                {
			System.out.println( "Error: Producer was interrupted." );
		}
	}

        //add nodes to heap as it runs
	@Override
	public void run() {
		while (!isFinished()) 
                {
                        //set the number of nodes to produce to random
			int nodeToAdd = getRandomNodes();
			for (int i = 0; i < nodeToAdd; i++) {
				Node producedNode = createNode();
				this.minHeap.add( producedNode );
			}
                        
			System.out.println(String.format("Producer has produced ~%d new nodes.", nodeToAdd));
			System.out.println("Producer is idling...");
			idle();
		}

		System.out.println("Producer has completed its tasks.");
		
                //set producer complete flag
		flags.setProducerComplete(true);
	}
}