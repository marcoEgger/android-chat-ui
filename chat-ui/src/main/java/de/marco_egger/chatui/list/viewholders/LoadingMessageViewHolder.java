package de.marco_egger.chatui.list.viewholders;

import android.view.View;
import de.marco_egger.chatui.interfaces.OnMessageInteractionListener;

/**
 * @author Marco Egger
 */
public class LoadingMessageViewHolder extends MessageViewHolder {

    public LoadingMessageViewHolder(View itemView, OnMessageInteractionListener listener) {
        super(itemView, listener);
    }

    @Override
    public void updateUi() {
        // Do nothing
    }
}
