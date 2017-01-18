package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Sticker;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTSticker")
public class ABTSticker extends ABTObject {
	protected Sticker inner = new Sticker();
	
	public void Initialize() {
		this.ObjectType = "ABTSticker";
	}
	
	protected void innerSet(Sticker m) {
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

    public ABTPhotoSize getThumb() {
    	ABTPhotoSize m = new ABTPhotoSize();
    	m.Initialize();
    	m.innerSet(inner.getThumb());
        return m;
    }

}
