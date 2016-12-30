package de.marco_egger.chatui;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import de.marco_egger.chatui.interfaces.OnLoadMoreMessagesListener;
import de.marco_egger.chatui.interfaces.OnMessageInteractionListener;
import de.marco_egger.chatui.interfaces.OnSendMessageListener;
import de.marco_egger.chatui.list.MessageAdapter;
import de.marco_egger.chatui.list.MessageList;
import de.marco_egger.chatui.model.Message;

/**
 * Put this {@link Fragment} into your activity to provide a chat message list with an edit field for the user to
 * type new messages. It displays a {@link FloatingActionButton} to let the user sent his message.
 *
 * @author Marco Egger
 */
public class ChatMessagesFragment extends Fragment implements
        OnMessageInteractionListener,
        OnLoadMoreMessagesListener {

    private OnSendMessageListener sendMessageListener;
    private OnMessageInteractionListener messageInteractionListener;
    private OnLoadMoreMessagesListener moreMessagesListener;

    private MessageList list;
    private MessageAdapter adapter;

    private EditText editText;
    private FloatingActionButton fabSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat_messages, container, false);

        // Get list and set adapter
        list = (MessageList) v.findViewById(R.id.list);
        // Register this fragment for both listeners, so the ViewHolders can be propagated correctly from the beginning
        adapter = new MessageAdapter(list, this, this);
        list.setAdapter(adapter);

        // Get "new message" view references
        editText = (EditText) v.findViewById(R.id.edit_text);
        fabSend = (FloatingActionButton) v.findViewById(R.id.fab_send);

        // Register fab send listener
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSendMessageClicked();
            }
        });

        // Set the style
        setStyle(R.style.ChatUiTheme);

        return v;
    }

    /**
     * Customize the style.
     *
     * @param style the new style
     */
    public void setStyle(int style) {
        TypedArray ta = getActivity().obtainStyledAttributes(style, R.styleable.ChatUiTheme);

        // Get send drawable
        Drawable sendDrawable = ta.getDrawable(R.styleable.ChatUiTheme_srcSendDrawable);
        if (sendDrawable != null) {
            fabSend.setImageDrawable(sendDrawable);
        }

        // Set send button color
        int sendBackgroundColor = ta.getColor(R.styleable.ChatUiTheme_sendBackgroundColor,
                ContextCompat.getColor(getContext(), R.color.colorDarkBlue));
        fabSend.setBackgroundTintList(ColorStateList.valueOf(sendBackgroundColor));

        ta.recycle();
    }

    @Override
    public void onMessageClicked(Message message) {
        if (messageInteractionListener != null) {
            messageInteractionListener.onMessageClicked(message);
        }
    }

    @Override
    public void onLoadMoreMessages() {
        if (moreMessagesListener != null) {
            moreMessagesListener.onLoadMoreMessages();
        }
    }

    /**
     * En/disable loading events.
     *
     * @param moreMessagesAvailable {@link true} if there are new messages to load, {@code false} otherwise
     */
    public void setMoreMessagesAvailable(boolean moreMessagesAvailable) {
        if (moreMessagesAvailable) {
            adapter.enableLoading(true);
        } else {
            adapter.enableLoading(false);
        }
    }

    /**
     * Add a new message to the end (most recent message) of the conversation.
     *
     * @param message the message to add
     */
    public void addMessage(Message message) {
        adapter.addMessage(message);
    }

    /**
     * Add a new message to the start (oldest message) of the conversation.
     *
     * @param message the message to add to the start
     */
    public void addMessageToStart(Message message) {
        adapter.addMessageToStart(message);
    }

    public void notifyNewMessagesLoaded() {
        adapter.notifyDataLoaded();
    }

    /**
     * Set the listener for receiving events when the user wants to send a message.
     *
     * @param listener the listener to set (can be {@code null})
     */
    public void setSendMessageListener(@Nullable OnSendMessageListener listener) {
        this.sendMessageListener = listener;
    }

    /**
     * Set the listener for message interaction events.
     *
     * @param listener the listener to set (can be {@code null})
     */
    public void setMessageInteractionListener(@Nullable OnMessageInteractionListener listener) {
        this.messageInteractionListener = listener;
    }

    /**
     * Set the listener for loading more messages.
     *
     * @param listener the listener to set (can be {@code null})
     */
    public void setMoreMessagesListener(@Nullable OnLoadMoreMessagesListener listener) {
        this.moreMessagesListener = listener;
    }

    /**
     * Handles the click event on the {@link FloatingActionButton}.
     */
    private void handleSendMessageClicked() {
        String message = editText.getText().toString();

        // If a message is entered and a listener set
        if (!TextUtils.isEmpty(message) && sendMessageListener != null) {
            // Notify registered listener
            sendMessageListener.onSendMessageClicked(message);

            // Remove text
            editText.setText("");
        }
    }
}
