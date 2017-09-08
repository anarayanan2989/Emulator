package io.egen.sensor;

import org.mongodb.morphia.annotations.Id;

public class Weight {
	@Id
	private String id;
	
	private String timeStamp;
	private String base_weight;
	private String recorded_weight;
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getBase_weight() {
		return base_weight;
	}
	public void setBase_weight(String base_weight) {
		this.base_weight = base_weight;
	}
	public String getRecorded_weight() {
		return recorded_weight;
	}
	public void setRecorded_weight(String recorded_weight) {
		this.recorded_weight = recorded_weight;
	}
}
