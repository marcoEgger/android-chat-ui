package de.marco_egger.chatui.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author Marco Egger
 */

public class MessageLayoutManager extends LinearLayoutManager {

    public MessageLayoutManager(Context context) {
        super(context);
        setStackFromEnd(true);
    }
}
