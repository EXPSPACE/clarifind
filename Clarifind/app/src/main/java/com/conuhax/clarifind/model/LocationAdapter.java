package com.conuhax.clarifind.model;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.conuhax.clarifind.R;
import com.conuhax.clarifind.model.yellowpages.Listings;

import java.util.ArrayList;

/**
 * Created by Simon on 2017-01-21.
 */

public class LocationAdapter extends ArrayAdapter<Listings> {
    public LocationAdapter(Context context, ArrayList<Listings> listing) {
        super(context, 0, listing);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Listings listings = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_location, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.locationName);
        TextView street = (TextView) convertView.findViewById(R.id.locationStreet);
        TextView city = (TextView) convertView.findViewById(R.id.locationCity);
        TextView prov = (TextView) convertView.findViewById(R.id.locationProv);
        TextView postal = (TextView) convertView.findViewById(R.id.locationPostal);

        // Populate the data into the template view using the data object
        name.setText(listings.name);
        street.setText(listings.address.street);
        city.setText(listings.address.city);
        prov.setText(listings.address.prov);
        postal.setText(listings.address.pcode);
        // Return the completed view to render on screen
        return convertView;
    }
}
