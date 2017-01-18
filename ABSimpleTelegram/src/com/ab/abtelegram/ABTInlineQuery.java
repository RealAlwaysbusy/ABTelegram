package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTInlineQuery")
public class ABTInlineQuery extends ABTObject {
	protected InlineQuery inner = new InlineQuery();
	
	public void Initialize() {
		this.ObjectType = "ABTInlineQuery";
	}
	
	protected void innerSet(InlineQuery u) {
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

    public ABTLocation getLocation() {
    	ABTLocation m = new ABTLocation();
    	m.Initialize();
    	m.innerSet(inner.getLocation());
        return m;
    }

    public String getQuery() {
        return inner.getQuery();
    }

    public String getOffset() {
        return inner.getOffset();
    }	

}
