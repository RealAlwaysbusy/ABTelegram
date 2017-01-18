package com.ab.abtelegram;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.Author;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.GetMe;
import org.telegram.telegrambots.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendChatAction;
import org.telegram.telegrambots.api.methods.send.SendContact;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendLocation;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.methods.send.SendVenue;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.methods.send.SendVoice;
import org.telegram.telegrambots.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.UserProfilePhotos;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.updateshandlers.SentCallback;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;

@Author("Alain Bailleul")      
@Events(values={"UpdateReceived(update as ABTUpdate)","AsyncSendReceived(methodType as String, callbackId as String, success as Boolean, asyncObject as ABTObject)"})
@ShortName("ABTLongPollingBot")  
public class ABTLongPollingBot extends TelegramLongPollingBot {
	private final ExecutorService exe = Executors.newSingleThreadExecutor();
	protected String Token="";
	protected String UserName="";
	protected BA _ba;
	protected String _event;
	protected Object caller;
	
	@Override
	public String getBotToken() {
	    return Token;
	}

	@Override
	@Hide
	public void onUpdateReceived(Update update) {
		ABTUpdate m = new ABTUpdate();
		m.innerSet(update);
		_ba.raiseEvent(caller, _event + "_updatereceived", new Object[] {m});
	}

	@Override
	public String getBotUsername() {
	   return UserName;
	}
	
	public void Initialize(final BA ba, String eventName, Object callObject, String token, String userName) {
		_ba=ba;
		_event=eventName.toLowerCase();
		caller = callObject;
		Token=token;
		UserName=userName;
		
	}
	
	public ABTMessage BotSendMessage(String chatId, String text) throws TelegramApiException {
		SendMessage inner = new SendMessage();
		inner.setChatId(chatId);
		inner.setText(text);		
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendMessageEx(String chatId, String text, String parseMode, boolean disableWebPagePreview, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyMarkup ) throws TelegramApiException {
		SendMessage inner = new SendMessage();
		inner.setChatId(chatId);
		inner.setText(text);	
		switch (parseMode) {
			case ABTelegram.PARSEMODE_MARKDOWN:
				inner.enableMarkdown(true);
				break;
			case ABTelegram.PARSEMODE_HTML:
				inner.enableHtml(true);
				break;
		}
		if (disableWebPagePreview) inner.disableWebPagePreview();
		if (disableNotification) inner.disableNotification();
		if (replyToMessageId!=0) {
			inner.setReplayToMessageId(replyToMessageId);
		}
		if (replyMarkup!=null) {
			inner.setReplayMarkup(replyMarkup.inner);
		}
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotSendMessageAsync(String callbackId, String chatId, String text) throws TelegramApiException {
		SendMessage inner = new SendMessage();
		inner.setChatId(chatId);
		inner.setText(text);	
		
		try {
            sendMessageAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
                	}
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
        }	
	}
	
	public void BotSendMessageAsyncEx(String callbackId, String chatId, String text, String parseMode, boolean disableWebPagePreview, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyMarkup ) throws TelegramApiException {
		SendMessage inner = new SendMessage();
		inner.setChatId(chatId);
		inner.setText(text);	
		switch (parseMode) {
			case ABTelegram.PARSEMODE_MARKDOWN:
				inner.enableMarkdown(true);
				break;
			case ABTelegram.PARSEMODE_HTML:
				inner.enableHtml(true);
				break;
		}
		if (disableWebPagePreview) inner.disableWebPagePreview();
		if (disableNotification) inner.disableNotification();
		if (replyToMessageId!=0) {
			inner.setReplayToMessageId(replyToMessageId);
		}
		if (replyMarkup!=null) {
			inner.setReplayMarkup(replyMarkup.inner);
		}
		try {
            sendMessageAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendMessage", callbackId, false, null});
        }	
	}
	
	
	public boolean BotAnswerInlineQuery(String inlineQueryId, anywheresoftware.b4a.objects.collections.List inlineQueryResults) throws TelegramApiException {
		AnswerInlineQuery m = new AnswerInlineQuery();
		m.setInlineQueryId(inlineQueryId);
		List<InlineQueryResult> res = new ArrayList<InlineQueryResult>();
		for (int i=0;i<inlineQueryResults.getSize();i++) {
			ABTInlineQueryResult r = (ABTInlineQueryResult) inlineQueryResults.Get(i);
			res.add(r.inner);
		}
		m.setResults(res);
		return (Boolean) sendApiMethod(m);
	}
	
	public boolean BotAnswerInlineQueryEx(String inlineQueryId, anywheresoftware.b4a.objects.collections.List inlineQueryResults, Integer cacheTime, Boolean isPersonal, String nextOffset, String switchPmText, String switchPmParameter) throws TelegramApiException {
		AnswerInlineQuery m = new AnswerInlineQuery();
		m.setInlineQueryId(inlineQueryId);
		List<InlineQueryResult> res = new ArrayList<InlineQueryResult>();
		for (int i=0;i<inlineQueryResults.getSize();i++) {
			ABTInlineQueryResult r = (ABTInlineQueryResult) inlineQueryResults.Get(i);
			res.add(r.inner);
		}
		m.setResults(res);
		m.setCacheTime(cacheTime);
		m.setPersonal(isPersonal);
		m.setNextOffset(nextOffset);
		m.setSwitchPmText(switchPmText);
		m.setSwitchPmParameter(switchPmParameter);
		return (Boolean) sendApiMethod(m);
	}	
	
	public void BotAnswerInlineQueryAsync(String callbackId, String inlineQueryId, anywheresoftware.b4a.objects.collections.List inlineQueryResults) throws TelegramApiException {
		AnswerInlineQuery m = new AnswerInlineQuery();
		m.setInlineQueryId(inlineQueryId);
		List<InlineQueryResult> res = new ArrayList<InlineQueryResult>();
		for (int i=0;i<inlineQueryResults.getSize();i++) {
			ABTInlineQueryResult r = (ABTInlineQueryResult) inlineQueryResults.Get(i);
			res.add(r.inner);
		}
		m.setResults(res);
		try {
            answerInlineQueryAsync(m, new SentCallback<Boolean>() {
                @Override
                public void onResult(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, true, null});
                    } else {
                    	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, false, null});
                    }
                }

                @Override
                public void onError(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Boolean> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, false, null});
        }
	}
	
	public void BotAnswerInlineQueryAsyncEx(String callbackId, String inlineQueryId, anywheresoftware.b4a.objects.collections.List inlineQueryResults, Integer cacheTime, Boolean isPersonal, String nextOffset, String switchPmText, String switchPmParameter) throws TelegramApiException {
		AnswerInlineQuery m = new AnswerInlineQuery();
		m.setInlineQueryId(inlineQueryId);
		List<InlineQueryResult> res = new ArrayList<InlineQueryResult>();
		for (int i=0;i<inlineQueryResults.getSize();i++) {
			ABTInlineQueryResult r = (ABTInlineQueryResult) inlineQueryResults.Get(i);
			res.add(r.inner);
		}
		m.setResults(res);
		m.setCacheTime(cacheTime);
		m.setPersonal(isPersonal);
		m.setNextOffset(nextOffset);
		m.setSwitchPmText(switchPmText);
		m.setSwitchPmParameter(switchPmParameter);
		try {
            answerInlineQueryAsync(m, new SentCallback<Boolean>() {
                @Override
                public void onResult(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, true, null});
                    } else {
                    	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, false, null});
                    }
                }

                @Override
                public void onError(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Boolean> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerInlineQuery", callbackId, false, null});
        }
	}	
	
	public Boolean BotSendChatAction(String chatId, String chatActionType) throws TelegramApiException {
		SendChatAction m = new SendChatAction();
		m.setChatId(chatId);
		m.setAction(chatActionType);		
		return (Boolean) sendApiMethod(m);
	}	
	
	public void BotSendChatActionAsync(String callbackId, String chatId, String chatActionType) throws TelegramApiException {
		SendChatAction m = new SendChatAction();
		m.setChatId(chatId);
		m.setAction(chatActionType);		
		try {
            sendChatActionAsync(m, new SentCallback<Boolean>() {
                @Override
                public void onResult(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendChatAction", callbackId, true, null});
                    } else {
                    	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendChatAction", callbackId, false, null});
                    }
                }

                @Override
                public void onError(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendChatAction", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Boolean> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendChatAction", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendChatAction", callbackId, false, null});
        }
	}	
	
	public ABTMessage BotForwardMessage(String chatId, String fromChatId, int messageId) throws TelegramApiException {
		ForwardMessage inner = new ForwardMessage();
		inner.setChatId(chatId);
		inner.setFromChatId(fromChatId); //TODO changed to be a string
		inner.setMessageId(messageId);
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotForwardMessageEx(String chatId, String fromChatId, int messageId, boolean disableNotification) throws TelegramApiException {
		ForwardMessage inner = new ForwardMessage();
		inner.setChatId(chatId);
		inner.setFromChatId(fromChatId); //TODO changed to be a string
		inner.setMessageId(messageId);
		if (disableNotification) {
			inner.disableNotification();
		}		
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotForwardMessageAsync(String callbackId, String chatId, String fromChatId, int messageId) throws TelegramApiException {
		ForwardMessage inner = new ForwardMessage();
		inner.setChatId(chatId);
		inner.setFromChatId(fromChatId); //TODO changed to be a string
		inner.setMessageId(messageId);
		try {
            forwardMessageAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
        }	
	}
	
	public void BotForwardMessageAsyncEx(String callbackId, String chatId, String fromChatId, int messageId, boolean disableNotification) throws TelegramApiException {
		ForwardMessage inner = new ForwardMessage();
		inner.setChatId(chatId);
		inner.setFromChatId(fromChatId); //TODO changed to be a string
		inner.setMessageId(messageId);
		if (disableNotification) {
			inner.disableNotification();
		}		
		try {
            forwardMessageAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"ForwardMessage", callbackId, false, null});
        }	
	}
	
	public ABTMessage BotSendLocation(String chatId, float latitude, float longitude) throws TelegramApiException {
		SendLocation inner = new SendLocation();
		inner.setChatId(chatId);
		inner.setLatitude(latitude);
		inner.setLongitude(longitude);
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendLocationEx(String chatId, float latitude, float longitude, boolean disableNotifications, int replyToMessageId, ABTReplyKeyboard replyMarkup) throws TelegramApiException {
		SendLocation inner = new SendLocation();
		inner.setChatId(chatId);
		inner.setLatitude(latitude);
		inner.setLongitude(longitude);
		if (disableNotifications) inner.disableNotification();
		if (replyToMessageId!=0) {
			inner.setReplayToMessageId(replyToMessageId);
		}
		if (replyMarkup!=null) {
			inner.setReplayMarkup(replyMarkup.inner);
		}
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	} 
	
	public void BotSendLocationAsync(String callbackId, String chatId, float latitude, float longitude) throws TelegramApiException {
		SendLocation inner = new SendLocation();
		inner.setChatId(chatId);
		inner.setLatitude(latitude);
		inner.setLongitude(longitude);
		try {
            sendLocationAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
        }	
	}
	
	public void BotSendLocationAsyncEx(String callbackId, String chatId, float latitude, float longitude, boolean disableNotifications, int replyToMessageId, ABTReplyKeyboard replyMarkup) throws TelegramApiException {
		SendLocation inner = new SendLocation();
		inner.setChatId(chatId);
		inner.setLatitude(latitude);
		inner.setLongitude(longitude);
		if (disableNotifications) inner.disableNotification();
		if (replyToMessageId!=0) {
			inner.setReplayToMessageId(replyToMessageId);
		}
		if (replyMarkup!=null) {
			inner.setReplayMarkup(replyMarkup.inner);
		}
		try {
            sendLocationAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendLocation", callbackId, false, null});
        }	
	} 
	
	public ABTMessage BotSendVenue(String chatId, float latitude, float longitude, String title, String address) throws TelegramApiException {
		SendVenue inner = new SendVenue();
		inner.setChatId(chatId);
		inner.setLatitude(latitude);
		inner.setLongitude(longitude);
		inner.setTitle(title);
		inner.setAddress(address);
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendVenueEx(String chatId, float latitude, float longitude, String title, String address, String foursquareId, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyMarkup) throws TelegramApiException {
		SendVenue inner = new SendVenue();
		inner.setChatId(chatId);
		inner.setLatitude(latitude);
		inner.setLongitude(longitude);
		inner.setTitle(title);
		inner.setAddress(address);
		inner.setFoursquareId(foursquareId);
		inner.setReplayToMessageId(replyToMessageId);		
		if (disableNotification) inner.disableNotification();
		if (replyToMessageId!=0) {
			inner.setReplayToMessageId(replyToMessageId);
		}
		if (replyMarkup!=null) {
			inner.setReplayMarkup(replyMarkup.inner);
		}
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}

	public void BotSendVenueAsync(String callbackId, String chatId, float latitude, float longitude, String title, String address) throws TelegramApiException {
		SendVenue inner = new SendVenue();
		inner.setChatId(chatId);
		inner.setLatitude(latitude);
		inner.setLongitude(longitude);
		inner.setTitle(title);
		inner.setAddress(address);
		try {
            sendVenueAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
        }	
	}
	
	public void BotSendVenueAsyncEx(String callbackId, String chatId, float latitude, float longitude, String title, String address, String foursquareId, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyMarkup) throws TelegramApiException {
		SendVenue inner = new SendVenue();
		inner.setChatId(chatId);
		inner.setLatitude(latitude);
		inner.setLongitude(longitude);
		inner.setTitle(title);
		inner.setAddress(address);
		inner.setFoursquareId(foursquareId);
		inner.setReplayToMessageId(replyToMessageId);		
		if (disableNotification) inner.disableNotification();
		if (replyToMessageId!=0) {
			inner.setReplayToMessageId(replyToMessageId);
		}
		if (replyMarkup!=null) {
			inner.setReplayMarkup(replyMarkup.inner);
		}
		try {
            sendVenueAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVenue", callbackId, false, null});
        }	
	}

	
	public ABTMessage BotSendContact(String chatId, String phoneNumber, String firstName) throws TelegramApiException {
		SendContact inner = new SendContact();
		inner.setChatId(chatId);
		inner.setPhoneNumber(phoneNumber);
		inner.setFirstName(firstName);
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendContactEx(String chatId, String phoneNumber, String firstName, String lastName, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyMarkup) throws TelegramApiException {
		SendContact inner = new SendContact();
		inner.setChatId(chatId);
		inner.setPhoneNumber(phoneNumber);
		inner.setFirstName(firstName);
		inner.setLastName(lastName);
		inner.setReplayToMessageId(replyToMessageId);		
		if (disableNotification) inner.disableNotification();
		if (replyToMessageId!=0) {
			inner.setReplayToMessageId(replyToMessageId);
		}
		if (replyMarkup!=null) {
			inner.setReplayMarkup(replyMarkup.inner);
		}
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotSendContactAsync(String callbackId, String chatId, String phoneNumber, String firstName) throws TelegramApiException {
		SendContact inner = new SendContact();
		inner.setChatId(chatId);
		inner.setPhoneNumber(phoneNumber);
		inner.setFirstName(firstName);
		try {
            sendContactAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
        }	
	}
	
	public void BotSendContactAsyncEx(String callbackId, String chatId, String phoneNumber, String firstName, String lastName, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyMarkup) throws TelegramApiException {
		SendContact inner = new SendContact();
		inner.setChatId(chatId);
		inner.setPhoneNumber(phoneNumber);
		inner.setFirstName(firstName);
		inner.setLastName(lastName);
		inner.setReplayToMessageId(replyToMessageId);		
		if (disableNotification) inner.disableNotification();
		if (replyToMessageId!=0) {
			inner.setReplayToMessageId(replyToMessageId);
		}
		if (replyMarkup!=null) {
			inner.setReplayMarkup(replyMarkup.inner);
		}
		try {
            sendContactAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendContact", callbackId, false, null});
        }	
	}
	
	public Boolean BotKickMember(String chatId, int userId) throws TelegramApiException {
		KickChatMember inner = new KickChatMember();
		inner.setChatId(chatId);
		inner.setUserId(userId);
		return (Boolean) sendApiMethod(inner);
	}
	
	public void BotKickMemberAsync(String callbackId, String chatId, int userId) throws TelegramApiException {
		KickChatMember inner = new KickChatMember();
		inner.setChatId(chatId);
		inner.setUserId(userId);
		try {
            kickMemberAsync(inner, new SentCallback<Boolean>() {
                @Override
                public void onResult(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"KickMember", callbackId, true, null});
                    } else {
                    	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"KickMember", callbackId, false, null});
                    }
                }

                @Override
                public void onError(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"KickMember", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Boolean> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"KickMember", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"KickMember", callbackId, false, null});
        }
	}
	
	public Boolean BotUnbanMember(String chatId, int userId) throws TelegramApiException {
		UnbanChatMember inner = new UnbanChatMember();
		inner.setChatId(chatId);
		inner.setUserId(userId);
		return (Boolean) sendApiMethod(inner);		
	}
	
	public void BotUnbanMemberAsync(String callbackId, String chatId, int userId) throws TelegramApiException {
		UnbanChatMember inner = new UnbanChatMember();
		inner.setChatId(chatId);
		inner.setUserId(userId);
		try {
            unbanMemberAsync(inner, new SentCallback<Boolean>() {
                @Override
                public void onResult(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"UnbanMember", callbackId, true, null});
                    } else {
                    	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"UnbanMember", callbackId, false, null});
                    }
                }

                @Override
                public void onError(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"UnbanMember", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Boolean> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"UnbanMember", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"UnbanMember", callbackId, false, null});
        }		
	}
	
	public ABTMessage BotEditMessageText(String chatId, int messageId, String inlineMessageId, String text) throws TelegramApiException {
		EditMessageText inner = new EditMessageText();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		inner.setText(text);
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotEditMessageTextEx(String chatId, int messageId, String inlineMessageId, String text, String parseMode, boolean disableWebViewPreview, ABTReplyKeyboard inlineKeyboardMarkup) throws TelegramApiException {
		EditMessageText inner = new EditMessageText();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		inner.setText(text);
		if (disableWebViewPreview) {
			inner.disableWebPagePreview();
		}
		switch (parseMode) {
		case ABTelegram.PARSEMODE_MARKDOWN:
			inner.enableMarkdown(true);
			break;
		case ABTelegram.PARSEMODE_HTML:
			inner.enableHtml(true);
			break;
		}
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup ikm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				inner.setReplyMarkup(ikm);
			}
		}
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotEditMessageTextAsync(String callbackId, String chatId, int messageId, String inlineMessageId, String text) throws TelegramApiException {
		EditMessageText inner = new EditMessageText();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		inner.setText(text);
		try {
            editMessageTextAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
        }	
	}
	
	public void BotEditMessageTextAsyncEx(String callbackId, String chatId, int messageId, String inlineMessageId, String text, String parseMode, boolean disableWebViewPreview, ABTReplyKeyboard inlineKeyboardMarkup) throws TelegramApiException {
		EditMessageText inner = new EditMessageText();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		inner.setText(text);
		if (disableWebViewPreview) {
			inner.disableWebPagePreview();
		}
		switch (parseMode) {
		case ABTelegram.PARSEMODE_MARKDOWN:
			inner.enableMarkdown(true);
			break;
		case ABTelegram.PARSEMODE_HTML:
			inner.enableHtml(true);
			break;
		}
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup ikm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				inner.setReplyMarkup(ikm);
			}
		}
		try {
            editMessageTextAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageText", callbackId, false, null});
        }	
	}
	
	public ABTMessage BotEditMessageCaption(String chatId, int messageId, String inlineMessageId) throws TelegramApiException {
		EditMessageCaption inner = new EditMessageCaption();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotEditMessageCaptionEx(String chatId, int messageId, String inlineMessageId, String caption, ABTReplyKeyboard inlineKeyboardMarkup) throws TelegramApiException {
		EditMessageCaption inner = new EditMessageCaption();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		inner.setCaption(caption);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup ikm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				inner.setReplyMarkup(ikm);
			}
		}
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotEditMessageCaptionAsync(String callbackId, String chatId, int messageId, String inlineMessageId) throws TelegramApiException {
		EditMessageCaption inner = new EditMessageCaption();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		try {
            editMessageCaptionAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
        }	
	}
	
	public void BotEditMessageCaptionAsyncEx(String callbackId, String chatId, int messageId, String inlineMessageId, String caption, ABTReplyKeyboard inlineKeyboardMarkup) throws TelegramApiException {
		EditMessageCaption inner = new EditMessageCaption();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		inner.setCaption(caption);
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup ikm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				inner.setReplyMarkup(ikm);
			}
		}
		try {
            editMessageCaptionAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageCaption", callbackId, false, null});
        }	
	}
	
	public ABTMessage BotEditMessageReplyMarkup(String chatId, int messageId, String inlineMessageId) throws TelegramApiException {
		EditMessageReplyMarkup inner = new EditMessageReplyMarkup();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotEditMessageReplyMarkupEx(String chatId, int messageId, String inlineMessageId, ABTReplyKeyboard inlineKeyboardMarkup) throws TelegramApiException {
		EditMessageReplyMarkup inner = new EditMessageReplyMarkup();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				BA.Log("Changing ReplyMarkup");
				InlineKeyboardMarkup ikm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				inner.setReplyMarkup(ikm);
			}
		}
		Message m = (Message) sendApiMethod(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotEditMessageReplyMarkupAsync(String callbackId, String chatId, int messageId, String inlineMessageId) throws TelegramApiException {
		EditMessageReplyMarkup inner = new EditMessageReplyMarkup();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		try {
            editMessageReplyMarkupAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
        }	
	}
	
	public void BotEditMessageReplyMarkupAsyncEx(String callbackId, String chatId, int messageId, String inlineMessageId, ABTReplyKeyboard inlineKeyboardMarkup) throws TelegramApiException {
		EditMessageReplyMarkup inner = new EditMessageReplyMarkup();
		if (inlineMessageId.equals("")) {
			inner.setChatId(chatId);
			inner.setMessageId(messageId);
		} else {
			inner.setInlineMessageId(inlineMessageId);
		}
		if (inlineKeyboardMarkup!=null) {
			if (inlineKeyboardMarkup.ObjectType.equals("ABTInlineKeyboardMarkup")) {
				InlineKeyboardMarkup ikm = (InlineKeyboardMarkup)inlineKeyboardMarkup.inner;
				inner.setReplyMarkup(ikm);
			}
		}
		try {
            editMessageReplyMarkupAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"EditMessageReplyMarkup", callbackId, false, null});
        }	
	}
	
	public boolean BotAnswerCallbackQuery(String callbackQueryId) throws TelegramApiException {
		AnswerCallbackQuery inner = new AnswerCallbackQuery();
		inner.setCallbackQueryId(callbackQueryId);
		return (boolean) sendApiMethod(inner);		
	}
	
	public Boolean BotAnswerCallbackQueryEx(String callbackQueryId, String text, boolean showAlert) throws TelegramApiException {
		AnswerCallbackQuery inner = new AnswerCallbackQuery();
		inner.setCallbackQueryId(callbackQueryId);
		inner.setText(text);
		inner.setShowAlert(showAlert);
		return (boolean)sendApiMethod(inner);
	}
	
	public void BotAnswerCallbackQueryAsync(String callbackId, String callbackQueryId) throws TelegramApiException {
		AnswerCallbackQuery inner = new AnswerCallbackQuery();
		inner.setCallbackQueryId(callbackQueryId);
		try {
            answerCallbackQueryAsync(inner, new SentCallback<Boolean>() {
                @Override
                public void onResult(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, true, null});
                    } else {
                    	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, false, null});
                    }
                }

                @Override
                public void onError(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Boolean> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, false, null});
        }
	}
	
	public void BotAnswerCallbackQueryAsyncEx(String callbackId, String callbackQueryId, String text, boolean showAlert) throws TelegramApiException {
		AnswerCallbackQuery inner = new AnswerCallbackQuery();
		inner.setCallbackQueryId(callbackQueryId);
		inner.setText(text);
		inner.setShowAlert(showAlert);
		try {
            answerCallbackQueryAsync(inner, new SentCallback<Boolean>() {
                @Override
                public void onResult(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, true, null});
                    } else {
                    	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, false, null});
                    }
                }

                @Override
                public void onError(BotApiMethod<Boolean> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Boolean> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"AnswerCallbackQuery", callbackId, false, null});
        }
	}
	
	public ABTUserProfilePhotos BotGetUserProfilePhotos(int userId) throws TelegramApiException {
		GetUserProfilePhotos inner = new GetUserProfilePhotos();
		inner.setUserId(userId);
		UserProfilePhotos m = (UserProfilePhotos) sendApiMethod(inner);
		ABTUserProfilePhotos ret = new ABTUserProfilePhotos();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTUserProfilePhotos BotGetUserProfilePhotosEx(int userId, int offset, int limit) throws TelegramApiException {
		GetUserProfilePhotos inner = new GetUserProfilePhotos();
		inner.setUserId(userId);
		inner.setOffset(offset);
		inner.setLimit(limit);
		UserProfilePhotos m = (UserProfilePhotos) sendApiMethod(inner);
		ABTUserProfilePhotos ret = new ABTUserProfilePhotos();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotGetUserProfilePhotosAsync(String callbackId, int userId) throws TelegramApiException {
		GetUserProfilePhotos inner = new GetUserProfilePhotos();
		inner.setUserId(userId);
		try {
            getUserProfilePhotosAsync(inner, new SentCallback<UserProfilePhotos>() {
                @Override
                public void onResult(BotApiMethod<UserProfilePhotos> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		UserProfilePhotos uPhotos = botApiMethod.deserializeResponse(jsonObject);
                		if (uPhotos != null) {
                			ABTUserProfilePhotos ret = new ABTUserProfilePhotos();
                			ret.innerSet(uPhotos);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<UserProfilePhotos> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<UserProfilePhotos> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
        }			
	}
	
	public void BotGetUserProfilePhotosAsyncEx(String callbackId, int userId, int offset, int limit) throws TelegramApiException {
		GetUserProfilePhotos inner = new GetUserProfilePhotos();
		inner.setUserId(userId);
		inner.setOffset(offset);
		inner.setLimit(limit);
		try {
            getUserProfilePhotosAsync(inner, new SentCallback<UserProfilePhotos>() {
                @Override
                public void onResult(BotApiMethod<UserProfilePhotos> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		UserProfilePhotos uPhotos = botApiMethod.deserializeResponse(jsonObject);
                		if (uPhotos != null) {
                			ABTUserProfilePhotos ret = new ABTUserProfilePhotos();
                			ret.innerSet(uPhotos);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<UserProfilePhotos> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<UserProfilePhotos> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetUserProfilePhotos", callbackId, false, null});
        }	
	}
	
	public ABTFile BotGetFile(String fileId) throws TelegramApiException {
		GetFile inner = new GetFile();
		inner.setFileId(fileId);
		File m = (File) sendApiMethod(inner);
		ABTFile ret = new ABTFile();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotGetFileAsync(String callbackId, String fileId) throws TelegramApiException {
		GetFile inner = new GetFile();
		inner.setFileId(fileId);
		try {
            getFileAsync(inner, new SentCallback<File>() {
                @Override
                public void onResult(BotApiMethod<File> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		File fil = botApiMethod.deserializeResponse(jsonObject);
                		if (fil != null) {
                			ABTFile ret = new ABTFile();
                			ret.innerSet(fil);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetFile", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetFile", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetFile", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<File> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetFile", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<File> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetFile", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetFile", callbackId, false, null});
        }			
	}
	
	public ABTUser BotGetMe() throws TelegramApiException {
		GetMe inner = new GetMe();
		User m = (User) sendApiMethod(inner);
		ABTUser ret = new ABTUser();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotGetMeAsync(String callbackId) throws TelegramApiException {
		try {
            getMeAsync(new SentCallback<User>() {
                @Override
                public void onResult(BotApiMethod<User> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		User us = botApiMethod.deserializeResponse(jsonObject);
                		if (us != null) {
                			ABTUser ret = new ABTUser();
                			ret.innerSet(us);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetMe", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetMe", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetMe", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<User> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetMe", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<User> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetMe", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"GetMe", callbackId, false, null});
        }	
	}
	
	public ABTMessage BotSendDocument(boolean isNew, String chatId, String document, String documentName) throws TelegramApiException, JSONException {
		SendDocument inner = new SendDocument();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setDocument(document);
		} else {
			inner.setNewDocument(document, documentName);
		}
		Message m = sendDocument(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendDocumentEx(boolean isNew, String chatId, String document, String documentName, String caption, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendDocument inner = new SendDocument();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setDocument(document);
		} else {
			inner.setNewDocument(document, documentName);
		}
		inner.setCaption(caption);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		Message m = sendDocument(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotSendDocumentAsync(String callbackId, boolean isNew, String chatId, String document, String documentName) throws TelegramApiException, JSONException {
		SendDocument inner = new SendDocument();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setDocument(document);
		} else {
			inner.setNewDocument(document, documentName);
		}
		try {
            sendDocumentAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
        }		
	}
	
	public void BotSendDocumentAsyncEx(String callbackId, boolean isNew, String chatId, String document, String documentName, String caption, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendDocument inner = new SendDocument();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setDocument(document);
		} else {
			inner.setNewDocument(document, documentName);
		}
		inner.setCaption(caption);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		try {
            sendDocumentAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendDocument", callbackId, false, null});
        }	
	}
	
	public ABTMessage BotSendPhoto(boolean isNew, String chatId, String photo) throws TelegramApiException, JSONException {
		SendPhoto inner = new SendPhoto();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setPhoto(photo);
		} else {
			if (photo.toLowerCase().endsWith(".jpg")) {
				inner.setNewPhoto(photo, "file.jpg");
			}
			if (photo.toLowerCase().endsWith(".png")) {
				inner.setNewPhoto(photo, "file.png");
			}
		}
		Message m = sendPhoto(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendPhotoEx(boolean isNew, String chatId, String photo, String caption, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendPhoto inner = new SendPhoto();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setPhoto(photo);
		} else {
			if (photo.toLowerCase().endsWith(".jpg")) {
				inner.setNewPhoto(photo, "file.jpg");
			}
			if (photo.toLowerCase().endsWith(".png")) {
				inner.setNewPhoto(photo, "file.png");
			}
		}
		inner.setCaption(caption);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		Message m = sendPhoto(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotSendPhotoAsync(String callbackId, boolean isNew, String chatId, String photo) throws TelegramApiException, JSONException {
		SendPhoto inner = new SendPhoto();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setPhoto(photo);
		} else {
			if (photo.toLowerCase().endsWith(".jpg")) {
				inner.setNewPhoto(photo, "file.jpg");
			}
			if (photo.toLowerCase().endsWith(".png")) {
				inner.setNewPhoto(photo, "file.png");
			}
		}
		try {
            sendPhotoAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
        }	
	}
	
	public void BotSendPhotoAsyncEx(String callbackId, boolean isNew, String chatId, String photo, String caption, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendPhoto inner = new SendPhoto();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setPhoto(photo);
		} else {
			if (photo.toLowerCase().endsWith(".jpg")) {
				inner.setNewPhoto(photo, "file.jpg");
			}
			if (photo.toLowerCase().endsWith(".png")) {
				inner.setNewPhoto(photo, "file.png");
			}
		}
		inner.setCaption(caption);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		try {
            sendPhotoAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendPhoto", callbackId, false, null});
        }	
	}
		
	public ABTMessage BotSendVideo(boolean isNew, String chatId, String video) throws TelegramApiException, JSONException {
		SendVideo inner = new SendVideo();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setVideo(video);
		} else {
			inner.setNewVideo(video, "file.mp4");
		}
		Message m = sendVideo(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendVideoEx(boolean isNew, String chatId, String video, int duration, int width, int height, String caption, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendVideo inner = new SendVideo();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setVideo(video);
		} else {
			inner.setNewVideo(video, "file.mp4");
		}
		inner.setDuration(duration);
		inner.setWidth(width);
		inner.setHeight(height);
		inner.setCaption(caption);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		Message m = sendVideo(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotSendVideoAsync(String callbackId, boolean isNew, String chatId, String video) throws TelegramApiException, JSONException {
		SendVideo inner = new SendVideo();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setVideo(video);
		} else {
			inner.setNewVideo(video, "file.mp4");
		}
		try {
            sendVideoAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
        }	
	}
	
	public void BotSendVideoAsyncEx(String callbackId, boolean isNew, String chatId, String video, int duration, int width, int height, String caption, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendVideo inner = new SendVideo();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setVideo(video);
		} else {
			inner.setNewVideo(video, "file.mp4");
		}
		inner.setDuration(duration);
		inner.setWidth(width);
		inner.setHeight(height);
		inner.setCaption(caption);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		try {
            sendVideoAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVideo", callbackId, false, null});
        }
	}

	public ABTMessage BotSendSticker(boolean isNew, String chatId, String sticker) throws TelegramApiException, JSONException {
		SendSticker inner = new SendSticker();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setSticker(sticker);
		} else {
			inner.setNewSticker(sticker, "file.webp");
		}
		Message m = sendSticker(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendStickerEx(boolean isNew, String chatId, String sticker, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendSticker inner = new SendSticker();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setSticker(sticker);
		} else {
			inner.setNewSticker(sticker, "file.webp");
		}
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		Message m = sendSticker(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotSendStickerAsync(String callbackId, boolean isNew, String chatId, String sticker) throws TelegramApiException, JSONException {
		SendSticker inner = new SendSticker();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setSticker(sticker);
		} else {
			inner.setNewSticker(sticker, "file.webp");
		}
		try {
            sendStickerAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
        }
	}
	
	public void BotSendStickerAsyncEx(String callbackId, boolean isNew, String chatId, String sticker, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendSticker inner = new SendSticker();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setSticker(sticker);
		} else {
			inner.setNewSticker(sticker, "file.webp");
		}
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		try {
            sendStickerAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendSticker", callbackId, false, null});
        }
	}

	public ABTMessage BotSendAudio(boolean isNew, String chatId, String audio) throws TelegramApiException, JSONException {
		SendAudio inner = new SendAudio();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setAudio(audio);
		} else {
			inner.setNewAudio(audio, "file.mp3");
		}
		Message m = sendAudio(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendAudioEx(boolean isNew, String chatId, String audio, int duration, String performer, String title, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendAudio inner = new SendAudio();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setAudio(audio);
		} else {
			inner.setNewAudio(audio, "file.mp3");
		}
		inner.setDuration(duration);
		inner.setPerformer(performer);
		inner.setTitle(title);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		Message m = sendAudio(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotSendAudio(String callbackId, boolean isNew, String chatId, String audio) throws TelegramApiException, JSONException {
		SendAudio inner = new SendAudio();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setAudio(audio);
		} else {
			inner.setNewAudio(audio, "file.mp3");
		}
		try {
            sendAudioAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
        }
	}
	
	public void BotSendAudioEx(String callbackId, boolean isNew, String chatId, String audio, int duration, String performer, String title, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendAudio inner = new SendAudio();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setAudio(audio);
		} else {
			inner.setNewAudio(audio, "file.mp3");
		}
		inner.setDuration(duration);
		inner.setPerformer(performer);
		inner.setTitle(title);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		try {
            sendAudioAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendAudio", callbackId, false, null});
        }
	}
	
	public ABTMessage BotSendVoice(boolean isNew, String chatId, String voice) throws TelegramApiException, JSONException {
		SendVoice inner = new SendVoice();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setVoice(voice);
		} else {
			inner.setNewVoice(voice, "file.ogg");
		}
		Message m = sendVoice(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public ABTMessage BotSendVoiceEx(boolean isNew, String chatId, String voice, int duration, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendVoice inner = new SendVoice();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setVoice(voice);
		} else {
			inner.setNewVoice(voice, "file.ogg");
		}
		inner.setDuration(duration);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		Message m = sendVoice(inner);
		ABTMessage ret = new ABTMessage();
		ret.innerSet(m);
		return ret;
	}
	
	public void BotSendVoiceAsync(String callbackId, boolean isNew, String chatId, String voice) throws TelegramApiException, JSONException {
		SendVoice inner = new SendVoice();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setVoice(voice);
		} else {
			inner.setNewVoice(voice, "file.ogg");
		}
		try {
            sendVoiceAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
        }
	}
	
	public void BotSendVoiceAsyncEx(String callbackId, boolean isNew, String chatId, String voice, int duration, boolean disableNotification, int replyToMessageId, ABTReplyKeyboard replyKeyboard) throws TelegramApiException, JSONException {
		SendVoice inner = new SendVoice();
		inner.setChatId(chatId);
		if (!isNew) {
			inner.setVoice(voice);
		} else {
			inner.setNewVoice(voice, "file.ogg");
		}
		inner.setDuration(duration);
		if (disableNotification) {
			inner.disableNotification();
		}
		inner.setReplayToMessageId(replyToMessageId);
		if (replyKeyboard!=null) {
				inner.setReplayMarkup(replyKeyboard.inner);
		
		}
		try {
            sendVoiceAsync(inner, new SentCallback<Message>() {
                @Override
                public void onResult(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	if (jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                		Message sentMessage = botApiMethod.deserializeResponse(jsonObject);
                		if (sentMessage != null) {
                			ABTMessage ret = new ABTMessage();
                			ret.innerSet(sentMessage);
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, true, ret});
                		} else {
                			_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
                		}
                	} else {
                		_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
                	}
                    
                }

                @Override
                public void onError(BotApiMethod<Message> botApiMethod, JSONObject jsonObject) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
                }

                @Override
                public void onException(BotApiMethod<Message> botApiMethod, Exception e) {
                	_ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
                }
            });
        } catch (TelegramApiException e) {
            BA.Log("ERROR: " + e.getMessage());
            _ba.raiseEvent(caller, _event + "_asyncsendreceived", new Object[] {"SendVoice", callbackId, false, null});
        }
	}
	
	
	public anywheresoftware.b4a.objects.collections.List BotGetUpdates() throws TelegramApiException {
		GetUpdates inner = new GetUpdates();
		return GetUpdatesInner(inner);
	}
	
	public anywheresoftware.b4a.objects.collections.List BotGetUpdatesEx(int offset, int limit, int timeout) throws TelegramApiException {
		GetUpdates inner = new GetUpdates();		
		inner.setLimit(limit);
		inner.setOffset(offset);
		inner.setTimeout(timeout);
		return GetUpdatesInner(inner);
	}	
	
	protected anywheresoftware.b4a.objects.collections.List GetUpdatesInner(GetUpdates inner) throws TelegramApiException {
		anywheresoftware.b4a.objects.collections.List rets = new anywheresoftware.b4a.objects.collections.List();
		rets.Initialize();
		 String responseContent;
	        try {
	            CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
	            String url = getBaseUrl() + inner.getPath();
	            HttpPost httppost = new HttpPost(url);
	            httppost.addHeader("charset", StandardCharsets.UTF_8.name());
	            httppost.setEntity(new StringEntity(inner.toJson().toString(), ContentType.APPLICATION_JSON));
	            CloseableHttpResponse response = httpclient.execute(httppost);
	            HttpEntity ht = response.getEntity();
	            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
	            responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
	            JSONObject jsonObject = new JSONObject(responseContent);
	            if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
	            	BA.Log(jsonObject.toString(4));
	                //throw new TelegramApiException("Error at " + method.getPath(), "");
	            	return rets;
	            }
	            //BA.Log(jsonObject.toString(4));
	            JSONArray upds = jsonObject.getJSONArray("result");
	            for (int j=0;j<upds.length();j++) {
	            	JSONObject upd = upds.getJSONObject(j);
	            	ABTUpdate u = new ABTUpdate();
	            	u.Initialize();
	            	u.innerSet(new Update(upd));
	            	rets.Add(u);
	            }
	            //return method.deserializeResponse(jsonObject);
	        } catch (IOException e) {
	        	BA.Log("Unable to execute " + inner.getPath() + " " + e);
	        	return null;
	        } catch (UnsupportedCharsetException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	        return rets;
	}
	
	@SuppressWarnings("rawtypes")
	protected void sendApiMethodAsync(BotApiMethod method, SentCallback callback) {
        exe.submit(new Runnable() {
            @SuppressWarnings("unchecked")
			@Override
            public void run() {
                try {
                    CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
                    String url = getBaseUrl() + method.getPath();
                    HttpPost httppost = new HttpPost(url);
                    httppost.addHeader("charset", StandardCharsets.UTF_8.name());
                    httppost.setEntity(new StringEntity(method.toJson().toString(), ContentType.APPLICATION_JSON));
                    CloseableHttpResponse response = httpclient.execute(httppost);
                    HttpEntity ht = response.getEntity();
                    BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                    String responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);

                    JSONObject jsonObject = new JSONObject(responseContent);
                    if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
                        callback.onError(method, jsonObject);
                    }
                    callback.onResult(method, jsonObject);
                } catch (IOException e) {
                    callback.onException(method, e);
                } catch (JSONException e) {
					e.printStackTrace();
				}

            }
        });
    }

    @SuppressWarnings("rawtypes")
	protected Serializable sendApiMethod(BotApiMethod method) throws TelegramApiException {
        String responseContent;
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            String url = getBaseUrl() + method.getPath();
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("charset", StandardCharsets.UTF_8.name());
            httppost.setEntity(new StringEntity(method.toJson().toString(), ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity ht = response.getEntity();
            BufferedHttpEntity buf = new BufferedHttpEntity(ht);
            responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(responseContent);
            if (!jsonObject.getBoolean(Constants.RESPONSEFIELDOK)) {
            	BA.Log(jsonObject.toString(4));
                //throw new TelegramApiException("Error at " + method.getPath(), "");
            	return null;
            }

            return method.deserializeResponse(jsonObject);
        } catch (IOException e) {
            //throw new TelegramApiException("Unable to execute " + method.getPath() + " method", e);
        	BA.Log("Unable to execute " + method.getPath() + " method " + e);
        	return null;
        } catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return null;
        
    }

    private String getBaseUrl() {
        return Constants.BASEURL + getBotToken() + "/";
    }
}
