package de.marco_egger.chatui.list;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.marco_egger.chatui.R;
import de.marco_egger.chatui.interfaces.OnLoadMoreMessagesListener;
import de.marco_egger.chatui.interfaces.OnMessageInteractionListener;
import de.marco_egger.chatui.list.viewholders.LoadingMessageViewHolder;
import de.marco_egger.chatui.list.viewholders.MessageViewHolder;
import de.marco_egger.chatui.list.viewholders.SystemMessageViewHolder;
import de.marco_egger.chatui.list.viewholders.UserMessageViewHolder;
import de.marco_egger.chatui.model.Message;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Marco Egger
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private static final int VIEW_TYPE_LOCAL_USER = 1;
    private static final int VIEW_TYPE_REMOTE_USER = 2;
    private static final int VIEW_TYPE_SYSTEM = 3;
    private static final int VIEW_TYPE_LOADING = 4;

    private RecyclerView list;
    private OnMessageInteractionListener messageInteractionListener;
    private OnLoadMoreMessagesListener moreMessagesListener;
    private ArrayList<Message> messages;
    private Handler handler;

    private AtomicBoolean loadingEnabled;
    private AtomicBoolean dataPending;

    public MessageAdapter(MessageList list, final OnMessageInteractionListener messageInteractionListener,
                          final OnLoadMoreMessagesListener moreMessagesListener) {
        this.list = list;
        this.messageInteractionListener = messageInteractionListener;
        this.moreMessagesListener = moreMessagesListener;
        this.messages = new ArrayList<>();
        this.handler = new Handler();
        this.loadingEnabled = new AtomicBoolean(false);
        this.dataPending = new AtomicBoolean(false);

        setHasStableIds(true);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case VIEW_TYPE_LOCAL_USER:
                v = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_message_local_text, parent, false);
                return new UserMessageViewHolder(v, messageInteractionListener);

            case VIEW_TYPE_REMOTE_USER:
                v = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_message_remote_text, parent, false);
                return new UserMessageViewHolder(v, messageInteractionListener);

            case VIEW_TYPE_SYSTEM:
                v = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_message_system, parent, false);
                return new SystemMessageViewHolder(v, messageInteractionListener);

            case VIEW_TYPE_LOADING:
                v = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_loading, parent, false);
                return new LoadingMessageViewHolder(v, null);

            default:
                throw new IllegalArgumentException("The view type is not defined correctly");
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_LOADING:
                // Only request new data if there is no data loading process running and if loading is enabled
                if (!dataPending.get() && loadingEnabled.get()) {
                    dataPending.set(true);
                    // Notify the listener in a separate thread, so the RecyclerView is not blocked in the meantime
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            moreMessagesListener.onLoadMoreMessages();
                        }
                    });
                }
                break;

            case VIEW_TYPE_LOCAL_USER:
            case VIEW_TYPE_REMOTE_USER:
            case VIEW_TYPE_SYSTEM:
                // If loading is enabled the first message has the position 1 in the adapter, so we need an offset to get the
                // correct message from the ArrayList
                // Set the message item into the ViewHolder
                holder.setMessage(messages.get(position - calculateOffset()));
                break;

            default:
                throw new IllegalArgumentException("The message type is not defined correctly");
        }
    }

    @Override
    public int getItemViewType(int position) {
        // The first position is reserved for the loading view if new data can be loaded
        if (loadingEnabled.get() && position == 0) {
            return VIEW_TYPE_LOADING;
        }

        // If loading is enabled the first message has the position 1 in the adapter, so we need an offset to get the
        // correct message from the ArrayList
        // Decide the view type on the message source
        switch (messages.get(position - calculateOffset()).getSource()) {
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
    public long getItemId(int position) {
        // If loading is enabled and the very first position is reached -> id is -1
        if (loadingEnabled.get() && position == 0) {
            return -1;
        }

        // If loading is enabled the first message has position 1 in the adapter, so we need an offset to get the
        // correct message from the ArrayList
        return messages.get(position - calculateOffset()).getId();
    }

    @Override
    public int getItemCount() {
        // The amount of messages and if new messages can be loaded an additional item for
        // displaying the loading indicator
        return messages.size() + (loadingEnabled.get() ? 1 : 0);
    }

    /**
     * Notify the adapter that the data has been loaded from the last event. If this is not called no further
     * load more messages event will be triggered!
     */
    public synchronized void notifyDataLoaded() {
        dataPending.set(false);
    }

    /**
     * En/disable loading. This includes showing/hiding of the loading indicator and triggering
     * events to the subscribed listener
     *
     * @param enable {@code true} to enable loading, {@code false} to disable
     */
    public synchronized void enableLoading(boolean enable) {
        dataPending.set(false);
        setLoadingEnabled(enable);
    }

    private synchronized void setLoadingEnabled(boolean enable) {
        // Store new value
        loadingEnabled.set(enable);

        // Notify about the insertion/removal of the loading view
        if (loadingEnabled.get()) {
            notifyItemInserted(0);
        } else {
            notifyItemRemoved(0);
        }
    }

    /**
     * Add a new message to the adapter with an animation.
     *
     * @param message the new message to add
     */
    public synchronized void addMessage(Message message) {
        messages.add(message);

        // Notify the adapter about an inserted message with the correct offset is loading is enabled
        notifyItemInserted(messages.size() + calculateOffset());
        // Scroll to the bottom (end of list) with an inverted offset
        list.scrollToPosition(messages.size() - (loadingEnabled.get() ? 0 : 1));
    }

    /**
     * Add a new message at the beginning of the list to the adapter with an animation.
     *
     * @param message the new message to add to the beginning
     */
    public synchronized void addMessageToStart(Message message) {
        messages.add(0, message);
        notifyItemInserted(0);
    }

    /**
     * Calculate an offset depending on the current state if new messages are available.
     *
     * @return the calculated offset
     */
    private synchronized int calculateOffset() {
        return loadingEnabled.get() ? 1 : 0;
    }
}
