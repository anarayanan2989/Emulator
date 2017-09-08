package io.egen.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.client.RestTemplate;

import io.egen.DAO.SensorRepository;
import io.egen.sensor.Emulator;
import io.egen.sensor.Weight;

public class SensorService implements SensorRepository {
	
	@Autowired
	public RestTemplate restTemplate;
	
	@Autowired
	public MongoOperations mongoOperations;
	
	protected String api_Url;
	protected int base_value;
	
	protected Emulator emulator = Emulator.getInstance();
	
	private static final Logger log = Logger.getLogger(SensorService.class.getName());
	
	public SensorService(String api_Url,int base_value){
		this.api_Url = api_Url;
		this.base_value = base_value;
	}

	public List<Weight> findBytimeStamp(String startTime, String endTime) {
		log.log(Level.INFO,"Retrieving all JSON objects based on the timestamp range from" +startTime+ "to " + endTime);
		Weight[] weights = restTemplate.getForObject(api_Url+"/findRange/{startTime}/{endTime}",Weight[].class);
		return Arrays.asList(weights);
	}

	public void save(Weight saved, String collectionName) {
		int anomaly_1 = 30000;
		int anomaly_2 = -90;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if(mongoOperations.collectionExists(Weight.class)){
			mongoOperations.dropCollection(Weight.class);
		}
		else{
			while(true) {
			int start_weight = base_value;

			// increasing the weight up by 30 till 190
			for(int i = 0; i < 30; i++) {
				try {
					emulator.post(api_Url, start_weight++);
					saved.setBase_weight(Integer.toString(base_value));
					saved.setRecorded_weight(Integer.toString(start_weight));
					saved.setTimeStamp(dateFormat.format(System.currentTimeMillis()));
					
					mongoOperations.save(saved, "collection");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// decreasing the weight up by 15 till 175
			for(int i = 0; i < 15; i++) {
				try {
					emulator.post(api_Url, start_weight--);
					saved.setBase_weight(Integer.toString(base_value));
					saved.setRecorded_weight(Integer.toString(start_weight));
					saved.setTimeStamp(dateFormat.format(System.currentTimeMillis()));
					
					mongoOperations.save(saved, "collection");
					saved.setBase_weight(Integer.toString(base_value));
					saved.setRecorded_weight(Integer.toString(start_weight));
					saved.setTimeStamp(dateFormat.format(System.currentTimeMillis()));
					
					mongoOperations.save(saved, "collection");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				emulator.post(api_Url, anomaly_1);
				saved.setBase_weight(Integer.toString(base_value));
				saved.setRecorded_weight(Integer.toString(start_weight));
				saved.setTimeStamp(dateFormat.format(System.currentTimeMillis()));
				
				mongoOperations.save(saved, "collection");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// decreasing the weight up by 15 till 160
			for(int i = 0; i < 15; i++) {
				try {
					emulator.post(api_Url, start_weight--);
					saved.setBase_weight(Integer.toString(base_value));
					saved.setRecorded_weight(Integer.toString(start_weight));
					saved.setTimeStamp(dateFormat.format(System.currentTimeMillis()));
					
					mongoOperations.save(saved, "collection");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				emulator.post(api_Url, anomaly_2);
				saved.setBase_weight(Integer.toString(base_value));
				saved.setRecorded_weight(Integer.toString(start_weight));
				saved.setTimeStamp(dateFormat.format(System.currentTimeMillis()));
				
				mongoOperations.save(saved, "collection");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}
	}
}
