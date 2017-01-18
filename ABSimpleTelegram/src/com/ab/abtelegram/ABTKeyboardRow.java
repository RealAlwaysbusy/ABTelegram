package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTKeyboardRow")
public class ABTKeyboardRow extends ABTObject {
	protected KeyboardRow inner = new KeyboardRow();
	
	public void Initialize() {
		
	}
	
	public void AddButton(String text) {
		inner.add(text);
	}
	
	public void AddButtonAt(int index, String text) {
		inner.add(index, text);
	}
	
	public boolean Contains(String text) {
		return inner.contains(text);
	}
	
	public int LastIndexOf(String text) {
		return inner.lastIndexOf(text);
	}
	
	public int IndexOf(String text) {
		return inner.indexOf(text);
	}
	
	public ABTKeyboardButton Set(int index, String text) {
		ABTKeyboardButton m = new ABTKeyboardButton();
		m.Initialize(text);
		m.innerSet(inner.set(index, text));
		return m;
	}
	
	public boolean Remove(String text) {
		return inner.remove(text);
	}
}
