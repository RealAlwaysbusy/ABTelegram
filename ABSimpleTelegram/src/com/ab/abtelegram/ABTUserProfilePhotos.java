package com.ab.abtelegram;

import java.util.List;

import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.UserProfilePhotos;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTUserProfilePhotos")
public class ABTUserProfilePhotos extends ABTObject {
	protected UserProfilePhotos inner = new UserProfilePhotos();
	
	public void Initialize() {
		
	}
	
	protected void innerSet(UserProfilePhotos m) {
		inner = m;
	}
	
	public Integer getTotalCount() {
        return inner.getTotalCount();
    }

	public anywheresoftware.b4a.objects.collections.List getPhotos() {
		anywheresoftware.b4a.objects.collections.List m = new anywheresoftware.b4a.objects.collections.List();
	    m.Initialize();
		
		List<List<PhotoSize>> phs = inner.getPhotos();
		for (int i=0;i<phs.size();i++) {
			ABTPhotoSizes sizes = new ABTPhotoSizes();
			sizes.Initialize();
		    for (int j=0;j<phs.get(i).size();j++) {
		    	ABTPhotoSize e = new ABTPhotoSize();
			 	e.Initialize();
			 	e.innerSet(phs.get(i).get(j));
			 	sizes.Add(e);
		    }
		    m.Add(sizes);
		 }
		return m;		
	}   

   
}
