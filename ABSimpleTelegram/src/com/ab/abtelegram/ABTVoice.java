package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Voice;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTVoice")
public class ABTVoice extends ABTObject {
	protected Voice inner = new Voice();
	
	public void Initialize() {
		this.ObjectType = "ABTVoice";
	}
	
	protected void innerSet(Voice m) {
		inner = m;		
	}
	
    public String getFileId() {
        return inner.getFileId();
    }   

    public Integer getDuration() {
        return inner.getDuration();
    }

    public String getMimeType() {
        return inner.getMimeType();
    }    

    public Integer getFileSize() {
        return inner.getFileSize();
    }  

}
