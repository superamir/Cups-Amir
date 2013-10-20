package com.cups.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;

import com.cups.entities.Venue;

public class Model {
	
	private static Model _instance;
	private  List<Venue> _venueList; 
	private Location _location;
	
	private Model(){
		setVenueList(new LinkedList<Venue>());
	}
	
	public static Model getInstance(){
		if(_instance == null){
			_instance = new Model();
		}
		return _instance;
	}

	public List<Venue> getVenueList() {
		return _venueList;
	}

	public void setVenueList(List<Venue> venueList) {
		_venueList = venueList;
	}
	
	public void addAndReplaceVenueList(List<Venue> venueList){
		_venueList.clear();
		if (getLocation() != null){
			Collections.sort(venueList, new VenueComparable());
		}
		_venueList.addAll(venueList);
	}

	public Location getLocation() {
		return _location;
	}

	public void setLocation(Location location) {
		_location = location;
	}
	
	public class VenueComparable implements Comparator<Venue>{
		 
	    @Override
	    public int compare(Venue v1, Venue v2) {
	    	Location locationv1 = new Location("GPS");
	    	locationv1.setLatitude(v1.getLatitude());
	    	locationv1.setLongitude(v1.getLongtitude());
	    	Location locationv2 = new Location("GPS");
	    	locationv2.setLatitude(v2.getLatitude());
	    	locationv2.setLongitude(v2.getLongtitude());
	    	Float v1Distance = getLocation().distanceTo(locationv1);
	    	Float v2Distance = getLocation().distanceTo(locationv2);
	    	return v1Distance.compareTo(v2Distance);
	    }
	}

}
