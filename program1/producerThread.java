package program1;

import java.util.*;
/**
 * Thread that produces nodes for the consumer threads to consume and process.
 */
public class producerThread implements Runnable {
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
				Process producedNode = createNode();
				//this.minHeap.add( producedNode );
			}
			System.out.println( String.format( "Producer has produced ~%d new nodes.", nodesToProduce ) );
			System.out.println( "Producer is idling..." );
			idle();
		}

		System.out.println( "Producer has completed its tasks." );
		// Notify consumers that producer has finished.
		//flags.setProducerIsDone( true );
	}
}