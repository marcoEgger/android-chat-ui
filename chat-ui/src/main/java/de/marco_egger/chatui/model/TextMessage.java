package de.marco_egger.chatui.model;

/**
 * @author Marco Egger
 */
public class TextMessage extends Message {

    protected final String message;

    public TextMessage(long id, MessageSource source, long userId, String username, String userAvatarUrl,
                       long timestamp, String message) {
        super(id, source, userId, username, userAvatarUrl, timestamp);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public String getMessage() {
        return message;
    }
}
