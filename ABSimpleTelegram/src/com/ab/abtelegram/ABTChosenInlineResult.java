package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.inlinequery.result.ChosenInlineResult;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTChosenInlineResult")
public class ABTChosenInlineResult extends ABTObject {
	
	protected ChosenInlineResult inner = new ChosenInlineResult();
	
	public void Initialize() {
		this.ObjectType = "ABTChosenInlineResult";
	}
	
	protected void innerSet(ChosenInlineResult u) {
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
   
}
