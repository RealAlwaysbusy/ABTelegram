package org.telegram.telegrambots.api.objects.replykeyboard.buttons;

import java.util.ArrayList;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;


public class InlineKeyboardRow extends ArrayList<InlineKeyboardButton> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2541306838515874460L;

	public boolean add(String text, String url, String callbackData, String switchInlineQuery) {
		InlineKeyboardButton m = new InlineKeyboardButton();
		m.setText(text);
		if (!url.equals("")) {
			m.setUrl(url);
		}
		if (!callbackData.equals("")) {
			m.setCallbackData(callbackData);
		}
		if (!switchInlineQuery.equals("")) {
			m.setSwitchInlineQuery(switchInlineQuery);
		}
        return super.add(m);
    }

    public void add(int index, String text, String url, String callbackData, String switchInlineQuery) {
    	InlineKeyboardButton m = new InlineKeyboardButton();
		m.setText(text);
		if (!url.equals("")) {
			m.setUrl(url);
		}
		if (!callbackData.equals("")) {
			m.setCallbackData(callbackData);
		}
		if (!switchInlineQuery.equals("")) {
			m.setSwitchInlineQuery(switchInlineQuery);
		}
        super.add(index, m);
    }

    /*
    public boolean contains(String text) {
        return super.contains(new InlineKeyboardButton(text));
    }

    public int lastIndexOf(String text) {
        return super.lastIndexOf(new InlineKeyboardButton(text));
    }

    public int indexOf(String text) {
        return super.indexOf(new InlineKeyboardButton(text));
    }
    */

    public InlineKeyboardButton set(int index, String text, String url, String callbackData, String switchInlineQuery) {
    	InlineKeyboardButton m = new InlineKeyboardButton();
		m.setText(text);
		if (!url.equals("")) {
			m.setUrl(url);
		}
		if (!callbackData.equals("")) {
			m.setCallbackData(callbackData);
		}
		if (!switchInlineQuery.equals("")) {
			m.setSwitchInlineQuery(switchInlineQuery);
		}
        return super.set(index, m);
    }

    public boolean remove(String text) {
    	InlineKeyboardButton foundBtn = null;
    	for (int i=0;i<size();i++) {
    		if (this.get(i).getText().equals("")) {
    			foundBtn = this.get(i);
    		}
    	}
    	if (foundBtn!=null) {
    		return super.remove(foundBtn);
    	}
    	return false;
        
    }
}
