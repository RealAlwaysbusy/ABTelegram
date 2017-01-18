package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.MessageEntity;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTMessageEntity")
public class ABTMessageEntity extends ABTObject {
	protected MessageEntity inner = new MessageEntity();
	
	public void Initialize() {
		this.ObjectType = "ABTMessageEntity";
	}
	
	protected void innerSet(MessageEntity m) {
		inner = m;
	}
	
    public String getType() {
        return inner.getType();
    }

    public Integer getOffset() {
        return inner.getOffset();
    }

    public Integer getLength() {
        return inner.getLength();
    }

    public String getUrl() {
        return inner.getUrl();
    }
}
