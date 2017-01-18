package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Video;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTVideo")
public class ABTVideo extends ABTObject {
	protected Video inner = new Video();
	
	public void Initialize() {
		this.ObjectType = "ABTVideo";
	}
	
	protected void innerSet(Video m) {
		inner = m;
	}

    public Integer getWidth() {
        return inner.getWidth();
    }

    public String getFileId() {
        return inner.getFileId();
    }

    public Integer getHeight() {
        return inner.getHeight();
    }

     public Integer getDuration() {
        return inner.getDuration();
    }

    public ABTPhotoSize getThumb() {
    	ABTPhotoSize m = new ABTPhotoSize();
    	m.Initialize();
    	m.innerSet(inner.getThumb());
        return m;
    }
    
    public String getMimeType() {
        return inner.getMimeType();
    }    

    public Integer getFileSize() {
        return inner.getFileSize();
    }
}
