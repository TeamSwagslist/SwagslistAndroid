package com.aidancbrady.swagslist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SessionData 
{
	public static String username;
	public static List<EventEntry> cachedEvents = new ArrayList<EventEntry>();
	
	public static void reset()
	{
		username = null;
		cachedEvents.clear();
	}
}
