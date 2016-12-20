package de.marco_egger.chatui.list.viewholders;

import android.view.View;
import android.widget.TextView;
import de.marco_egger.chatui.R;
import de.marco_egger.chatui.model.TextMessage;

/**
 * @author Marco Egger
 */
public class SystemMessageViewHolder extends MessageViewHolder {

    private TextView messageView;

    public SystemMessageViewHolder(View itemView, OnChatMessageInteractionListener listener) {
        super(itemView, listener);

        messageView = (TextView) itemView.findViewById(R.id.message);
    }

    @Override
    public void updateUi() {
        if (message instanceof TextMessage) {
            messageView.setText(((TextMessage) message).getMessage());
        }
    }
}
