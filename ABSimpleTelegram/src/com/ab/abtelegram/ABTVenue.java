package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Venue;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTVenue")
public class ABTVenue extends ABTObject {
	protected Venue inner = new Venue();
	
	public void Initialize() {
		this.ObjectType = "ABTVenue";
	}
	
	protected void innerSet(Venue m) {
		inner = m;		
	}
	
    public ABTLocation getLocation() {
    	ABTLocation m = new ABTLocation();
    	m.Initialize();
    	m.innerSet(inner.getLocation());
        return m;
    }

    public String getTitle() {
        return inner.getTitle();
    }

    public String getAddress() {
        return inner.getAddress();
    }

    public String getFoursquareId() {
        return inner.getFoursquareId();
    }
}
