package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputContactMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputLocationMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputVenueMessageContent;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTInputMessageContent")
public class ABTInputMessageContent extends ABTObject {
	protected InputMessageContent inner;

	public void innerSet(InputMessageContent m, String type) {
		this.ObjectType = type;
		this.inner = m;
	}
	
	public void InitializeAsContact(String phoneNumber, String firstName, String lastName) {
		InputContactMessageContent m = new InputContactMessageContent();
		m.setPhoneNumber(phoneNumber);
		m.setFirstName(firstName);
		m.setLastName(lastName);
		inner = m;
	}
	
	public void InitializeAsLocation(float latitude, float longitude) {
		InputLocationMessageContent m = new InputLocationMessageContent();
		m.setLatitude(latitude);
		m.setLongitude(longitude);
		inner = m;
	}
	
	public void InitializeAsText(String messageText, String parseMode, boolean disableWebPagePreview) {
		InputTextMessageContent m = new InputTextMessageContent();
		m.setMessageText(messageText);
		m.setParseMode(parseMode);
		m.setDisableWebPagePreview(disableWebPagePreview);
		inner = m;
	}
	
	public void InitializeAsVenue(float latitude, float longitude, String title, String address, String foursquareId) {
		InputVenueMessageContent m = new InputVenueMessageContent();
		m.setLatitude(latitude);
		m.setLongitude(longitude);
		m.setTitle(title);
		m.setAddress(address);
		m.setFoursquareId(foursquareId);
		inner = m;
	}
}
