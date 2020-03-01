package program1;

import java.time.LocalDateTime;

/**
 * Consumes tasks and allows them to execute.
 * Requests nodes from the process queue, simulates execution for each process,
 * reports stats on the process and its task, etc.
 */
public class consumerThread implements Runnable {
    
	private static int lastId = 0;
	private final long wait_miliseconds = 33;

	private minHeap processQueue;
	private int ProcessID;

	private boolean isRunning;
	private String tabsPrepend;
	//private FlagCommunicator flags;

	private int totalConsumed;

	public consumerThread ( minHeap queue) {
		this.processQueue = queue;
		this.ProcessID = ++ lastId;
		//this.flags = fc;
		this.totalConsumed = 0;
		this.isRunning = false;

		StringBuilder sb = new StringBuilder();
		for ( int i = 0; i < this.ProcessID; i++ ) {
			sb.append( '\t' );
		}

		this.tabsPrepend = sb.toString();


	}

	/**
	 * @return the ProcessID of the consumer thread.
	 */
	public int getId () {
		return ProcessID;
	}

	/**
	 * @return the total number of nodes consumed by this consumer.
	 */
	public int getTotalConsumed () {
		return totalConsumed;
	}

	/**
	 * Requests a node from {@link minHeap} if there is one. If the queue is empty, waits.
	 *
	 * @return the requested node.
	 */
	public Process requestNode () {
		report( "is requesting a new node." );

		while ( this.processQueue.isEmpty() ) {

			report( "cannot find new node." );

			if ( flags.isProducerIsDone() ) {
				report( "thinks there won't be any more nodes to request." );
				this.isRunning = false;
				return null;
			} else {
				idle();
			}
		}

		try {
			return this.processQueue.removeHead();
		} 
                catch ( InterruptedException e ) {
			report( "was interrupted." );
			return null;
		}
	}

	private void report ( String message ) {
		System.out.println( String.format( "%sConsumer %d %s", this.tabsPrepend, this.getId(), message ) );
	}

	/**
	 * Waits for the given time in ms.
	 */
	private void idle () {
		try {
			report( "is idling..." );
			Thread.sleep(wait_miliseconds );
		} catch ( InterruptedException e ) {
			report( "was interrupted." );
		}
	}

	/**
	 * Consumes the processes in the queue while there are some to get.
	 */
	@Override
	public void run () {
		this.isRunning = true;
		while ( this.isRunning ) {
			try {
				Process node = this.requestNode();

				if ( node == null ) {
					continue;
				}

				node.run();

				LocalDateTime processFinished = LocalDateTime.now();

				String nodeStatistics = node.toString();

				report( String.format( "finished %s at %s", nodeStatistics, Utility.formatDateTime(finishedProcessingTime)));

				this.totalConsumed++;

			} catch ( InterruptedException ex ) 
                        {
				report( "was interrupted." );
			}
		}

		report( "is done." );
		report( String.format( "consumed %d nodes.", this.totalConsumed ) );

	}
}