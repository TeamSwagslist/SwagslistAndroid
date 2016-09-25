package com.example.android.swagslist;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aidancbrady.swagslist.Account;
import com.aidancbrady.swagslist.EventEntry;
import com.aidancbrady.swagslist.SessionData;
import com.aidancbrady.swagslist.client.ClientNetworkHandler;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class AddEvent extends AppCompatActivity  {

    int PLACE_PICKER_REQUEST = 1;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

    public long startMillis;
    public long endMillis;
    String eventName;
    String eventDescription;
    boolean isSponsor;
    LatLng location;
    String addressOfLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

        }
        catch (GooglePlayServicesNotAvailableException ex) {
            //handle exception
        }
        catch (GooglePlayServicesRepairableException exc) {
            //handle exception
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                location = place.getLatLng();
                addressOfLocation = place.getAddress().toString();
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }

        TextView loc = (TextView) findViewById(R.id.location);
        loc.setText(addressOfLocation);
    }

    public void setData (View view) {
        EditText eventname = (EditText) findViewById(R.id.eventName);
        eventName = eventname.getText().toString();

        EditText eventdescription = (EditText) findViewById(R.id.eventDescription);
        eventDescription = eventdescription.getText().toString();

        CheckBox sponsor = (CheckBox) findViewById(R.id.sponsoredEvent);
        isSponsor = sponsor.isChecked();

        ArrayList<EventEntry.SwagType> swagset = new ArrayList<EventEntry.SwagType>();

        CheckBox food = (CheckBox) findViewById(R.id.food);
        if (food.isChecked()) {
            swagset.add(EventEntry.SwagType.FOOD);
        }

        CheckBox apparel = (CheckBox) findViewById(R.id.apparel);
        if (apparel.isChecked()) {
            swagset.add(EventEntry.SwagType.APPAREL);
        }

        CheckBox trinkets = (CheckBox) findViewById(R.id.trinkets);
        if (trinkets.isChecked()) {
            swagset.add(EventEntry.SwagType.TRINKETS);
        }

        EventEntry newEntry = new EventEntry();

        newEntry.setName(eventName);
        newEntry.setDescription(eventDescription);
        newEntry.setPremium(isSponsor);
        newEntry.setSwagSet(swagset);
        newEntry.setStartTime(startMillis);
        newEntry.setEndTime(endMillis);
        newEntry.setLocation(location.latitude, location.longitude);
        newEntry.setOwnerUsername(SessionData.username);

        ClientNetworkHandler.Response response = ClientNetworkHandler.addEvent(newEntry);

        if(response.accept) {
            this.finish();
        }
        else {
            TextView error = (TextView) findViewById(R.id.errorMessage);
            error.setText(response.message);
        }
    }


    //For the start time TimePicker
    public void onButtonClickedStart(View v){
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"TimePicker");
        startMillis = newFragment.millis;
    }

    //For the end time TimePicker
    public void onButtonClickedEnd(View v){
        TimePickerFragment1 newFragment = new TimePickerFragment1();
        newFragment.show(getFragmentManager(),"TimePicker");
        endMillis = newFragment.millis;
    }
}
