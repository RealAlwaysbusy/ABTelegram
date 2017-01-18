package org.telegram.telegrambots.api.objects.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;



/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Upon receiving a message with this object, Telegram clients will display a reply interface
 * to the user (act as if the user has selected the bot‘s message and tapped ’Reply'). This can be
 * extremely useful if you want to create user-friendly step-by-step interfaces without having to
 * sacrifice privacy mode.
 * @date 22 of June of 2015
 */
import anywheresoftware.b4a.BA.Hide;
@Hide
public class ForceReplyKeyboard implements ReplyKeyboard {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2896999351777146936L;
	private static final String FORCEREPLY_FIELD = "force_reply";
    private static final String SELECTIVE_FIELD = "selective";
    /**
     * Shows reply interface to the user, as if they manually selected the bot‘s message and tapped
     * ’Reply'
     */
    @JsonProperty(FORCEREPLY_FIELD)
    private Boolean forceReply;
    /**
     * Use this parameter if you want to force reply from specific users only. Targets: 1) users
     * that are @mentioned in the text of the Message object; 2) if the bot's message is a reply
     * (has reply_to_message_id), sender of the original message.
     */
    @JsonProperty(SELECTIVE_FIELD)
    private Boolean selective;

    public ForceReplyKeyboard() {
        super();
        this.forceReply = true;
    }

    public ForceReplyKeyboard(JSONObject jsonObject) throws JSONException {
        super();
        if (jsonObject.has(FORCEREPLY_FIELD)) {
            this.forceReply = jsonObject.getBoolean(FORCEREPLY_FIELD);
        }
        if (jsonObject.has(SELECTIVE_FIELD)) {
            this.selective = jsonObject.getBoolean(SELECTIVE_FIELD);
        }
    }

    public Boolean getForceReply() {
        return forceReply;
    }

    public ForceReplyKeyboard setForceReply(Boolean forceReply) {
        this.forceReply = forceReply;
        return this;
    }

    public Boolean getSelective() {
        return selective;
    }

    public ForceReplyKeyboard setSelective(Boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(FORCEREPLY_FIELD, this.forceReply);
        if (this.selective != null) {
            jsonObject.put(SELECTIVE_FIELD, this.selective);
        }

        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeBooleanField(FORCEREPLY_FIELD, forceReply);
        gen.writeBooleanField(SELECTIVE_FIELD, selective);
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "ForceReplyKeyboard{" +
                "forceReply=" + forceReply +
                ", selective=" + selective +
                '}';
    }
}
