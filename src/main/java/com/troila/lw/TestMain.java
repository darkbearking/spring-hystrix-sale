package com.troila.lw;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TestMain {

	//模擬客戶端瀏覽器
	public static void main(String[] args) throws Exception {
		final CloseableHttpClient httpclient = HttpClients.createDefault();
		final String url = "http://localhost:8080/toHello";
		System.out.println("into here ");
		for(int i=0; i<20; i++) {
			Thread t = new Thread() {

				@Override
				public void run() {
					try {
						HttpGet httpget = new HttpGet(url);
						HttpResponse response = httpclient.execute(httpget);
						System.out.println(EntityUtils.toString(response.getEntity()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
		Thread.sleep(15000);
	}
}
