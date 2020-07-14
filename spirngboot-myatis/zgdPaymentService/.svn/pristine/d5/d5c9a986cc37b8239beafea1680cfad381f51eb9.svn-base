package com.ule.uhj.init.service.impl;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ThreadPool{
 public static void main(String args[]){
  ExecutorService threadPool = Executors.newFixedThreadPool(2);
  MyTask t1 = new MyTask("MT1");
  MyTask t2 = new MyTask("MT2");
  MyTask t3 = new MyTask("MT3");
  threadPool.execute(t1);
  threadPool.execute(t2);
  threadPool.execute(t3);
 }
}
class MyTask implements Runnable{
 private String tname;
 public MyTask(String tname){
  this.tname = tname;
 }
 public void run(){
  System.out.println("\n=========任务"+tname+"开始执行===========");
  for(int i = 0;i<30;i++){
   System.out.println("["+tname+"_"+i+"]");
  }
  System.out.println("\n=========任务"+tname+"执行结束===========");
 }
}
