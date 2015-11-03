package com.yellowcong.test;

import org.junit.Test;

class SaleTicket implements Runnable{  
    private int tickets = 100;  
      
      
    private synchronized void sale(){  
        if(tickets > 0){  
            System.out.println(Thread.currentThread().getName() + "卖出 第 "+ (tickets--)+"张票");  
              
            try{  
                Thread.sleep(100);  
            }catch(InterruptedException e){  
                e.printStackTrace();  
            }  
        }  
    }  
    public void run(){  
        while(tickets > 0){  
            sale();  
        }  
    }  
}  


public class JavaTest {  
    
	/**
	 * 测试方法下 多线程运行不了
	 */
	@Test      
	public  void Test(){  

        SaleTicket st = new SaleTicket();  
        Thread t1 = new Thread(st, "一号窗口");  
        Thread t2 = new Thread(st, "二号窗口");  
        Thread t3 = new Thread(st, "三号窗口");  
        Thread t4 = new Thread(st,"四号窗口 ");  
        t1.start();  
        t2.start();  
        t3.start();  
        t4.start();  
          
      
          
    } 
}  