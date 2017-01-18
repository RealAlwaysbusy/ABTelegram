package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTInlineKeyboardRow")
public class ABTInlineKeyboardRow {
	protected InlineKeyboardRow inner = new InlineKeyboardRow();
	
	public void Initialize() {
		
	}
	
	public void AddButton(String text, String url, String callbackData, String switchInlineQuery) {
		inner.add(text, url, callbackData, switchInlineQuery);
	}
	
	public void AddButtonAt(int index, String text, String url, String callbackData, String switchInlineQuery) {
		inner.add(index, text, url, callbackData, switchInlineQuery);
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
	
	public ABTInlineKeyboardButton Set(int index, String text, String url, String callbackData, String switchInlineQuery) {
		ABTInlineKeyboardButton m = new ABTInlineKeyboardButton();
		m.innerSet(inner.set(index, text, url, callbackData, switchInlineQuery));
		return m;
	}
	
	public boolean Remove(String text) {
		return inner.remove(text);
	}
}
