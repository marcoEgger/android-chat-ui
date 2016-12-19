package de.marco_egger.chatui.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author Marco Egger
 */
public class MessageList extends RecyclerView {

    public MessageList(Context context) {
        super(context);
        init(context, null);
    }

    public MessageList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MessageList(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Set layout manager
        MessageLayoutManager manager = new MessageLayoutManager(context);
        setLayoutManager(manager);
    }
}
