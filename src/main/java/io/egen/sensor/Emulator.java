package io.egen.sensor;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.NoHttpResponseException;
import org.apache.http.conn.HttpHostConnectException;

import groovyx.net.http.HTTPBuilder;



public class Emulator {
	private static int interval_in_ms = 5000;
	
	private static Emulator emulator = null;
	
	public static Emulator getInstance(){
		if(emulator == null){
			return new Emulator();
		}
		else
			return emulator;
	}
	
	public static void post(String url, int value) throws Exception {
		HTTPBuilder http = new HTTPBuilder(url);
		
		Map<String, Object> map = new HashMap<String,Object>();
		String json = "{\"timeStamp\": \"" + String.valueOf(System.currentTimeMillis()) + "\", \"value\": \"" + value + "\"}";
		map.put("body", json);
		System.out.println("Posting data " + json + " to api at " + url);
		
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("content-type", "application/json");
		map.put("headers", headers);
		
		try {
			http.post(map);
		} catch(Exception e) {
			System.out.println("API [" + url + "] not reachable. Error - " + e.getMessage());
		}
		Thread.sleep(interval_in_ms);
	}

}
