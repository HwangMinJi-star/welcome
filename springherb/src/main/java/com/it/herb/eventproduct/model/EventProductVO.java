package com.it.herb.eventproduct.model;

import com.it.herb.common.SearchVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class EventProductVO extends SearchVO{
	private int eventProductNo;
	private int productNo;
	private String eventName;
	
	@Override
	public String toString() {
		return "EventProductVO [eventProductNo=" + eventProductNo + ", productNo=" + productNo + ", eventName="
				+ eventName + ", toString()=" + super.toString() + "]";
	}
	
	
}
