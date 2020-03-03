package program1;

//import com.sun.org.apache.bcel.internal.classfile.Utility;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import sun.security.pkcs11.wrapper.Functions;

//import java.time.LocalDateTime;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Consumes tasks and allows them to execute.
 * Requests nodes from the process queue, simulates execution for each process,
 * reports stats on the process and its task, etc.
 */
public class consumerThread implements Runnable {
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
	 * The id of the previous consumer thread.
	 */
	private static int lastId = 0;
	/**
	 * The time in milliseconds to wait while idling.
	 */
	private final long IDLE_WAIT_IN_MILLISECONDS = 33;
        /*
	 * The id of the consumer thread.
	 */
	private int id;
	/**
	 * True if the thread is to continue consuming.
	 */
	private boolean isRunning;

	/**
	 * A variable to improve time reporting information about the thread.
	 */
	private String tabsPrepend;

	/**
	 * The total number of nodes consumed by this consumer.
	 */
	private int totalConsumed;

	public consumerThread ( minHeap heap, ThreadFlags flags ) {
		this.minHeap = heap;
		this.id = ++ lastId;
    
	private static int lastId = 0;
	private final long wait_miliseconds = 33;

	private minHeap processQueue;
	private int ProcessID;

	private boolean isRunning;
	private String tabsPrepend;

	private int totalConsumed;

	public consumerThread ( minHeap queue) {
		this.processQueue = queue;
		this.ProcessID = ++ lastId;
		this.totalConsumed = 0;
		this.isRunning = false;

		StringBuilder sb = new StringBuilder();
		for ( int i = 0; i < this.id; i++ ) {

		for ( int i = 0; i < this.ProcessID; i++ ) {
			sb.append( '\t' );
		}

		this.tabsPrepend = sb.toString();


	}

	/**
	 * @return the id of the consumer thread.
	 */
	public int getId () {
		return id;

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
	 *
	 * @return the requested node.
	 */
	public Node requestNode () {
		report( "is requesting a new node." );

		while ( this.minHeap.isEmpty() ) {

			report( "cannot find new node." );

			if ( flags.producerComplete = true ) {
				report("thinks there won't be any more nodes to request.");

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
			return this.minHeap.removeHead();
		} catch ( InterruptedException e ) {
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
			Thread.sleep(wait_miliseconds);

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
                DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss");
                this.isRunning = true;
		while ( this.isRunning ) {
			try {
				Node nodeToProcess = this.requestNode();

				if ( nodeToProcess == null ) {
					continue;
				}
                                //TODO: rework Run() function for the node class
				nodeToProcess.run();

				LocalDateTime finishedProcessingTime = java.time.LocalDateTime.now();

				String nodeStatistics = nodeToProcess.toString();
                                //Date dateFormat = new Date();
                                SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss a zzz"); 
                                
				report( String.format( "finished %s at %s", nodeStatistics, sf.format( finishedProcessingTime ) ) );

				this.totalConsumed++;

			} catch ( InterruptedException ex ) {

    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
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

				report( String.format( "finished %s at %s", nodeStatistics, formatDate.format(processFinished)));

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