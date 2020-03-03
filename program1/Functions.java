/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program1;

import java.text.SimpleDateFormat;
import java.time.*;

class ThreadFlags {
        boolean producerComplete = false; 

        public ThreadFlags(boolean producerComplete) 
        {
            this.producerComplete = false; 
        }

        public boolean producerComplete(boolean b) {
            return this.producerComplete;
        }
        
        //updates flag when the producer is done 
        public synchronized void setProducerComplete (boolean producerComplete) {
            this.producerComplete = producerComplete;
        }

    } 

//generating a random number to use in node creation 
class RandomNumber {
    private static int getNuminRange(int min, int max) {
            return (int)(Math.random() * ((max - min) + 1)) + min;
    }
}


//node data structure 
class Node implements Comparable {
    
    private int processID;
    private int priority;
    private int timeSlice;
    private LocalDateTime nodeStart;
    private int prevID = 0; 
        
    public Node(String processID, int priority, int runTime) {
        this.processID = ++ prevID;
        this.priority = priority; 
        this.timeSlice = timeSlice;
       
    }
    
    public int getprocessID() {
        return processID;
    }
    
    public int getPriority () {
        return priority;
    }
    
    public void setPriority(int priority) { 
        this.priority = priority;
    }
    
    public int getTime() {
        return timeSlice;
    }
    
    public void setTime() { 
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
        SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss a zzz"); 

        
        return String.format("Process: ID %d with priority %d (start %s)", this.getprocessID(), this.getPriority(), sf.format(this.getStart()));
   
    }
    
    public void run() throws InterruptedException {
        this.setStart(java.time.LocalDateTime.now());
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
    }
    }
}
