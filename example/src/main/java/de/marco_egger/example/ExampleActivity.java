package de.marco_egger.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import de.marco_egger.chatui.ChatMessagesFragment;
import de.marco_egger.chatui.interfaces.OnSendMessageListener;
import de.marco_egger.chatui.model.MessageSource;
import de.marco_egger.chatui.model.TextMessage;

public class ExampleActivity extends AppCompatActivity {

    private ChatMessagesFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        // Get fragment reference
        fragment = (ChatMessagesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);

        // ====== Styling =======
        fragment.setStyle(R.style.AppTheme_MyChatUi);

        // ====== Adding messages =======

        // Add local messages
        addLocalMessages();

        // Add system messages
        addSystemMessages();

        // Add remote messages
        addRemoteMessages();

        // ======= Register listener =======
        fragment.setSendMessageListener(new OnSendMessageListener() {
            @Override
            public void onSendMessageClicked(String message) {
                // Send to server, do your stuff here...

                // Don't forget to add the message on success
                fragment.addMessage(new TextMessage(6745823L, MessageSource.LOCAL_USER, 666, "Fenix",
                        "https://machura-erdem.de/demo/app/wp-content/uploads/2016/10/article-image-upload-9.jpg",
                        System.currentTimeMillis(), message));
            }
        });
    }

    private void addLocalMessages() {
        fragment.addMessage(new TextMessage(1234567L, MessageSource.LOCAL_USER, 666, "Fenix",
                "https://machura-erdem.de/demo/app/wp-content/uploads/2016/10/article-image-upload-9.jpg",
                1463657595000L, "Hello!"));
        fragment.addMessage(new TextMessage(1234568L, MessageSource.LOCAL_USER, 666, "Fenix",
                "https://machura-erdem.de/demo/app/wp-content/uploads/2016/10/article-image-upload-9.jpg",
                1482147195000L, "What's up?"));
        fragment.addMessage(new TextMessage(1234560L, MessageSource.LOCAL_USER, 666, "Fenix",
                "https://machura-erdem.de/demo/app/wp-content/uploads/2016/10/article-image-upload-9.jpg",
                1482161595000L, "No answer... You're pretty damn rude!"));
    }

    private void addSystemMessages() {
        fragment.addMessage(new TextMessage(312454L, MessageSource.SYSTEM, 0, "System",
                "", 1482161595000L, "Die security number of Serious Vendor has changed"));
    }

    private void addRemoteMessages() {
        fragment.addMessage(new TextMessage(1234560L, MessageSource.REMOTE_USER, 789, "Serious Vendor",
                "https://machura-erdem.de/demo/app/wp-content/uploads/2016/06/profile-picture.jpg",
                1482161695000L, "Sry, I just got your messages!"));
    }
}
