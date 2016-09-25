package com.aidancbrady.swagslist.client;

import android.content.Intent;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aidancbrady.swagslist.EventEntry;
import com.aidancbrady.swagslist.SessionData;
import com.aidancbrady.swagslist.SharedData;
import com.example.android.swagslist.EditEvent;
import com.example.android.swagslist.MainActivity;
import com.example.android.swagslist.MapsActivity;
import com.example.android.swagslist.ViewEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ClientNetworkHandler 
{
	public static List<String> sendMessages(String... messages)
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			List<String> responses = new ArrayList<String>();
			Socket socket = new Socket(SharedData.SERVER_IP, SharedData.SERVER_PORT);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			for(String message : messages)
			{
				writer.println(message);
			}

			writer.flush();
			
			responses.add(reader.readLine());

			socket.close();
			
			return responses;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void refreshEvents(final MapsActivity activity)
	{
		SessionData.cachedEvents.clear();
		List<String> response = sendMessages("LISTENTRIES");
		
		try {
			if(response != null)
			{
				String[] split = response.get(0).split(SharedData.PRIME_SPLITTER);
				int amount = Integer.parseInt(split[1]);
				
				for(int i = 0; i < amount; i++)
				{
					EventEntry entry = EventEntry.createFromCSV(split[i+2].split(SharedData.SPLITTER), 0);
					
					if(entry != null)
					{
						SessionData.cachedEvents.add(entry);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		activity.getMap().clear();

		for(final EventEntry entry : SessionData.cachedEvents) {
			LatLng sydney = new LatLng(entry.getLatitude(), entry.getLongitude());
			activity.getMap().addMarker(new MarkerOptions().position(sydney).title(entry.getName()).snippet(entry.eventSnippet()));
			activity.getMap().moveCamera(CameraUpdateFactory.newLatLng(sydney));

		/*	activity.getMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(Marker marker) {
					Intent intent = new Intent(activity, ViewEvent.class);
					activity.startActivity(intent);
					return false;
				}
			});*/

			activity.getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
				@Override
				public void onInfoWindowClick(Marker marker) { //if SessionData.username == marker username, then go to Edit
					Intent intent = new Intent(activity, EditEvent.class);

					if(SessionData.username==entry.getOwnerUsername()) {
						activity.startActivity(intent);
					}
				}
			});
		}
	}
	
	public static Response login(String username, String password)
	{
		List<String> response = sendMessages("AUTH" + SharedData.SPLITTER + username + SharedData.SPLITTER + password);
		
		try {
			if(response != null)
			{
				String[] split = response.get(0).split(SharedData.SPLITTER);
				
				if(split[0].equals("ACCEPT"))
				{
					SessionData.username = username;
					return Response.ACCEPT;
				}
				else {
					return new Response(false, split[1]);
				}
			}
		} catch(Exception e) {
			SessionData.reset();
			e.printStackTrace();
		}
		
		return Response.ERROR;
	}
	
	public static Response register(String username, String password)
	{
		List<String> response = sendMessages("REGISTER" + SharedData.SPLITTER + username + SharedData.SPLITTER + password);
		
		try {
			if(response != null)
			{
				String[] split = response.get(0).split(SharedData.SPLITTER);
				
				if(split[0].equals("ACCEPT"))
				{
					SessionData.username = username;
					return Response.ACCEPT;
				}
				else {
					return new Response(false, split[1]);
				}
			}
		} catch(Exception e) {
			SessionData.reset();
			e.printStackTrace();
		}
		
		return Response.ERROR;
	}
	
	public static Response addEvent(EventEntry entry)
	{
		List<String> response = sendMessages("NEWENTRY" + SharedData.SPLITTER + entry.toCSV());
		
		try {
			if(response != null)
			{
				String[] split = response.get(0).split(SharedData.SPLITTER);
				return split[0].equals("ACCEPT") ? Response.ACCEPT : new Response(false, split[1]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return Response.ERROR;
	}
	
	public static Response editEvent(String origName, EventEntry entry)
	{
		List<String> response = sendMessages("EDITENTRY," + origName + "," + entry.toCSV());
		
		try {
			if(response != null)
			{
				String[] split = response.get(0).split(SharedData.SPLITTER);
				return split[0].equals("ACCEPT") ? Response.ACCEPT : new Response(false, split[1]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return Response.ERROR;
	}
	
	public static class Response
	{
		public static final Response ERROR = new Response(false, "Error");
		public static final Response ACCEPT = new Response(true, null);
		
		public boolean accept;
		public String message;
		
		public Response(boolean accept, String message)
		{
			this.accept = accept;
			this.message = message;
		}
	}
}