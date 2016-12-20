package de.marco_egger.chatui.list.viewholders;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import de.marco_egger.chatui.list.MessageAdapter;
import de.marco_egger.chatui.model.Message;

import java.text.SimpleDateFormat;

/**
 * Basic {@link android.support.v7.widget.RecyclerView.ViewHolder} for the {@link MessageAdapter}.
 *
 * @author Marco Egger
 */
public abstract class MessageViewHolder extends RecyclerView.ViewHolder {

    @SuppressLint("SimpleDateFormat")
    protected static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    protected Message message;

    public MessageViewHolder(View itemView, final OnChatMessageInteractionListener listener) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onChatMessageClicked(message);
            }
        });
    }

    public void setMessage(Message message) {
        this.message = message;
        updateUi();
    }

    /**
     * This method is called when a new {@link Message} item is set and should update all view components.
     */
    public abstract void updateUi();

    /**
     * An interaction listener for some click events.
     */
    public interface OnChatMessageInteractionListener {
        /**
         * This is called when the user clicks on a {@link Message}.
         *
         * @param message the message the user clicked on
         */
        void onChatMessageClicked(Message message);
    }
}
