package de.marco_egger.chatui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.marco_egger.chatui.list.MessageAdapter;
import de.marco_egger.chatui.list.MessageList;
import de.marco_egger.chatui.list.MessageViewHolder;
import de.marco_egger.chatui.model.Message;

/**
 * @author Marco Egger
 */
public class ChatMessagesFragment extends Fragment implements MessageViewHolder.OnChatMessageInteractionListener {

    private MessageList list;
    private MessageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat_messages, container, false);

        // Get list and set adapter
        list = (MessageList) v.findViewById(R.id.list);
        adapter = new MessageAdapter(this);
        list.setAdapter(adapter);

        return v;
    }

    @Override
    public void onChatMessageClicked(Message message) {
        Log.d("ChatMessagesFragment", message.toString());
    }

    public void addMessage(Message message) {
        adapter.addMessage(message);
    }
}
