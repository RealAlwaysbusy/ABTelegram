package com.ab.abtelegram;

import java.util.List;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.PhotoSize;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTMessage")
public class ABTMessage extends ABTObject {
	protected Message inner = new Message();
	
	public void Initialize() {
		this.ObjectType = "ABTMessage";
	}
	
	protected void innerSet(Message m) {
		inner = m;
	}
	
	public Integer getMessageId() {
        return inner.getMessageId();
    }

    public ABTUser getFrom() {
    	ABTUser m = new ABTUser();
    	m.Initialize();
    	m.innerSet(inner.getFrom());
        return m;
    }

    public Integer getDate() {
        return inner.getDate();
    }

    public ABTChat getChat() {
    	ABTChat m = new ABTChat();
    	m.Initialize();
    	m.innerSet(inner.getChat());
        return m;
    }

    public ABTUser getForwardFrom() {
    	ABTUser m = new ABTUser();
    	m.Initialize();
    	m.innerSet(inner.getForwardFrom());
        return m;
    }

    public Integer getForwardDate() {
        return inner.getForwardDate();
    }

    public String getText() {
        return inner.getText();
    }

    public anywheresoftware.b4a.objects.collections.List getEntities() {
    	anywheresoftware.b4a.objects.collections.List m = new anywheresoftware.b4a.objects.collections.List();
    	m.Initialize();
    	List<MessageEntity> mes = inner.getEntities();
    	for (int i=0;i<mes.size();i++) {
    		ABTMessageEntity e = new ABTMessageEntity();
    		e.Initialize();
    		e.innerSet(mes.get(i));
    		m.Add(e);
    	}
        return m;
    }

    public ABTAudio getAudio() {
    	ABTAudio m = new ABTAudio();
    	m.Initialize();
    	m.innerSet(inner.getAudio());
        return m;
    }

    public ABTDocument getDocument() {
    	ABTDocument m = new ABTDocument();
    	m.Initialize();
    	m.innerSet(inner.getDocument());
        return m;        
    }

    public anywheresoftware.b4a.objects.collections.List getPhoto() {
    	anywheresoftware.b4a.objects.collections.List m = new anywheresoftware.b4a.objects.collections.List();
    	m.Initialize();    	
    	List<PhotoSize> phs = inner.getPhoto();
    	for (int i=0;i<phs.size();i++) {
    		ABTPhotoSize e = new ABTPhotoSize();
    		e.Initialize();
    		e.innerSet(phs.get(i));
    		m.Add(e);
    	}
        return m;
    }

    public ABTSticker getSticker() {
    	ABTSticker m = new ABTSticker();
    	m.Initialize();
    	m.innerSet(inner.getSticker());
        return m;    
    }

    public ABTVideo getVideo() {
    	ABTVideo m = new ABTVideo();
    	m.Initialize();
    	m.innerSet(inner.getVideo());
        return m;  
        
    }

    public ABTContact getContact() {
    	ABTContact m = new ABTContact();
    	m.Initialize();
    	m.innerSet(inner.getContact());
        return m;    
    }

    public ABTLocation getLocation() {
    	ABTLocation m = new ABTLocation();
    	m.Initialize();
    	m.innerSet(inner.getLocation());
        return m;    
    }

    public ABTVenue getVenue() {
    	ABTVenue m = new ABTVenue();
    	m.Initialize();
    	m.innerSet(inner.getVenue());
        return m;    
    }

    public ABTMessage getPinnedMessage() {
    	ABTMessage m = new ABTMessage();
    	m.Initialize();
    	m.innerSet(inner.getPinnedMessage());
        return m;    
    }

    public ABTUser getNewChatMember() {
    	ABTUser m = new ABTUser();
    	m.Initialize();
    	m.innerSet(inner.getNewChatMember());
        return m;
    }

    public ABTUser getLeftChatMember() {
    	ABTUser m = new ABTUser();
    	m.Initialize();
    	m.innerSet(inner.getLeftChatMember());
        return m;    
    }

    public String getNewChatTitle() {
        return inner.getNewChatTitle();
    }

    public anywheresoftware.b4a.objects.collections.List getNewChatPhoto() {
    	anywheresoftware.b4a.objects.collections.List m = new anywheresoftware.b4a.objects.collections.List();
    	m.Initialize();    	
    	List<PhotoSize> phs = inner.getNewChatPhoto();
    	for (int i=0;i<phs.size();i++) {
    		ABTPhotoSize e = new ABTPhotoSize();
    		e.Initialize();
    		e.innerSet(phs.get(i));
    		m.Add(e);
    	}
        return m;
    }

    public Boolean getDeleteChatPhoto() {
        return inner.getDeleteChatPhoto();
    }

    public Boolean getGroupchatCreated() {
        return inner.getGroupchatCreated();
    }

    public ABTMessage getReplyToMessage() {
    	ABTMessage m = new ABTMessage();
    	m.Initialize();
    	m.innerSet(inner.getReplyToMessage());
        return m;  
    }

    public ABTVoice getVoice() {
    	ABTVoice m = new ABTVoice();
    	m.Initialize();
    	m.innerSet(inner.getVoice());
        return m;  
    }

    public Boolean getSuperGroupCreated() {
        return inner.getSuperGroupCreated();
    }

    public Boolean getChannelChatCreated() {
        return inner.getChannelChatCreated();
    }

    public Long getMigrateToChatId() {
        return inner.getMigrateToChatId();
    }

    public Long getMigrateFromChatId() {
        return inner.getMigrateFromChatId();
    }

    public boolean isGroupMessage() {
        return inner.getChat().isGroupChat();
    }

    public boolean isUserMessage() {
        return inner.getChat().isUserChat();
    }

    public boolean isChannelMessage() {
    	return inner.getChat().isChannelChat();
    }

    public boolean isSuperGroupMessage() {
    	return inner.getChat().isSuperGroupChat();
    }

    public int getChatId() {
    	return inner.getChat().getId();
    }

    public boolean hasText() {
        return inner.hasText();
    }

    public boolean hasDocument() {
        return inner.hasDocument();
    }

    public boolean isReply() {
        return inner.isReply();
    }

    public boolean hasLocation() {
        return inner.hasLocation();
    }
}
