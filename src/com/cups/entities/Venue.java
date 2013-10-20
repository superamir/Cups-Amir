package com.cups.entities;

public class Venue {
	
	private String _name;
	private String _address;
	private double _latitude;
	private double _longtitude;
	
	
	public Venue(String name, String address, double lat, double lng){
		setName(name);
		setAddress(address);
		setLatitude(lat);
		setLongtitude(lng);
	}


	public String getName() {
		return _name;
	}


	public void setName(String name) {
		_name = name;
	}


	public String getAddress() {
		return _address;
	}


	public void setAddress(String address) {
		_address = address;
	}


	public double getLatitude() {
		return _latitude;
	}


	public void setLatitude(double latitude) {
		_latitude = latitude;
	}


	public double getLongtitude() {
		return _longtitude;
	}


	public void setLongtitude(double longtitude) {
		_longtitude = longtitude;
	}
	
	
	
	
}
