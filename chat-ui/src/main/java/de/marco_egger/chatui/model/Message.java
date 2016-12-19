package de.marco_egger.chatui.model;

/**
 * @author Marco Egger
 */
public abstract class Message {

    protected final long id;
    protected final MessageSource source;
    protected final long userId;
    protected final String username;
    protected final String userAvatarUrl;
    protected final long timestamp;

    public Message(long id, MessageSource source, long userId, String username, String userAvatarUrl, long timestamp) {
        this.id = id;
        this.source = source;
        this.userId = userId;
        this.username = username;
        this.userAvatarUrl = userAvatarUrl;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public MessageSource getSource() {
        return source;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Get the users initials.
     *
     * @return the initials with 1 or 2 characters
     */
    public String getUserInitials() {
        String[] splits = username.split(" ");
        if (splits.length > 1) {
            return splits[0].substring(0, 1) + splits[1].substring(0, 1);
        } else {
            return splits[0].substring(0, 1);
        }
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
