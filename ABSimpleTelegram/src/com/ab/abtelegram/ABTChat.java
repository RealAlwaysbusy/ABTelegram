package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Chat;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTChat")
public class ABTChat extends ABTObject {
	protected Chat inner = new Chat();
	
	public void Initialize() {
		this.ObjectType = "ABTChat";
	}
	
	protected void innerSet(Chat m) {
		inner = m;
	}
	
    public int getId() {
        return inner.getId();
    }

    public Boolean isGroupChat() {
        return inner.isGroupChat();
    }

    public Boolean isChannelChat() {
        return inner.isChannelChat();
    }

    public Boolean isUserChat() {
        return inner.isUserChat();
    }

    public Boolean isSuperGroupChat() {
        return inner.isSuperGroupChat();
    }

    public String getTitle() {
        return inner.getTitle();
    }

    public String getFirstName() {
        return inner.getFirstName();
    }

    public String getLastName() {
        return inner.getLastName();
    }

    public String getUserName() {
        return inner.getUserName();
    }	

}
