package com.appdeeb.adeebibne.secretMessage;

//Owner: Adeeb Ibne Amjad

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;

public class MainActivity extends Activity {
    EditText user_message;
    EditText user_key;
    TextView display_encrypted_message;
    TextView copyText;
    Button copyButton;
    String finalEncryptedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_message = (EditText)findViewById(R.id.messageToEncrypt);
        user_key = (EditText)findViewById(R.id.encryptKey);
        display_encrypted_message = (TextView)findViewById(R.id.finalMessage);
        copyText = (TextView)this.findViewById(R.id.finalMessage);


        Button encryptButton = (Button)findViewById(R.id.encryption_button);
        Button decryptButton = (Button)findViewById(R.id.decryptKey);
        copyButton = (Button) this.findViewById(R.id.copyButton);
        if (finalEncryptedMessage != null) {

            copyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(copyText.getText().toString());
                }
            });
        }

        encryptButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                String name = user_message.getText().toString();
                int user_key_next = Integer.parseInt(user_key.getText().toString());
                finalEncryptedMessage = encrypt(name,user_key_next);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                display_encrypted_message.setText("Encrypted message: " + finalEncryptedMessage);



            }
        });
        decryptButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                String name = user_message.getText().toString();
                int user_key_next = Integer.parseInt(user_key.getText().toString());
                String finalDecryptedMessage = decrypt(name,user_key_next);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                display_encrypted_message.setText("Decrypted message: " + finalDecryptedMessage);



            }
        });
    }

    public static String encrypt(String originalMessage, int shift) {
        String encryptedMessage = "";
        for (int i = 0; i < originalMessage.length(); i++) {

            char latest = originalMessage.charAt(i);
            // is it uppercase?
            if (latest >= 'A' && latest <= 'Z') {
                // add shift to it. Then normalize it so that it's between A and Z still.
                int letterCount = (latest - 'A');
                // Add the shift and take modulus to put it back between 0 and 25.
                int encrypted = (letterCount + shift) % 26;

                // if result is less than 0 then this is a negative number. add 26 to it
                if (encrypted < 0) {
                    encrypted = encrypted + 26;
                }

                // Now add back the letter A. Store into latest
                latest = (char) (encrypted + 'A');
            } else if (latest >= 'a' && latest <= 'z') {
                // same idea, but starting with 'a'
                int letterCount = latest - 'a';
                int encrypted = (letterCount + shift) % 26;

                // if encrypted is less than 0 then this is a negative number. add 26 to it
                if (encrypted < 0) {
                    encrypted = encrypted + 26;
                }

                latest = (char) (encrypted + 'a');
            }

            encryptedMessage = encryptedMessage + latest;
        }

        return encryptedMessage;
    }

    public static String decrypt(String originalMessage, int shift) {
        return encrypt(originalMessage, -shift);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",

                Uri.parse("http://host/path"),

                Uri.parse("android-app://com.appdeeb.adeebibne.ajairaencryptor/http/host/path")
        );
        //AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.appdeeb.adeebibne.ajairaencryptor/http/host/path")
        );
        //AppIndex.AppIndexApi.end(client, viewAction);
        //client.disconnect();
    }
}