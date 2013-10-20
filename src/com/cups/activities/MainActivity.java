package com.cups.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.cups.R;
import com.cups.adapters.VenuesAdapter;
import com.cups.model.Model;
import com.cups.services.VenuesService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainActivity extends Activity {

	private BroadcastReceiver _venuesBroadcastReceiver;
	private VenuesAdapter _venuesAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Instantiate  broadcast receiver so we will know when venues list has changed
		_venuesBroadcastReceiver =  new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				handleIntent(intent);
			}
		};
		//instantiate venue array dapter
		_venuesAdapter = new VenuesAdapter(this, Model.getInstance().getVenueList());
		ListView venueListView = (ListView)findViewById(R.id.venue_list);
		venueListView.setAdapter(_venuesAdapter);

		 // Get the location manager
	    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    String provider = locationManager.getBestProvider(criteria, false);
	    //set location in model
	    Model.getInstance().setLocation(locationManager.getLastKnownLocation(provider));
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		_venuesAdapter.notifyDataSetChanged();
		//init view so that loading circle will show untill we update the venue list
		ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_circle);
		progressBar.setVisibility(View.VISIBLE);
		ListView venueListView = (ListView)findViewById(R.id.venue_list);
		venueListView.setVisibility(View.INVISIBLE);
		
		//register receiver
		LocalBroadcastManager.getInstance(this).registerReceiver(_venuesBroadcastReceiver, 
				new IntentFilter(getString(R.string.model_changed)));
		
		//request the venue list from url
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(getString(R.string.cupstelaviv_venues_url), new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				//start VenuesService.class to handle the updating of the list
				Intent intent = new Intent(getApplicationContext(), VenuesService.class);
				intent.putExtra(getString(R.string.venues), response);
				startService(intent);
			}
		});

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(_venuesBroadcastReceiver);
	}
	
	/**
	 * when the broadcast is received we will update the view and notify the array adapter
	 * @param intent
	 */
	private void handleIntent(Intent intent){
		_venuesAdapter.notifyDataSetChanged();
		ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_circle);
		progressBar.setVisibility(View.INVISIBLE);
		ListView venueListView = (ListView)findViewById(R.id.venue_list);
		venueListView.setVisibility(View.VISIBLE);
	}


	private boolean isGPSEnabled(){
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
		  .isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to 
		// go to the settings
		if (!enabled) {
		  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		  startActivity(intent);
		  return false;
		} 
		return true;
	}


}
