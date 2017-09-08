package io.egen.sensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.egen.DAO.SensorRepository;
import io.egen.service.SensorService;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {
	
	public static final String url = "http://CONSUMER-SERVICE";
	public static final int base_value = 150;
	
	public static void main(String args[]){
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	public SensorRepository sensorRepository(){
		return new SensorService(url,base_value);
	}
	
}
