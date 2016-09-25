package com.example.android.swagslist;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.aidancbrady.swagslist.client.ClientNetworkHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnMarkerClickListener,OnInfoWindowClickListener {

    private GoogleMap mMap;

    public GoogleMap getMap()
    {
        return mMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ClientNetworkHandler.refreshEvents(this);
/*        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").snippet("YOOOO"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


       /* mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) { //if SessionData.username == marker username, then go to Edit
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);



                startActivity(intent);

            }
        });*/
    }

    public void onActivityReenter(int result, Intent data)
    {
        ClientNetworkHandler.refreshEvents(this);
    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        //just here for the info window click abstract shit.
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void goToAddEvent (View view) {
        Intent intent = new Intent(this, AddEvent.class);

        startActivity(intent);
    }

    public void goToEditEvent (View view) {
        Intent intent = new Intent(this, EditEvent.class);

        startActivity(intent);

    }

    public void refresh (View view) {
        ClientNetworkHandler.refreshEvents(this);
    }

/*    class CustomInfoWindowAdapter implements InfoWindowAdapter {
        public View getInfoWindow(Marker marker) {
            return null;
        }
        public View getInfoContents(Marker marker) {
            return null;
        }

        private void render (Marker marker, View view) {
            marker.setTitle("WOW");
            marker.setSnippet("YAY");
        }
    }*/




}
