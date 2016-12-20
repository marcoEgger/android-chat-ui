package de.marco_egger.chatui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.marco_egger.chatui.R;
import de.marco_egger.chatui.interfaces.OnMessageInteractionListener;
import de.marco_egger.chatui.list.viewholders.MessageViewHolder;
import de.marco_egger.chatui.list.viewholders.SystemMessageViewHolder;
import de.marco_egger.chatui.list.viewholders.UserMessageViewHolder;
import de.marco_egger.chatui.model.Message;

import java.util.ArrayList;

/**
 * @author Marco Egger
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private static final int VIEW_TYPE_LOCAL_USER = 1;
    private static final int VIEW_TYPE_REMOTE_USER = 2;
    private static final int VIEW_TYPE_SYSTEM = 3;

    private RecyclerView list;
    private OnMessageInteractionListener listener;
    private ArrayList<Message> messages;

    public MessageAdapter(MessageList list, final OnMessageInteractionListener listener) {
        this.list = list;
        this.listener = listener;
        this.messages = new ArrayList<>();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case VIEW_TYPE_LOCAL_USER:
                v = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_message_local_text, parent, false);
                return new UserMessageViewHolder(v, listener);

            case VIEW_TYPE_REMOTE_USER:
                v = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_message_remote_text, parent, false);
                return new UserMessageViewHolder(v, listener);

            case VIEW_TYPE_SYSTEM:
                v = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_message_system, parent, false);
                return new SystemMessageViewHolder(v, listener);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.setMessage(messages.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        // Decide the view type on the message source
        switch (messages.get(position).getSource()) {
            case LOCAL_USER:
                return VIEW_TYPE_LOCAL_USER;

            case REMOTE_USER:
                return VIEW_TYPE_REMOTE_USER;

            case SYSTEM:
                return VIEW_TYPE_SYSTEM;

            default:
                throw new IllegalArgumentException("The message type is not defined correctly");
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
        list.scrollToPosition(messages.size() - 1);
    }
}
