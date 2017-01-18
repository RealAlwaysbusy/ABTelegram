package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.File;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTFile")
public class ABTFile extends ABTObject {
	protected File inner = new File();
	
	public void Initialize() {
				
	}
	
	protected void innerSet(File m) {
		inner = m;
	}
	
    public String getFileId() {
        return inner.getFileId();
    }
    
    public Integer getFileSize() {
        return inner.getFileSize();        		
    }

    public String getFilePath() {
        return inner.getFilePath();
    }    
}
