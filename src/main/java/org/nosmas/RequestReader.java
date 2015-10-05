package org.nosmas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


public class RequestReader {

	
	private CloseableHttpClient client;
	private HttpHost host;
	private HttpGet request;
	private HttpResponse response;
	private BufferedReader lineReader;
	private String result;
	
	
	
	

	
	/**/
	public RequestReader() {
	
	client = HttpClientBuilder.create().build();
	host= new HttpHost("api.goeuro.com",80,"http");
	request = null;
	response = null;
	
	}





	public String read(String searchKey) {
		
		request = new HttpGet("/api/v2/position/suggest/en/" + searchKey); 
		request.addHeader("accept", "application/json");
		
		try{
			response = client.execute(host, request);
			
			lineReader = 
					new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			this.result = lineReader.readLine();
			
			
			
			
			return result;
		}catch(ClientProtocolException CPE){
			System.err.println("ERROR: Http Protocol Error");
			System.exit(1);
		}catch(IOException IOE){
			System.err.println("ERROR: connection aborted");
			System.exit(1);
		}
		 
		
		return null;		
	}
	
	public void close(){
		if(client != null)
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
}
