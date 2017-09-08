package io.egen.DAO;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import io.egen.sensor.Weight;

@Component
public interface SensorRepository extends Repository<Weight,String>{
	public List<Weight> findBytimeStamp(@Param("start") String startTime, @Param("end") String endTime);
	public void save(Weight saved, String collectionName);
}
