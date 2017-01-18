package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.CallbackQuery;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTCallbackQuery")
public class ABTCallbackQuery extends ABTObject {
	protected CallbackQuery inner = new CallbackQuery();
	
	public void Initialize() {
		this.ObjectType = "ABTCallbackQuery";
	}
	
	protected void innerSet(CallbackQuery u) {
		inner = u;		
	}
	
    public String getId() {
        return inner.getId();
    }  

    public ABTUser getFrom() {
    	ABTUser m = new ABTUser();
    	m.Initialize();
    	m.innerSet(inner.getFrom());
        return m;
    }

    public String getInlineMessageId() {
        return inner.getInlineMessageId();
    }
    
    public String getData() {
    	return inner.getData();    	
    }
    
    public ABTMessage getMessage() {
    	ABTMessage m = new ABTMessage();
    	m.Initialize();
    	m.innerSet(inner.getMessage());
    	return m;
    }

}
