
package program1;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

class ThreadFlags {
  
        private boolean producerComplete; 

        public ThreadFlags() 
        {
            this.producerComplete = false; 
        }

        public boolean isProducerComplete() {
           return producerComplete;
        }
        
        //updates flag when the producer is done 
        public synchronized void setProducerComplete (boolean producerComplete) {
            this.producerComplete = producerComplete;
        }

    } 

//generating a random number to use in node creation 
class RandomNumber {
    public static int getRandomNumber(int min, int max) {
            return (int)(Math.random() * (max - min) + min);
    }
}


//node data structure 
class Node implements Comparable {
    
    private int processID;
    private int priority;
    private int timeSlice;
    private LocalDateTime nodeStart;
    private int prevID = 0; 
        
    public Node(int priority, int timeSlice) {
        this.processID = ++ prevID;
        this.priority = priority; 
        this.timeSlice = timeSlice;
    }
    
    public int getprocessID() {
        return processID;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) { 
        this.priority = priority;
    }
    
    public int getTime() {
        return timeSlice;
    }
    
    public void setTime(int timeSlice) { 
        this.timeSlice = timeSlice;
    }
    
    public LocalDateTime getStart() { 
        return nodeStart;
    }
    
    public void setStart(LocalDateTime nodeStart) {
        this.nodeStart = nodeStart;
    }
    
    public String toString() {
        //return processID + "\t" + priority + "\t" + run_time;
        //SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss a zzz"); 
        
        return String.format("Process: ID %d with priority %d (start %s)", this.getprocessID(), this.getPriority(), TimeFormat.formatDateTime(this.getStart()));
   
    }
    
    public void run() throws InterruptedException {
        this.setStart(CurrentTime.getCurrentTime());
        Thread.sleep(this.timeSlice);
    
    }
    
    public int compareTo(Object o) {
       //@Override
        if(o instanceof Node) { 
            Node other = (Node) o; 
            return Integer.compare(this.getPriority(), other.getPriority());
    }
        return - 1; 
    }
}
 
//gets the current date/time 
class CurrentTime {
    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}

class CurrentTimeFormatted {
    public static String getCurrentTimeFormatted() {
        return TimeFormat.formatDateTime(CurrentTime.getCurrentTime());
    }
}
        
        
//formats the date/time
class TimeFormat {

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern( "HH:mm:ss:nnnnnnnnnnnnnnn"));
    }
}