package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Location;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTLocation")
public class ABTLocation extends ABTObject {
	protected Location inner = new Location();
	
	public void Initialize() {
		this.ObjectType = "ABTLocation";
	}
	
	protected void innerSet(Location m) {
		inner = m;		
	}
	
	public Double getLongitude() {
        return inner.getLongitude();
    }
  
    public Double getLatitude() {
        return inner.getLatitude();
    }
}
