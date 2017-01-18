package com.ab.abtelegram;

import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultAudio;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultContact;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultDocument;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultGif;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultLocation;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultMpeg4Gif;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultPhoto;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultVenue;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultVideo;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultVoice;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedAudio;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedDocument;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedGif;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedMpeg4Gif;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedPhoto;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedSticker;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedVideo;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedVoice;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import anywheresoftware.b4a.BA.ShortName;

@ShortName("ABTInlineQueryResult")
public class ABTInlineQueryResult extends ABTObject {
	protected InlineQueryResult inner;
	
	protected void innerSet(InlineQueryResult m, String type) {
		this.ObjectType = type;
		this.inner = m;
	}
	
	public void InitializeAsArticle(String id, String title, ABTInputMessageContent inputMessageContent) {
		this.ObjectType = "Article";
		InlineQueryResultArticle m = new InlineQueryResultArticle();
		m.setId(id);
		m.setTitle(title);
		m.setInputMessageContent(inputMessageContent.inner);
		this.inner = m;		
	}
	
	public void InitializeAsArticleEx(String id, String title, ABTInputMessageContent inputMessageContent, ABTReplyKeyboard inlineKeyboardMarkup, String url, boolean hideUrl, String description, String thumbUrl, int thumbWidth, int thumbHeight) {
		this.ObjectType = "Article";
		InlineQueryResultArticle m = new InlineQueryResultArticle();
		m.setId(id);
		m.setTitle(title);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		if (!url.equals("")) {
			m.setUrl(url);
		}
		m.setHideUrl(hideUrl);
		m.setDescription(description);
		m.setThumbUrl(thumbUrl);
		m.setThumbWidth(thumbWidth);
		m.setThumbHeight(thumbHeight);
		this.inner = m;		
	}
	
	public void InitializeAsAudio(String id, String title, String audioUrl) {
		this.ObjectType = "Audio";
		InlineQueryResultAudio m = new InlineQueryResultAudio();
		m.setId(id);
		m.setAudioUrl(audioUrl);
		m.setTitle(title);
		this.inner = m;
	}
	
	public void InitializeAsAudioEx(String id, String title, String audioUrl, String performer, int audioDuration, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent ) {
		this.ObjectType = "Audio";
		InlineQueryResultAudio m = new InlineQueryResultAudio();
		m.setId(id);
		m.setAudioUrl(audioUrl);
		m.setTitle(title);
		m.setAudioDuration(audioDuration);
		m.setPerformer(performer);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}
	
	public void InitializeAsAudioCached(String id, String audioFileId) {
		this.ObjectType = "AudioCached";
		InlineQueryResultCachedAudio m = new InlineQueryResultCachedAudio();
		m.setId(id);
		m.setAudioFileId(audioFileId);
		this.inner = m;
	}
	
	public void InitializeAsAudioCachedEx(String id, String audioFileId, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent) {
		this.ObjectType = "AudioCached";
		InlineQueryResultCachedAudio m = new InlineQueryResultCachedAudio();
		m.setId(id);
		m.setAudioFileId(audioFileId);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}
	
	public void InitializeAsContact(String id, String phoneNumber, String firstName) {
		this.ObjectType = "Contact";
		InlineQueryResultContact m = new InlineQueryResultContact();
		m.setId(id);
		m.setPhoneNumber(phoneNumber);
		m.setFirstName(firstName);
		this.inner = m;
	}
	
	public void InitializeAsContactEx(String id, String phoneNumber, String firstName, String lastName, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent, String thumbUrl, int thumbWidth, int thumbHeight ) {
		this.ObjectType = "Contact";
		InlineQueryResultContact m = new InlineQueryResultContact();
		m.setId(id);
		m.setPhoneNumber(phoneNumber);
		m.setFirstName(firstName);
		m.setLastName(lastName);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		m.setThumbUrl(thumbUrl);
		m.setThumbWidth(thumbWidth);
		m.setThumbHeight(thumbHeight);
		this.inner = m;
	}
	
	
	public void InitializeAsDocument(String id, String title, String documentUrl, String documentMimeType) {
		this.ObjectType = "Document";
		InlineQueryResultDocument m = new InlineQueryResultDocument();
		m.setId(id);
		m.setTitle(title);
		m.setDocumentUrl(documentUrl);
		m.setMimeType(documentMimeType);
		this.inner = m;
	}
	
	public void InitializeAsDocumentEx(String id, String title, String documentUrl, String documentMimeType, String caption, String description, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent, String thumbUrl, int thumbWidth, int thumbHeight ) {
		this.ObjectType = "Document";
		InlineQueryResultDocument m = new InlineQueryResultDocument();
		m.setId(id);
		m.setTitle(title);
		m.setDocumentUrl(documentUrl);
		m.setMimeType(documentMimeType);
		m.setCaption(caption);
		m.setDescription(description);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		m.setThumbUrl(thumbUrl);
		m.setThumbWidth(thumbWidth);
		m.setThumbHeight(thumbHeight);
		this.inner = m;
	}

	public void InitializeAsDocumentCached(String id, String title, String documentFileId) {
		this.ObjectType = "DocumentCached";
		InlineQueryResultCachedDocument m = new InlineQueryResultCachedDocument();
		m.setId(id);
		m.setTitle(title);
		m.setDocumentFileId(documentFileId);
		this.inner = m;
	}
	
	public void InitializeAsDocumentCachedEx(String id, String title, String documentFileId, String description, String caption, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent ) {
		this.ObjectType = "DocumentCached";
		InlineQueryResultCachedDocument m = new InlineQueryResultCachedDocument();
		m.setId(id);
		m.setTitle(title);
		m.setDocumentFileId(documentFileId);
		m.setDescription(description);
		m.setCaption(caption);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}
	
	public void InitializeAsGif(String id, String gifUrl, String thumbUrl) {
		this.ObjectType = "Gif";
		InlineQueryResultGif m = new InlineQueryResultGif();
		m.setId(id);
		m.setGifUrl(gifUrl);
		m.setThumbUrl(thumbUrl);
		this.inner = m;
	}
	
	public void InitializeAsGifEx(String id, String gifUrl, String thumbUrl, int gifWidth, int gifHeight, String title, String caption, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent ) {
		this.ObjectType = "Gif";
		InlineQueryResultGif m = new InlineQueryResultGif();
		m.setId(id);
		m.setGifUrl(gifUrl);
		m.setThumbUrl(thumbUrl);
		m.setGifWidth(gifWidth);
		m.setGifHeight(gifHeight);
		m.setTitle(title);
		m.setCaption(caption);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}	

	public void InitializeAsGifCached(String id, String gifFileId) {
		this.ObjectType = "GifCached";
		InlineQueryResultCachedGif m = new InlineQueryResultCachedGif();
		m.setId(id);
		m.setGifFileId(gifFileId);
		this.inner = m;
	}
	
	public void InitializeAsGifCachedEx(String id, String gifFileId, String title, String caption, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent) {
		this.ObjectType = "GifCached";
		InlineQueryResultCachedGif m = new InlineQueryResultCachedGif();
		m.setId(id);
		m.setGifFileId(gifFileId);
		m.setTitle(title);
		m.setCaption(caption);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}
	
	public void InitializeAsLocation(String id, float latitude, float longitude, String title) {
		this.ObjectType = "Location";
		InlineQueryResultLocation m = new InlineQueryResultLocation();
		m.setId(id);
		m.setLatitude(latitude);
		m.setLongitude(longitude);
		m.setTitle(title);
		this.inner = m;
	}
	
	public void InitializeAsLocationEx(String id, float latitude, float longitude, String title, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent, String thumbUrl, int thumbWidth, int thumbHeight) {
		this.ObjectType = "Location";
		InlineQueryResultLocation m = new InlineQueryResultLocation();
		m.setId(id);
		m.setLatitude(latitude);
		m.setLongitude(longitude);
		m.setTitle(title);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		m.setThumbUrl(thumbUrl);
		m.setThumbWidth(thumbWidth);
		m.setThumbHeight(thumbHeight);
		this.inner = m;
	}

	public void InitializeAsMpeg4Gif(String id, String mpeg4Url, String thumbUrl) {
		this.ObjectType = "Mpeg4Gif";
		InlineQueryResultMpeg4Gif m = new InlineQueryResultMpeg4Gif();
		m.setId(id);
		m.setMpeg4Url(mpeg4Url);
		m.setThumbUrl(thumbUrl);
		this.inner = m;
	}
	
	public void InitializeAsMpeg4Gif(String id, String mpeg4Url, String thumbUrl, int mpeg4Width, int mpeg4Height, String title, String caption, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent ) {
		this.ObjectType = "Mpeg4Gif";
		InlineQueryResultMpeg4Gif m = new InlineQueryResultMpeg4Gif();
		m.setId(id);
		m.setMpeg4Url(mpeg4Url);
		m.setThumbUrl(thumbUrl);
		m.setMpeg4Width(mpeg4Width);
		m.setMpeg4Height(mpeg4Height);
		m.setTitle(title);
		m.setCaption(caption);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}

	public void InitializeAsMpeg4GifCached(String id, String mpeg4FileId) {
		this.ObjectType = "Mpeg4GifCached";
		InlineQueryResultCachedMpeg4Gif m = new InlineQueryResultCachedMpeg4Gif();
		m.setId(id);
		m.setMpeg4FileId(mpeg4FileId);
		this.inner = m;
	}
	
	public void InitializeAsMpeg4GifCachedEx(String id, String mpeg4FileId, String title, String caption, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent) {
		this.ObjectType = "Mpeg4GifCached";
		InlineQueryResultCachedMpeg4Gif m = new InlineQueryResultCachedMpeg4Gif();
		m.setId(id);
		m.setMpeg4FileId(mpeg4FileId);
		m.setTitle(title);
		m.setCaption(caption);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}

	public void InitializeAsPhoto(String id, String photoUrl, String thumbUrl) {
		this.ObjectType = "Photo";
		InlineQueryResultPhoto m = new InlineQueryResultPhoto();
		m.setId(id);
		m.setPhotoUrl(photoUrl);
		m.setThumbUrl(thumbUrl);
		this.inner = m;
	}
	
	public void InitializeAsPhotoEx(String id, String photoUrl, String thumbUrl, int photoWidth, int photoHeight, String title, String description, String caption, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent) {
		this.ObjectType = "Photo";
		InlineQueryResultPhoto m = new InlineQueryResultPhoto();
		m.setId(id);
		m.setPhotoUrl(photoUrl);
		m.setThumbUrl(thumbUrl);
		m.setPhotoWidth(photoWidth);
		m.setPhotoHeight(photoHeight);
		m.setTitle(title);
		m.setDescription(description);
		m.setCaption(caption);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}
	
	public void InitializeAsPhotoCached(String id, String photoFileId) {
		this.ObjectType = "PhotoCached";
		InlineQueryResultCachedPhoto m = new InlineQueryResultCachedPhoto();
		m.setId(id);
		m.setPhotoFileId(photoFileId);
		this.inner = m;
	}
	
	public void InitializeAsPhotoCachedEx(String id, String photoFileId, String title, String description, String caption, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent) {
		this.ObjectType = "PhotoCached";
		InlineQueryResultCachedPhoto m = new InlineQueryResultCachedPhoto();
		m.setId(id);
		m.setPhotoFileId(photoFileId);
		m.setTitle(title);
		m.setDescription(description);
		m.setCaption(caption);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}

	public void InitializeAsStickerCached(String id, String stickerFileId) {
		this.ObjectType = "StickerCached";
		InlineQueryResultCachedSticker m = new InlineQueryResultCachedSticker();
		m.setId(id);
		m.setStickerFileId(stickerFileId);
		this.inner = m;
	}
	
	public void InitializeAsStickerCachedEx(String id, String stickerFileId, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent) {
		this.ObjectType = "StickerCached";
		InlineQueryResultCachedSticker m = new InlineQueryResultCachedSticker();
		m.setId(id);
		m.setStickerFileId(stickerFileId);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}
	
	public void InitializeAsVenue(String id, float latitude, float longitude, String title, String address ) {
		this.ObjectType = "Venue";
		InlineQueryResultVenue m = new InlineQueryResultVenue();
		m.setId(id);
		m.setLatitude(Float.toString(latitude));
		m.setLongitude(Float.toString(longitude));
		m.setTitle(title);
		m.setAddress(address);
		this.inner = m;
	}
	
	public void InitializeAsVenueEx(String id, float latitude, float longitude, String title, String address, String foursquareId, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent, String thumbUrl, int thumbWidth, int thumbHeight  ) {
		this.ObjectType = "Venue";
		InlineQueryResultVenue m = new InlineQueryResultVenue();
		m.setId(id);
		m.setLatitude(Float.toString(latitude));
		m.setLongitude(Float.toString(longitude));
		m.setTitle(title);
		m.setAddress(address);
		m.setFoursquareId(foursquareId);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		m.setThumbUrl(thumbUrl);
		m.setThumbWidth(thumbWidth);
		m.setThumbHeight(thumbHeight);
		this.inner = m;
	}
	
	public void InitializeAsVideo(String id, String videoUrl, String videoMimetype, String thumbUrl, String title) {
		this.ObjectType = "Video";
		InlineQueryResultVideo m = new InlineQueryResultVideo();
		m.setId(id);
		m.setVideoUrl(videoUrl);
		m.setMimeType(videoMimetype);
		m.setThumbUrl(thumbUrl);
		m.setTitle(title);
		this.inner = m;
	}
	
	public void InitializeAsVideoEx(String id, String videoUrl, String videoMimetype, String thumbUrl, String title, String caption, int videoWidth, int videoHeight, int videoDuration, String description, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent ) {
		this.ObjectType = "Video";
		InlineQueryResultVideo m = new InlineQueryResultVideo();
		m.setId(id);
		m.setVideoUrl(videoUrl);
		m.setMimeType(videoMimetype);
		m.setThumbUrl(thumbUrl);
		m.setTitle(title);
		m.setCaption(caption);
		m.setVideoWidth(videoWidth);
		m.setVideoHeight(videoHeight);
		m.setVideoDuration(videoDuration);
		m.setDescription(description);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}	

	public void InitializeAsVideoCached(String id, String videoFileId, String title) {
		this.ObjectType = "VideoCached";
		InlineQueryResultCachedVideo m = new InlineQueryResultCachedVideo();
		m.setId(id);
		m.setVideoFileId(videoFileId);
		m.setTitle(title);
		this.inner = m;
	}
	
	public void InitializeAsVideoCachedEx(String id, String videoFileId, String title, String description, String caption, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent) {
		this.ObjectType = "VideoCached";
		InlineQueryResultCachedVideo m = new InlineQueryResultCachedVideo();
		m.setId(id);
		m.setVideoFileId(videoFileId);
		m.setTitle(title);
		m.setDescription(description);
		m.setCaption(caption);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}

	public void InitializeAsVoice(String id, String voiceUrl, String title) {
		this.ObjectType = "Voice";
		InlineQueryResultVoice m = new InlineQueryResultVoice();
		m.setId(id);
		m.setVoiceUrl(voiceUrl);
		m.setTitle(title);
		this.inner = m;
	}
	
	public void InitializeAsVoice(String id, String voiceUrl, String title, int voiceDuration, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent ) {
		this.ObjectType = "Voice";
		InlineQueryResultVoice m = new InlineQueryResultVoice();
		m.setId(id);
		m.setVoiceUrl(voiceUrl);
		m.setTitle(title);
		m.setVoiceDuration(voiceDuration);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}

	public void InitializeAsVoiceCached(String id, String voiceFileId, String title) {
		this.ObjectType = "VoiceCached";
		InlineQueryResultCachedVoice m = new InlineQueryResultCachedVoice();
		m.setId(id);
		m.setVoiceFileId(voiceFileId);
		m.setTitle(title);
		this.inner = m;
	}
	
	public void InitializeAsVoiceCachedEx(String id, String voiceFileId, String title, ABTReplyKeyboard inlineKeyboardMarkup, ABTInputMessageContent inputMessageContent) {
		this.ObjectType = "VoiceCached";
		InlineQueryResultCachedVoice m = new InlineQueryResultCachedVoice();
		m.setId(id);
		m.setVoiceFileId(voiceFileId);
		m.setTitle(title);
		m.setInputMessageContent(inputMessageContent.inner);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup rm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				m.setReplyMarkup(rm);
			}
		}
		this.inner = m;
	}
}
