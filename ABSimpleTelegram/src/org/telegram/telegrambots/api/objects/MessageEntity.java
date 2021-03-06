package org.telegram.telegrambots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.api.interfaces.IBotApiObject;

import java.io.IOException;



/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief This object represents one special entity in a text message. For example, hashtags,
 * usernames, URL.
 * @date 20 of June of 2015
 */
import anywheresoftware.b4a.BA.Hide;
@Hide
public class MessageEntity implements IBotApiObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4456151450082899723L;
	private static final String TYPE_FIELD = "type";
    private static final String OFFSET_FIELD = "offset";
    private static final String LENGTH_FIELD = "length";
    private static final String URL_FIELD = "url";
    @JsonProperty(TYPE_FIELD)
    /**
     * Type of the entity. One of
     * mention (@username),
     * hashtag,
     * bot_command,
     * url,
     * email,
     * bold (bold text),
     * italic (italic text),
     * code (monowidth string),
     * pre (monowidth block),
     * text_link (for clickable text URLs)
     */
    private String type;
    @JsonProperty(OFFSET_FIELD)
    private Integer offset; ///< Offset in UTF-16 code units to the start of the entity
    @JsonProperty(LENGTH_FIELD)
    private Integer length; ///< Length of the entity in UTF-16 code units
    @JsonProperty(URL_FIELD)
    private String url; ///< Optional. For text_link only, url that will be opened after user taps on the text

    public MessageEntity() {
        super();
    }

    public MessageEntity(JSONObject jsonObject) throws JSONException {
        super();
        this.type = jsonObject.getString(TYPE_FIELD);
        this.offset = jsonObject.getInt(OFFSET_FIELD);
        this.length = jsonObject.getInt(LENGTH_FIELD);
        if (EntityType.TEXTLINK.equals(type)) {
            this.url = jsonObject.getString(URL_FIELD);
        }
    }

    public String getType() {
        return type;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLength() {
        return length;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TYPE_FIELD, type);
        gen.writeNumberField(OFFSET_FIELD, offset);
        gen.writeNumberField(LENGTH_FIELD, length);
        if (url != null && EntityType.TEXTLINK.equals(type)) {
            gen.writeStringField(URL_FIELD, url);
        }
        gen.writeEndObject();
        gen.flush();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    @Override
    public String toString() {
        return "PhotoSize{" +
                "type='" + type + '\'' +
                ", offset=" + offset +
                ", length=" + length +
                ", url=" + url +
                '}';
    }
}
