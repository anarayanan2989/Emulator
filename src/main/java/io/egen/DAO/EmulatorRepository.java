package io.egen.DAO;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import io.egen.sensor.Weight;

public interface EmulatorRepository extends MongoRepository<Weight,String> {
	List<Weight> findByTimeRange(@Param("start") String startTime, @Param("end") String endTime);
}
