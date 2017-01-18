package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Update;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTUpdate")
public class ABTUpdate extends ABTObject {
	protected Update inner = new Update();
	
	public void Initialize() {
		this.ObjectType = "ABTUpdate";
	}
	
	protected void innerSet(Update u) {
		inner = u;
	}
	
	public Integer getUpdateId() {
        return inner.getUpdateId();
    }
	
	public boolean hasMessage() {
		return inner.hasMessage();
	}
	
	public boolean hasInlineQuery() {
		return inner.hasInlineQuery();
	}
	
	/*
	public boolean hasChosenInlineQuery() {
		return inner.hasChosenInlineQuery();
	}
	*/
	
	public boolean hasChosenInlineResult() {
		return inner.hasChosenInlineResult();
	}
	
	public boolean hasCallbackQuery() {
		return inner.hasCallbackQuery();
	}
	
	public ABTMessage GetMessage() {
		if (inner.hasMessage()) {
			ABTMessage m = new ABTMessage();
	    	m.Initialize();
	    	m.innerSet(inner.getMessage());
	        return m;
		}
		return null;
	}
	
	public ABTInlineQuery GetInlineQuery() {
		if (inner.hasInlineQuery()) {
			ABTInlineQuery m = new ABTInlineQuery();
	    	m.Initialize();
	    	m.innerSet(inner.getInlineQuery());
	        return m;
		}
		return null;
	}
	
	public ABTChosenInlineResult GetChosenInlineResult() {
		if (inner.hasChosenInlineResult()) {
			ABTChosenInlineResult m = new ABTChosenInlineResult();
	    	m.Initialize();
	    	m.innerSet(inner.getChosenInlineResult());
	    	return m;
		}
		return null;
	}
	
	/*
	public ABTChosenInlineQuery GetChosenInlineQuery() {
		if (inner.hasChosenInlineQuery()) {
			ABTChosenInlineQuery m = new ABTChosenInlineQuery();
	    	m.Initialize();
	    	m.innerSet(inner.getChosenInlineQuery());
	    	return m;
		}
		return null;
	}
	*/
	
	public ABTCallbackQuery GetCallbackQuery() {
		if (inner.hasCallbackQuery()) {
			ABTCallbackQuery m = new ABTCallbackQuery();
	    	m.Initialize();
	    	m.innerSet(inner.getCallbackQuery());
	        return m;
		}
		return null;
	}
 
}
