package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.PhotoSize;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTPhotoSize")
public class ABTPhotoSize extends ABTObject {
	protected PhotoSize inner = new PhotoSize();
	
	public void Initialize() {
		this.ObjectType = "ABTPhotoSize";
	}
	
	protected void innerSet(PhotoSize m) {
		inner = m;
	}
	
    public String getFileId() {
        return inner.getFileId();
    }

    public Integer getWidth() {
        return inner.getWidth();
    }

    public Integer getHeight() {
        return inner.getHeight();
    }
    
    public Integer getFileSize() {
        return inner.getFileSize();
    }

    /*
    public String getFilePath() {
        return inner.getFilePath();
    }
    */
}
