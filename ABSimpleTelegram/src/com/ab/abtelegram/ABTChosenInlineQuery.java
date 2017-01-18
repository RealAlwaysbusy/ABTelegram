package com.ab.abtelegram;

// DOES not exist in the api

//import org.telegram.telegrambots.api.objects.inlinequery.ChosenInlineQuery;

import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTChosenInlineQuery")
@Hide
public class ABTChosenInlineQuery extends ABTObject {
	/*
	protected ChosenInlineQuery inner = new ChosenInlineQuery();
	
	public void Initialize() {
		this.ObjectType = "ABTChosenInlineQuery";
	}
	
	protected void innerSet(ChosenInlineQuery u) {
		inner = u;
	}

    public String getResultId() {
        return inner.getResultId();
    }  

    public String getQuery() {
        return inner.getQuery();
    }

    public ABTUser getFrom() {
    	ABTUser m = new ABTUser();
    	m.Initialize();
    	m.innerSet(inner.getFrom());
        return m;
    }

    public ABTLocation getLocation() {
    	ABTLocation m = new ABTLocation();
    	m.Initialize();
    	m.innerSet(inner.getLocation());
        return m;
    }

    public String getInlineMessageId() {
        return inner.getInlineMessageId();
    }
    */
}
