package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTInlineKeyboardButton")
public class ABTInlineKeyboardButton extends ABTObject {
	protected InlineKeyboardButton inner = new InlineKeyboardButton();
	
	/*
	public void Initialize(String text) {
		inner.setText(text);
	}
	*/
	
	public void InitializeEx(String text, String url, String callbackData, String switchInlineQuery) {
		inner.setText(text);
		inner.setUrl(url);
		inner.setCallbackData(callbackData);
		inner.setSwitchInlineQuery(switchInlineQuery);
	}
	
	protected void innerSet(InlineKeyboardButton m) {
		inner = m;
	}
	
    public String getText() {
    	return inner.getText();
    }

    public void setText(String text) {
        inner.setText(text);
    }

    public String getUrl() {
        return inner.getUrl();
    }

    public void setUrl(String url) {
        inner.setUrl(url);
    }

    public String getCallbackData() {
        return inner.getCallbackData();
    }

    public void setCallbackData(String callbackData) {
        inner.setCallbackData(callbackData);
    }

    public String getSwitchInlineQuery() {
        return inner.getSwitchInlineQuery();        
    }

    public void setSwitchInlineQuery(String switchInlineQuery) {
        inner.setSwitchInlineQuery(switchInlineQuery);
    }
}
