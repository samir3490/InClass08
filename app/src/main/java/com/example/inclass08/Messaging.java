/**
 * In Class 08
 * Group Name- Samir Agrawal, Elijah Jesalva, Martin Miller.
 */
package com.example.inclass08;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Messaging extends Activity {

    //    private ParseQueryAdapter<ParseObject> adapter;
    ImageButton delete;
    ListView listview;
    List<ParseObject> ob;
    CustomListAdaptor adapter;
    private List<MessageUtils> messagelist = null;
    private List<ParseObject> parseObjlist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageme);

        delete = (ImageButton) findViewById(R.id.delete_text);

        // Get currently logged in user
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // create query for currently logged in user
            loadMessage();
        } else {
            // show the signup or login screen
            Toast.makeText(getApplicationContext(),
                    R.string.not_logged_in_to_application, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void loadMessage() {
//        adapter = new ParseQueryAdapter<ParseObject>(this, "Message", android.R.layout.simple_list_item_1);
        messagelist = new ArrayList<MessageUtils>();
        parseObjlist = new ArrayList<ParseObject>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Message");
        try {
            ob = query.find();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        for (ParseObject message : ob) {

            MessageUtils map = new MessageUtils();
//            map.setUsername((String) message.get("username"));
            map.setMessage((String) message.get("title"));
            map.setTime(message.getCreatedAt());
            System.out.println("@@ Datte : " + message.getCreatedAt());
            messagelist.add(map);
            parseObjlist.add(message);
        }

        listview = (ListView) findViewById(R.id.listView1);
        // Pass the results into ListViewAdapter.java
        adapter = new CustomListAdaptor(Messaging.this, messagelist, parseObjlist);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, ComposeMessage.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_logout) {
            ParseUser.logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_refresh) {
            loadMessage();
        }

        return super.onOptionsItemSelected(item);
    }

}
