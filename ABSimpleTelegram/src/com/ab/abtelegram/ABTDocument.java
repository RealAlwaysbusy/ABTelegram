package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.Document;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTDocument")
public class ABTDocument extends ABTObject {
	protected Document inner = new Document();
	
	public void Initialize() {
		this.ObjectType = "ABTDocument";
	}
	
	protected void innerSet(Document m) {
		inner = m;
	}
	
    public String getFileId() {
        return inner.getFileId();
    }

    public ABTPhotoSize getThumb() {
    	ABTPhotoSize m = new ABTPhotoSize();
    	m.Initialize();
    	m.innerSet(inner.getThumb());
        return m;
    }

    public String getFileName() {
        return inner.getFileName();
    }

    public String getMimeType() {
        return inner.getMimeType();
    }

    public Integer getFileSize() {
        return inner.getFileSize();
    }
}
