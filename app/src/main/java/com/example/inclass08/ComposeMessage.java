/**
 * In Class 08
 * Group Name- Samir Agrawal, Elijah Jesalva, Martin Miller.
 */
package com.example.inclass08;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//package com.example.inclass08;

/**
 * Created by Samir on 11/02/15.
 */

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ComposeMessage extends Activity {

    EditText composeMessage;
    Button submitInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        submitInfo= (Button) findViewById(R.id.button_submit);
        composeMessage= (EditText) findViewById(R.id.editText_compose);

        submitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (composeMessage.getText().toString().matches("")) {
                    Toast.makeText(ComposeMessage.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                } else {
                    ParseObject privateNote = new ParseObject("Message");
                    privateNote.put("title", composeMessage.getText().toString());
                    privateNote.setACL(new ParseACL(ParseUser
                            .getCurrentUser()));
                    privateNote.saveInBackground();
                    Toast.makeText(ComposeMessage.this, "Messae sent successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ComposeMessage.this, Messaging.class);
                    startActivity(intent);
                    finish();

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(ComposeMessage.this, Messaging.class);
        startActivity(intent);
        finish();
    }
}
