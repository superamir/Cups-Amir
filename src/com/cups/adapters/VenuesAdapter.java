package com.cups.adapters;

import java.util.List;

import com.cups.R;
import com.cups.entities.Venue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VenuesAdapter extends ArrayAdapter<Venue>{

	public VenuesAdapter(Context context, List<Venue> objects) {
		super(context, R.layout.venue_item_layout, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Venue venue = getItem(position);

		View venueItemLayout = (RelativeLayout)li.inflate(R.layout.venue_item_layout, parent, false);
		TextView nameView = (TextView)venueItemLayout.findViewById(R.id.venue_name);
		TextView addressView = (TextView)venueItemLayout.findViewById(R.id.venue_address);
		nameView.setText(venue.getName());
		addressView.setText(venue.getAddress());
		return venueItemLayout;
	}

}
