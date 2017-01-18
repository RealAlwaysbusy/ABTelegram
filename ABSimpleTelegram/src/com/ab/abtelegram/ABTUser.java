package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.User;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTUser")
public class ABTUser extends ABTObject {
	protected User inner = new User();
	
	public void Initialize() {
		this.ObjectType = "ABTUser";
	}
	
	protected void innerSet(User m) {
		inner = m;
	}
	
	public Integer getId() {
		return inner.getId();
	}

	public String getFirstName() {
		return inner.getFirstName();
	}

	public String getLastName() {
		return inner.getLastName();
	}

	public String getUserName() {
		return inner.getUserName();
	}
}
