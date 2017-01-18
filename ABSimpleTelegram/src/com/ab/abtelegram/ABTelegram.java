package com.ab.abtelegram;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import anywheresoftware.b4a.BA.Author;
import anywheresoftware.b4a.BA.DependsOn;
import anywheresoftware.b4a.BA.DesignerName;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;

@DesignerName("Build 20160422")                                    
@Version(1.01F)                                
@Author("Alain Bailleul")      
@ShortName("ABTelegram")    
@DependsOn(values={"jersey-container-grizzly2-http-2.22.2", "javax.inject-2.4.0-b34", "grizzly-http-server-2.3.23", "grizzly-http-2.3.23", "grizzly-framework-2.3.23", "jersey-common-2.22.2", "javax.annotation-api-1.2", "jersey-guava-2.22.2", "hk2-api-2.4.0-b34", "hk2-utils-2.4.0-b34", "aopalliance-repackaged-2.4.0-b34", "hk2-locator-2.4.0-b34", "javassist-3.18.1-GA", "osgi-resource-locator-1.0.1", "jersey-server-2.22.2", "jersey-client-2.22.2", "jersey-media-jaxb-2.22", "validation-api-1.1.0.Final", "javax.ws.rs-api-2.0.1", "jersey-media-json-jackson-2.22.2", "jersey-entity-filtering-2.22.2", "jackson-jaxrs-base-2.5.4", "jackson-core-2.5.4", "jackson-databind-2.5.4", "jackson-jaxrs-json-provider-2.5.4", "jackson-module-jaxb-annotations-2.5.4", "jackson-annotations-2.5.4", "jersey-bundle-1.19.1", "jsr311-api-1.1.1", "jersey-grizzly2-servlet-1.19.1", "grizzly-http-servlet-2.2.16", "jersey-grizzly2-1.19.1", "jersey-server-1.19.1", "jersey-core-1.19.1", "javax.servlet-api-3.0.1", "jersey-servlet-1.19.1", "json-20160212", "httpclient-4.5.2", "httpcore-4.4.4", "commons-logging-1.2", "commons-codec-1.9", "httpmime-4.5.2"})
public class ABTelegram {
	TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
	
	public static final String PARSEMODE_MARKDOWN = "Markdown";
    public static final String PARSEMODE_HTML = "html";
    
    public static final String ENTITYTYPE_MENTION = "mention"; ///< @username
    public static final String ENTITYTYPE_HASHTAG = "hashtag"; ///< #hashtag
    public static final String ENTITYTYPE_BOTCOMMAND = "bot_command"; ///< /botcommand
    public static final String ENTITYTYPE_URL = "url"; ///< http://url.url
    public static final String ENTITYTYPE_EMAIL = "email"; ///< email@email.com
    public static final String ENTITYTYPE_BOLD = "bold"; ///< Bold text
    public static final String ENTITYTYPE_ITALIC = "italic"; ///< Italic text
    public static final String ENTITYTYPE_CODE = "code"; ///< Monowidth string
    public static final String ENTITYTYPE_PRE = "pre"; ///< Monowidth block
    public static final String ENTITYTYPE_TEXTLINK = "text_link"; ///< Clickable urls
    
    public static final String DOCUMENTMIMETYPE_PDF="application/pdf";
    public static final String DOCUMENTMIMETYPE_ZIP="application/zip";
    
    public static final String VIDEOMIMETYPE_TEXT="text/html";
    public static final String VIDEOMIMETYPE_MP4="video/mp4";
    
    public static final String CHATACTIONTYPE_TYPING="typing";
    public static final String CHATACTIONTYPE_UPLOADPHOTO="upload_photo";
    public static final String CHATACTIONTYPE_RECORDVIDEO="record_video";
    public static final String CHATACTIONTYPE_UPLOADVIDEO="upload_video";
    public static final String CHATACTIONTYPE_RECORDAUDIO="record_audio";
    public static final String CHATACTIONTYPE_UPLOADAUDIO="upload_audio";
    public static final String CHATACTIONTYPE_UPLOADDOCUMENT="upload_document";
    public static final String CHATACTIONTYPE_FINDLOCATION="find_location";
    
	public void RegisterLongPollingBot(ABTLongPollingBot telegramLongPollingBot) throws TelegramApiException {
		telegramBotsApi.registerBot(telegramLongPollingBot);
	}
}
