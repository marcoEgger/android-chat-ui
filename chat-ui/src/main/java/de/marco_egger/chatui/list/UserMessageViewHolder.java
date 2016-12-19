package de.marco_egger.chatui.list;

import android.view.View;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import de.marco_egger.chatui.R;
import de.marco_egger.chatui.model.TextMessage;

/**
 * @author Marco Egger
 */
public class UserMessageViewHolder extends MessageViewHolder {

    private TextView userInitialsView;
    private CircleImageView avatarView;
    private TextView messageView;
    private TextView timestampView;

    public UserMessageViewHolder(View itemView, OnChatMessageInteractionListener listener) {
        super(itemView, listener);
        userInitialsView = (TextView) itemView.findViewById(R.id.user_initials);
        avatarView = (CircleImageView) itemView.findViewById(R.id.avatar);
        messageView = (TextView) itemView.findViewById(R.id.message);
        timestampView = (TextView) itemView.findViewById(R.id.timestamp);
    }

    @Override
    public void updateUi() {
        // TODO load avatar image
        userInitialsView.setText(message.getUsername().substring(0, 1));
        timestampView.setText(SIMPLE_DATE_FORMATTER.format(message.getTimestamp()));

        // If text message
        if (message instanceof TextMessage) {
            messageView.setText(((TextMessage) message).getMessage());
        }
    }
}
