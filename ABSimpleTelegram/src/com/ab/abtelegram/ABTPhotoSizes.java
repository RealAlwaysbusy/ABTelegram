package com.ab.abtelegram;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTPhotoSizes")
public class ABTPhotoSizes {
	public anywheresoftware.b4a.objects.collections.List Sizes = new anywheresoftware.b4a.objects.collections.List();
	
	public void Initialize() {
		Sizes.Initialize();
	}
	
	protected void Add(ABTPhotoSize ps) {
		Sizes.Add(ps);
	}
	
}
