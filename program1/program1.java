package project1;


import java.io.*;
import java.util.*;
import java.util.Scanner;

public class project1 {

    public static void main(String args[]) throws Exception {
        FileWriter out = new FileWriter("Schedule.txt");
        
        out.write("\t" + "Schedule.txt Output File" + System.getProperty("line.seperator"));
        out.flush();
        
        out.write("Process ID" + "" + "End Time" + "" + "Priority" + "" + "Run Time" + "" + System.getProperty("line.seperator"));
        out.flush();
        
        PriorityQueue EventHeap = new PriorityQueue(); 
        PriorityQueue ProcessHeap = new PriorityQueue();
        
        int global_time = 0; 
        
        Scanner in = new Scanner(new File("processes.txt"));
        
        boolean complete = false; 
        
        while(in.hasNext()) {
            String processID = in.next();
            
            int priority = in.nextInt();
            int time_run = in.nextInt();
            
            process process = new process(processID, priority, time_run);
            complete = false;
            
            processEvent processEvent = new processEvent(complete, global_time, process);
            
            EventHeap.add(processEvent);
            
        }
        
        boolean busy = false; 
        
        while (EventHeap.size() > 0) {
            processEvent event = (processEvent) EventHeap.remove();
            
            global_time = event.getTime();
            
            if(event.getComplete() == false) {
                ProcessHeap.add(event.getProcess()); 
                
                if (busy == false); {
                    process process = (process) ProcessHeap.remove();
                    busy = true; 
                    global_time = global_time + process.getRunTime();
                    complete = true; 
                    processEvent processEvent = new processEvent(complete, global_time, process);
            }
            } else {
                int total_time = global_time + event.getProcess().getRunTime();
            
        out.write(event.getProcess().getprocessID()+"\t"+global_time+"\t"+total_time+"\t"+event.getProcess().getRunTime()+System.getProperty("line.seperator"));
            out.flush();
            busy = false; 
            
            if(ProcessHeap.size() > 0) {
                process process = (process) ProcessHeap.remove();
                busy = true;
                global_time = global_time + process.getRunTime();
                complete = true; 
                processEvent processEvent = new processEvent(complete, global_time, process);
            }
           }
        }
    }

static class process implements Comparable {
    private String processID;
    private int priority;
    private int run_time;
    
    public process(String processID, int priority, int runTime) {
        this.processID = processID;
        this.priority = priority; 
        this.run_time = run_time;
    }
    
    public String getprocessID() {
        return processID;
    }
    
    public int getTime() {
        return run_time;
    }
    
    public int getRunTime() {
        return run_time;
    }
    
    public String toString() {
        return processID + "\t" + priority + "\t" + run_time;
    }
    
    public int compareTo(Object o) {
        return this.priority - ((process)o).priority;
    }
}

    static class processEvent implements Comparable {
        boolean complete = false; 
        int time; 
        process process; 

        public processEvent(boolean complete, int time, process process) {
            this.complete = true; 
            this.time = time; 
            this.process = process; 
        }

        public int getTime() {
            return time; 
        }

        public process getProcess() {
            return this.process;
        }

        public boolean getComplete() {
            return this.complete;
        }

        public int compareTo(Object o) {
            return this.time - ((processEvent)o).time;
        }
    }
}