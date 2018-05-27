package com.tzg.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

public class InvestTest {


	private final static String uri="/investor/asset/invest/exchange";
	private final static String param="{\"iloginAccountId\":4879,\"subjectId\":2635,\"investAmt\":\"100\",\"agreeProtocol\":\"true\",\"payPassword\":\"ee79976c9380d5e337fc1c095ece8c8f22f91f306ceeb161fa51fecede2c4ba1\",\"investDevice\":\"android\"}";
	 public static void main(String[] args) {  
         int count = 100;  
         CyclicBarrier cyclicBarrier = new CyclicBarrier(count);  
         ExecutorService executorService = Executors.newFixedThreadPool(count);  
         for (int i = 0; i < count; i++)  {
              executorService.execute(new InvestTest().new Task(cyclicBarrier));  
         }
         for(int j=0;j<count;j++){
        	 executorService.execute(new InvestTest().new Task2(cyclicBarrier));  
         }
         executorService.shutdown();  
         while (!executorService.isTerminated()) {  
              try {  
                   Thread.sleep(10);  
              } catch (InterruptedException e) {  
                   e.printStackTrace();  
              }  
         }  
    }  
	 
	 public class Task implements Runnable {  
         private CyclicBarrier cyclicBarrier;  
         public Task(CyclicBarrier cyclicBarrier) {  
              this.cyclicBarrier = cyclicBarrier;  
         }  
         @Override  
         public void run() {  
              try {  
                   // 等待所有任务准备就绪  
                   cyclicBarrier.await();  
                   String url = "https://testrest.tzg.cn"+uri;
           			callRestApi(url,new StringEntity(param,"utf-8"));
              } catch (Exception e) {  
                   e.printStackTrace();  
              }  
         }  
    }  
	 
	 public class Task2 implements Runnable {  
         private CyclicBarrier cyclicBarrier;  
         public Task2(CyclicBarrier cyclicBarrier) {  
              this.cyclicBarrier = cyclicBarrier;  
         }  
         @Override  
         public void run() {  
              try {  
                   // 等待所有任务准备就绪  
                   cyclicBarrier.await();  
                   String url = "https://testrest.tzg.cn"+uri;
           			callRestApi(url,new StringEntity(param,"utf-8"));
              } catch (Exception e) {  
                   e.printStackTrace();  
              }  
         }  
    }  
	 

	
	
	
	private static String callRestApi( String url,AbstractHttpEntity paramEntity) {
		HttpClient httpclient =  HttpClients.createDefault();
		HttpPost post = null;
		@SuppressWarnings("unused")
		String authentication = null;
		StringBuilder result = new StringBuilder();
		Integer httpStatus = 200;
		try {
			post = new HttpPost(url); 
			post.addHeader("Authorization","Basic MTAyMjAxNTA0Mjg6OGU1NTI3Yzc1MDk5ODU1NzBiZGU1NDRlM2M1NTZiNTM=");
			post.addHeader("Content-Type", "application/json");
			post.addHeader("Accept","application/json");
			
           post.setEntity(paramEntity);  
           HttpResponse httpResponse;  
           httpResponse = httpclient.execute(post);  
           httpStatus = httpResponse.getStatusLine().getStatusCode();
           System.out.println(httpStatus);
           HttpEntity httpEntity = httpResponse.getEntity();  
           if (httpEntity != null) { 
               BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
               String s = null;
               while (((s = reader.readLine()) != null)) {
                   result.append(s);
               }
               reader.close();// 关闭输入流
           }  
           //释放资源  
		}catch (UnsupportedEncodingException ex) {
	         ex.printStackTrace();
	    } catch (ClientProtocolException e) {
	    	 e.printStackTrace();
			return null;
		} catch (IOException e) {
			 e.printStackTrace();
			return null;
		}
		 return result.toString();
	}
		
}
