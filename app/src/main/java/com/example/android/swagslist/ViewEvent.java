package com.example.android.swagslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aidancbrady.swagslist.EventEntry;

public class ViewEvent extends AppCompatActivity {

    public EventEntry eventEntry;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
    }
}
