package de.marco_egger.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import de.marco_egger.chatui.ChatMessagesFragment;
import de.marco_egger.chatui.model.MessageSource;
import de.marco_egger.chatui.model.TextMessage;

public class ExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        ChatMessagesFragment fragment = (ChatMessagesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        fragment.addMessage(new TextMessage(1234567L, MessageSource.LOCAL_USER, 666, "Fenix", "avatarURL", 1463657595000L, "Hallo!"));
        fragment.addMessage(new TextMessage(1234568L, MessageSource.LOCAL_USER, 666, "Fenix", "avatarURL", 1482147195000L, "Was geht?"));
        fragment.addMessage(new TextMessage(1234560L, MessageSource.LOCAL_USER, 666, "Fenix", "avatarURL", 1482161595000L, "Keine Antwort... Man bist du unhöflich"));

        fragment.addMessage(new TextMessage(1234560L, MessageSource.REMOTE_USER, 789, "Serious Vendor", "avatarURL2", 1482161695000L, "Doch, sry hab die Nachrichten leider nicht bekommen!"));
    }
}
