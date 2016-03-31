package com.br.distanceCities.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.br.distanceCities.enums.Unit;

@XmlRootElement
public class Distance {
	
	private City city1;
	private City city2;
	private BigDecimal distance;
	private Unit unit;
	
	public Distance(){
		super();
	}
	
	public Distance(City city1, City city2, BigDecimal distance, Unit unit) {
		super();
		this.city1 = city1;
		this.city2 = city2;
		this.distance = distance;
		this.unit = unit;
	}

	public City getCity1() {
		return city1;
	}

	public void setCity1(City city1) {
		this.city1 = city1;
	}

	public City getCity2() {
		return city2;
	}

	public void setCity2(City city2) {
		this.city2 = city2;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
