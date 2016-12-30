package de.marco_egger.chatui.list.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import de.marco_egger.chatui.interfaces.OnMessageInteractionListener;
import de.marco_egger.chatui.list.MessageAdapter;
import de.marco_egger.chatui.model.Message;

/**
 * Basic {@link android.support.v7.widget.RecyclerView.ViewHolder} for the {@link MessageAdapter}.
 *
 * @author Marco Egger
 */
public abstract class MessageViewHolder extends RecyclerView.ViewHolder {

    protected Message message;

    public MessageViewHolder(View itemView, final OnMessageInteractionListener listener) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageClicked(message);
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

}
