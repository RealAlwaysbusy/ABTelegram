package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Audio;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTAudio")
public class ABTAudio extends ABTObject {
	protected Audio inner = new Audio();
	
	public void Initialize() {
		this.ObjectType = "ABTAudio";
	}
	
	protected void innerSet(Audio m) {
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

    public String getTitle() {
        return inner.getTitle();
    }

    public String getPerformer() {
        return inner.getPerformer();
    }

}
