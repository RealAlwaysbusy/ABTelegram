package org.telegram.telegrambots.api.methods.send;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;





import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;


/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Use this method to send voice notes, if you want Telegram clients to display the file as a
 * playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS
 * (other formats may be sent as Audio or Document).
 * @date 16 of July of 2015
 */
import anywheresoftware.b4a.BA.Hide;
@Hide
public class SendVoice extends BotApiMethod<Message> {
    public static final String PATH = "sendvoice";

    public static final String CHATID_FIELD = "chat_id";
    public static final String VOICE_FIELD = "voice";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String DURATION_FIELD = "duration";
    private String chatId; ///< Unique identifier for the chat sent message to (Or username for channels)
    private String voice; ///< Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new audio file using multipart/form-data.
    /**
     * Optional. Sends the message silently. iOS users will not receive a notification, Android
     * users will receive a notification with no sound. Other apps coming soon
     */
    private Boolean disableNotification;
    private Integer replayToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replayMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private Integer duration; ///< Optional. Duration of sent audio in seconds

    private boolean isNewVoice; ///< True to upload a new voice note, false to use a fileId
    private String voiceName; ///< Name of the voice note

    public SendVoice() {
        super();
    }

    @Override
    public String toString() {
        return "SendVoice{" +
                "chatId='" + chatId + '\'' +
                ", voice='" + voice + '\'' +
                ", replayToMessageId=" + replayToMessageId +
                ", replayMarkup=" + replayMarkup +
                ", duration=" + duration +
                '}';
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public SendVoice enableNotification() {
        this.disableNotification = false;
        return this;
    }

    public SendVoice disableNotification() {
        this.disableNotification = true;
        return this;
    }

    public String getChatId() {
        return chatId;
    }

    public SendVoice setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public String getVoice() {
        return voice;
    }

    public SendVoice setVoice(String voice) {
        this.voice = voice;
        this.isNewVoice = false;
        return this;
    }

    public SendVoice setNewVoice(String voice, String audioName) {
        this.voice = voice;
        this.isNewVoice = true;
        this.voiceName = audioName;
        return this;
    }

    public Integer getReplayToMessageId() {
        return replayToMessageId;
    }

    public SendVoice setReplayToMessageId(Integer replayToMessageId) {
        this.replayToMessageId = replayToMessageId;
        return this;
    }

    public ReplyKeyboard getReplayMarkup() {
        return replayMarkup;
    }

    public SendVoice setReplayMarkup(ReplyKeyboard replayMarkup) {
        this.replayMarkup = replayMarkup;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public SendVoice setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public boolean isNewVoice() {
        return isNewVoice;
    }

    public String getVoiceName() {
        return voiceName;
    }

	@Override
	public void serialize(JsonGenerator arg0, SerializerProvider arg1)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1,
			TypeSerializer arg2) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject toJson() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message deserializeResponse(JSONObject answer) throws JSONException {
		if (answer.getBoolean(Constants.RESPONSEFIELDOK)) {
	         return new Message(answer.getJSONObject(Constants.RESPONSEFIELDRESULT));
	     }
	     return null;
	}
}
