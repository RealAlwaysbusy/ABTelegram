package org.telegram.telegrambots.api.objects.inlinequery.result.chached;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.IOException;



/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Represents a link to a file stored on the Telegram servers. By default, this file will be
 * sent by the user with an optional caption. Alternatively, you can use input_message_content to
 * send a message with the specified content instead of the file.
 * @note Currently, only pdf-files and zip archives can be sent using this method.
 * @note This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 * @date 10 of April of 2016
 */
import anywheresoftware.b4a.BA.Hide;
@Hide
public class InlineQueryResultCachedDocument implements InlineQueryResult {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8098358885141781841L;
	private static final String TYPE_FIELD = "type";
    @JsonProperty(TYPE_FIELD)
    private static final String type = "document"; ///< Type of the result, must be "document"
    private static final String ID_FIELD = "id";
    private static final String TITLE_FIELD = "title";
    private static final String DOCUMENT_FILE_ID_FIELD = "document_file_id";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CAPTION_FIELD = "caption";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    @JsonProperty(ID_FIELD)
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    @JsonProperty(DOCUMENT_FILE_ID_FIELD)
    private String documentFileId; ///< A valid file identifier for the file
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. Short description of the result
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the document to be sent, 0-200 characters
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the file

    public static String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InlineQueryResultCachedDocument setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InlineQueryResultCachedDocument setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDocumentFileId() {
        return documentFileId;
    }

    public InlineQueryResultCachedDocument setDocumentFileId(String documentFileId) {
        this.documentFileId = documentFileId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InlineQueryResultCachedDocument setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public InlineQueryResultCachedDocument setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public InlineQueryResultCachedDocument setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
        return this;
    }

    public InputMessageContent getInputMessageContent() {
        return inputMessageContent;
    }

    public InlineQueryResultCachedDocument setInputMessageContent(InputMessageContent inputMessageContent) {
        this.inputMessageContent = inputMessageContent;
        return this;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TYPE_FIELD, type);
        jsonObject.put(ID_FIELD, id);
        jsonObject.put(DOCUMENT_FILE_ID_FIELD, documentFileId);
        jsonObject.put(TITLE_FIELD, title);
        if (description != null) {
            jsonObject.put(DESCRIPTION_FIELD, this.description);
        }
        if (replyMarkup != null) {
            jsonObject.put(REPLY_MARKUP_FIELD, replyMarkup);
        }
        if (inputMessageContent != null) {
            jsonObject.put(INPUTMESSAGECONTENT_FIELD, inputMessageContent);
        }
        if (caption != null) {
            jsonObject.put(CAPTION_FIELD, caption);
        }
        return jsonObject;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TYPE_FIELD, type);
        gen.writeStringField(ID_FIELD, id);
        gen.writeStringField(DOCUMENT_FILE_ID_FIELD, documentFileId);
        gen.writeStringField(TITLE_FIELD, title);
        if (description != null) {
            gen.writeStringField(DESCRIPTION_FIELD, description);
        }
        if (replyMarkup != null) {
            gen.writeObjectField(REPLY_MARKUP_FIELD, replyMarkup);
        }
        if (inputMessageContent != null) {
            gen.writeObjectField(INPUTMESSAGECONTENT_FIELD, inputMessageContent);
        }
        if (caption != null) {
            gen.writeStringField(CAPTION_FIELD, caption);
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
        return "InlineQueryResultCachedDocument{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", documentFileId='" + documentFileId + '\'' +
                ", caption='" + caption + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", inputMessageContent='" + inputMessageContent + '\'' +
                ", replyMarkup='" + replyMarkup + '\'' +
                '}';
    }
}
