package com.ab.abtelegram;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTReplyKeyboard")
public class ABTReplyKeyboard extends ABTObject {
	protected ReplyKeyboard inner;
		
	protected void innerSet(ReplyKeyboard r) {
		inner = r;
	}
	
	public void InitializeAsForceReplyKeyboard(boolean selective) {
		this.ObjectType = "ABTForceReplyKeyboard";
		ForceReplyKeyboard m = new ForceReplyKeyboard(); 
		m.setForceReply(true);
		m.setSelective(selective);
		inner = m;
	}
	
	public void InitializeAsInlineKeyboardMarkup(anywheresoftware.b4a.objects.collections.List inlineKeyboardRows) {
		this.ObjectType = "ABTInlineKeyboardMarkup";
		InlineKeyboardMarkup m = new InlineKeyboardMarkup();
		List<InlineKeyboardRow> lst = new ArrayList<InlineKeyboardRow>();
		for (int i=0;i<inlineKeyboardRows.getSize();i++) {
			ABTInlineKeyboardRow row = (ABTInlineKeyboardRow)inlineKeyboardRows.Get(i);
			lst.add(row.inner);
		}		
		m.setKeyboard(lst);
		inner = m;
	}
	
	public void InitializeAsReplyKeyboardHide(boolean selective) {
		this.ObjectType = "ABTReplyKeyboardHide";
		ReplyKeyboardHide m = new ReplyKeyboardHide();
		m.setHideKeyboard(true);
		m.setSelective(selective);
		inner = m;
	}
	
	public void InitializeAsReplyKeyboardMarkup(anywheresoftware.b4a.objects.collections.List keyboardRows, boolean resizeKeyboard, boolean oneTimeKeyboard, boolean selective) {
		this.ObjectType = "ABTReplyKeyboardMarkup";
		ReplyKeyboardMarkup m = new ReplyKeyboardMarkup();
		List<KeyboardRow> lst = new ArrayList<KeyboardRow>();
		for (int i=0;i<keyboardRows.getSize();i++) {
			ABTKeyboardRow row = (ABTKeyboardRow)keyboardRows.Get(i);
			lst.add(row.inner);
		}
		m.setKeyboard(lst);
		m.setResizeKeyboard(resizeKeyboard);
		m.setOneTimeKeyboad(oneTimeKeyboard);
		m.setSelective(selective);
		inner = m;
	}
}
