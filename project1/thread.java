/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.lang.*;

/**
 *
 * @author Alexandra
 */
public class thread extends Thread {
    public void run()
    {
        System.out.println("Running method...");
    }
    
    public static void main(String[] args)
    {
        thread t1 = new thread();
        thread t2 = new thread();
        thread t3 = new thread();
        
        System.out.println("T1 Thread Priority: " + t1.getPriority()); 
        System.out.println("T2 Thread Priority: " + t2.getPriority());
        System.out.println("T3 Thread Priority: " + t3.getPriority());
        
        t1.setPriority(2);
        t2.setPriority(5);
        t3.setPriority(8);
        
        System.out.println("T1 Thread Priority: " + t1.getPriority());
        System.out.println("T2 Thread Priority: " + t2.getPriority());
        System.out.println("T3 Thread Priority: " + t3.getPriority());
        
        System.out.print(Thread.currentThread().getName()); 
        
        System.out.println("Main thread priority: " + thread.currentThread().getPriority());
        
        thread.currentThread().setPriority(10); 
        System.out.println("Main thread priority: " + thread.currentThread().getPriority());
    }
}