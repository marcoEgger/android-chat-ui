package de.marco_egger.chatui.list.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;
import de.marco_egger.chatui.R;
import de.marco_egger.chatui.interfaces.OnMessageInteractionListener;
import de.marco_egger.chatui.model.TextMessage;
import de.marco_egger.chatui.utils.DateUtils;

/**
 * @author Marco Egger
 */
public class UserMessageViewHolder extends MessageViewHolder {

    private View avatarLayout;
    private TextView userInitialsView;
    private CircleImageView avatarView;
    private TextView messageView;
    private TextView timestampView;

    public UserMessageViewHolder(View itemView, OnMessageInteractionListener listener) {
        super(itemView, listener);
        avatarLayout = itemView.findViewById(R.id.avatar_layout);
        userInitialsView = (TextView) itemView.findViewById(R.id.user_initials);
        avatarView = (CircleImageView) itemView.findViewById(R.id.avatar);
        messageView = (TextView) itemView.findViewById(R.id.message);
        timestampView = (TextView) itemView.findViewById(R.id.timestamp);
    }

    @Override
    public void updateUi() {
        Context c = itemView.getContext();

        // Hide the user initials and avatar image view when the previous message is from the same source
        avatarLayout.setVisibility(previousSource == message.getSource() ? View.INVISIBLE : View.VISIBLE);

        // Map data to view
        userInitialsView.setText(message.getUserInitials());
        Glide.with(c).load(message.getUserAvatarUrl()).centerCrop().into(avatarView);
        timestampView.setText(DateUtils.timestampToString(c, message.getTimestamp()));

        // If text message
        if (message instanceof TextMessage) {
            messageView.setText(((TextMessage) message).getMessage());
        }
    }
}
