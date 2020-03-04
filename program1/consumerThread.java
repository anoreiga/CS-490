package program1;

//import com.sun.org.apache.bcel.internal.classfile.Utility;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import sun.security.pkcs11.wrapper.Functions;
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
	private final int idleWait = 66;
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
	 * The consumerID of the previous consumer thread.
	 */
	private static int lastId = 0;
	
        //consumer thread's ID
	private int consumerID;
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

	public consumerThread (minHeap heap, ThreadFlags TF ) {

		this.minHeap = heap;
		this.consumerID = ++ lastId;
                this.flags = TF;
		this.totalConsumed = 0;
		this.isRunning = false;

		StringBuilder sb = new StringBuilder();
		for ( int i = 0; i < this.consumerID; i++ ) {
			sb.append( '\t' );
		}

		this.tabsPrepend = sb.toString();
	}

	/**
	 * @return the id of the consumer thread.
	 */
	/**
	 * @return the id of the consumer thread.
	 */

    /**
     *
     * @return the consumerID of the consumer thread.
     */
    public int getConsumerID () {
		return consumerID;
	}

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

			if (flags.isProducerComplete()) {
				report("thinks there won't be any more nodes to request.");
                                this.isRunning = false; 
                                return null; 
                        }
                        else
                        {
                            idle();
                        }
                }
		try {
			return this.minHeap.removeHead();
		} 
                catch ( InterruptedException e ) 
                {
			report("was interrupted.");
			return null;
		}
	}

	private void report ( String message ) {
		System.out.println( String.format( "%sConsumer %d %s", this.tabsPrepend, this.getConsumerID(), message ) );
	}

	/**
	 * Waits for the given time in ms.
	 */
	private void idle () {
		try {
                        report( "is idling..." );
			Thread.sleep(idleWait);

			Thread.sleep(idleWait );
		} catch ( InterruptedException e ) {
			report( "was interrupted." );
		}
	}

	/**
	 * Consumes the processes in the queue while there are some to get.
	 */
	@Override
	public void run () {
                //DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss");
                this.isRunning = true;
		while (this.isRunning) {
			try {
				Node nodeToProcess = this.requestNode();

				if (nodeToProcess == null) {
					continue;
				}
				nodeToProcess.run();

				LocalDateTime finishedProcessingTime = CurrentTime.getCurrentTime();

				String nodeStatistics = nodeToProcess.toString();
                                //Date dateFormat = new Date();
                                //SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss a zzz"); 
                                
				report(String.format("finished %s at %s", nodeStatistics, TimeFormat.formatDateTime(finishedProcessingTime)));
				this.totalConsumed++;

			} 
                        catch ( InterruptedException ex ) 
                        {
                            report("process was interrupted");
                        }
                }
               
                report("is done."); 
                report(String.format("consumed %d nodes.", this.totalConsumed));
        }
}
