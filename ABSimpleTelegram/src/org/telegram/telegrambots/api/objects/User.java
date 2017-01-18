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
 * @brief This object represents a Telegram user or bot.
 * @date 20 of June of 2015
 */
import anywheresoftware.b4a.BA.Hide;
@Hide
public class User implements IBotApiObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5923032430222671892L;
	private static final String ID_FIELD = "id";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String USERNAME_FIELD = "username";
    @JsonProperty(ID_FIELD)
    private Integer id; ///< Unique identifier for this user or bot
    @JsonProperty(FIRSTNAME_FIELD)
    private String firstName; ///< User or bots first name
    @JsonProperty(LASTNAME_FIELD)
    private String lastName; ///< Optional. User or bots last name
    @JsonProperty(USERNAME_FIELD)
    private String userName; ///< Optional. User or bots username

    public User() {
        super();
    }

    public User(JSONObject jsonObject) throws JSONException {
        super();
        this.id = jsonObject.getInt(ID_FIELD);
        this.firstName = jsonObject.getString(FIRSTNAME_FIELD);
        if (jsonObject.has(LASTNAME_FIELD)) {
            this.lastName = jsonObject.getString(LASTNAME_FIELD);
        }
        if (jsonObject.has(USERNAME_FIELD)) {
            this.userName = jsonObject.getString(USERNAME_FIELD);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(ID_FIELD, id);
        gen.writeStringField(FIRSTNAME_FIELD, firstName);
        if (lastName != null) {
            gen.writeStringField(LASTNAME_FIELD, lastName);
        }
        if (userName != null) {
            gen.writeStringField(USERNAME_FIELD, userName);
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
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
