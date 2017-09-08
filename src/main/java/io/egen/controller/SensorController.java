package io.egen.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.egen.DAO.EmulatorRepository;
import io.egen.sensor.Weight;
import io.egen.service.SensorService;

@Controller
@EnableWebMvc
@RequestMapping(value ="/")
public class SensorController {
	private static final Logger log = Logger.getLogger(SensorController.class.getName());
	
	EmulatorRepository emulateRepo;
	
	
	
	@Autowired
	public SensorService sensorService;
	
	
	@GetMapping(path="/getWeights", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Weight> getAllWeights(){
		List<Weight> weight = emulateRepo.findAll();
		return weight;
		
	}
	
	@PostMapping(path="/findRange", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Weight> findByTimeRange(@PathVariable String startTime, @PathVariable String endTime){
		return emulateRepo.findByTimeRange(startTime, endTime);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void save(Weight obj ){
			sensorService.save(obj, "metrics");
	}
	
	
}
