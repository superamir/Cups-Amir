package com.cups.services;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cups.R;
import com.cups.entities.Venue;
import com.cups.model.Model;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class VenuesService extends IntentService{

	public VenuesService() {
		super("VenuesService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String venues = intent.getStringExtra(getString(R.string.venues));
		updateVenueList(venues);
	}
	
	
	/**
	 * convert venues string to Json, and all all venues to the venue model.
	 * @param venues
	 */
	private void updateVenueList(String venues){
		try {
			JSONObject jObjectList = new JSONObject(venues);
			//get an array of all the names in the Json
			JSONArray jArray = jObjectList.names();
			List<Venue> venueList = new LinkedList<Venue>();
			//go through all the names and add the venue data to the list 
			for (int i = 0; i<jArray.length(); i++){
				try{
					JSONObject jObject = jObjectList.getJSONObject(jArray.getString(i));
					String name = jObject.getString(getString(R.string.name));
					String address = jObject.getString(getString(R.string.address));
					double lat = jObject.getDouble(getString(R.string.lat));
					double lng = jObject.getDouble(getString(R.string.lng));
					venueList.add(new Venue(name, address, lat, lng));
				} catch (JSONException e) {
				}
			}
			//add venues to list
			Model.getInstance().addAndReplaceVenueList(venueList);
			//notify activity that list has changed
			LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast( new Intent(getString(R.string.model_changed)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
