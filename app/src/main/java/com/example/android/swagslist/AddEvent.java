package com.example.android.swagslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    private String eventName;
    private String eventDescription;
    public boolean isSponsor;

    public void setData (View view) {
        EditText eventname = (EditText) findViewById(R.id.eventName);
        eventName = eventname.getText().toString();

        EditText eventdescription = (EditText) findViewById(R.id.eventDescription);
        eventDescription = eventdescription.getText().toString();

        RadioButton sponsor = (RadioButton) findViewById(R.id.sponsoredEvent);
        isSponsor = sponsor.isChecked();

        goToMaps(view);
    }

    public void goToMaps (View view) {
        Intent intent = new Intent (this, MapsActivity.class);

        startActivity(intent);
    }
}
