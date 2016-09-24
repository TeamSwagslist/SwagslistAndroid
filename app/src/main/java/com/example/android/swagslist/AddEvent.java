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

import com.aidancbrady.swagslist.Account;
import com.aidancbrady.swagslist.EventEntry;
import com.aidancbrady.swagslist.SessionData;
import com.aidancbrady.swagslist.client.ClientNetworkHandler;

import java.util.ArrayList;

public class AddEvent extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    public void setData (View view) {
        EditText eventname = (EditText) findViewById(R.id.eventName);
        String eventName = eventname.getText().toString();

        EditText eventdescription = (EditText) findViewById(R.id.eventDescription);
        String eventDescription = eventdescription.getText().toString();

        CheckBox sponsor = (CheckBox) findViewById(R.id.sponsoredEvent);
        boolean isSponsor = sponsor.isChecked();

        EventEntry newEntry = new EventEntry();

        newEntry.setName(eventName);
        newEntry.setDescription(eventDescription);
        newEntry.setPremium(isSponsor);

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

        newEntry.setSwagSet(swagset);
        newEntry.setStartTime(0);
        newEntry.setEndTime(0);
        newEntry.setOwnerUsername(SessionData.username);

        ClientNetworkHandler.Response response = ClientNetworkHandler.addEvent(newEntry);

        if(response.accept) {
            goToMaps(view);
        }
        else {
            TextView error = (TextView) findViewById(R.id.errorMessage);
            error.setText(response.message);
        }
    }

    public void goToMaps (View view) {
        Intent intent = new Intent (this, MapsActivity.class);

        startActivity(intent);
    }

    public void onButtonClicked(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"TimePicker");
    }
}
