package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTKeyboardButton")
public class ABTKeyboardButton extends ABTObject {
	protected KeyboardButton inner = new KeyboardButton();
	
	public void Initialize(String text) {
		inner.setText(text);
	}
	
	public void InitializeEx(String text, boolean requestContact, boolean requestLocation) {
		inner.setText(text);
		inner.setRequestContact(requestContact);
		inner.setRequestLocation(requestLocation);
	}
	
	protected void innerSet(KeyboardButton m) {
		inner = m;
	}
}
