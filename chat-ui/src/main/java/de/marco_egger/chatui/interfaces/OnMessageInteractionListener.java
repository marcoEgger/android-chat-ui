package de.marco_egger.chatui.interfaces;

import de.marco_egger.chatui.model.Message;

/**
 * An interaction listener for some click events.
 *
 * @author Marco Egger
 */
public interface OnMessageInteractionListener {
    /**
     * This is called when the user clicks on a {@link Message}.
     *
     * @param message the message the user clicked on
     */
    void onMessageClicked(Message message);
}
