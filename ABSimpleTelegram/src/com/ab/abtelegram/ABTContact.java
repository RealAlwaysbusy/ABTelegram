package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Contact;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTContact")
public class ABTContact extends ABTObject {
	protected Contact inner = new Contact();
	
	public void Initialize() {
		this.ObjectType = "ABTContact";
	}
	
	protected void innerSet(Contact m) {
		inner = m;		
	}
	
    public String getPhoneNumber() {    	
    	return inner.getPhoneNumber();
    }
    
    public String getFirstName() {
    	return inner.getFirstName();
    }
    
    public String getLastName() {    	
    	return inner.getLastName();
    }

    public int getUserId() {    	
    	return inner.getUserId();
    }	
	

}
