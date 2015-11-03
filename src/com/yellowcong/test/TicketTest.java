package com.yellowcong.test;

import org.junit.Test;

public class TicketTest {
	
	public static void main(String [] args){
		
		TicketsSall sall =new TicketsSall();
		new Thread(sall,"------------------第一窗口---------------------").start();
		new Thread(sall,"第二窗口---------------------------------------").start();
		new Thread(sall,"---------------------------------------第三窗口").start();
		
	}
}	

class TicketsSall implements Runnable{
	private int ticket = 100;
	public synchronized void sall(){
		if(ticket>0){
			System.out.println("线程:"+Thread.currentThread().getName()+"\t还剩下"+(--ticket)+"张票");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(ticket>0){
			sall();
		}
	}
	
}

