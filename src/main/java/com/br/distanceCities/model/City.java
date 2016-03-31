package com.br.distanceCities.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class City {
	
	private Integer id;
	private String name;
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	public City(){
		super();
	}
	
	public City(Integer id){
		super();
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (!(obj instanceof City)) {
            return false;
        }

        City other = (City) obj;
        return (Objects.equals(this.id,other.id)
                && Objects.equals(this.latitude,other.latitude)
                && Objects.equals(this.longitude,other.longitude));
    }

}
