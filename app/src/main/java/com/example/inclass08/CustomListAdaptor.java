/**
 * In Class 08
 * Group Name- Samir Agrawal, Elijah Jesalva, Martin Miller.
 */
package com.example.inclass08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samir on 11/03/15.
 */
public class CustomListAdaptor extends BaseAdapter {

    private final ArrayList<ParseObject> parselist;
    Context context;
    LayoutInflater inflater;
    private List<MessageUtils> messagelist = null;
    private ArrayList<MessageUtils> arraylist;

    public CustomListAdaptor(Context context, List<MessageUtils> messagelist, List<ParseObject> parseObjlist) {
        this.context = context;
        this.messagelist = messagelist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<MessageUtils>();
        this.parselist = new ArrayList<ParseObject>();
        this.arraylist.addAll(messagelist);
        this.parselist.addAll(parseObjlist);
    }

    public class ViewHolder {
        TextView username;
        TextView user_message;
        TextView date;
        ImageButton delete;
    }

    @Override
    public int getCount() {
        return messagelist.size();
    }

    @Override
    public Object getItem(int i) {
        return messagelist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_todo_add2, null);
            // Locate the TextViews in listview_item.xml
            holder.username = (TextView) view.findViewById(R.id.textView_username);
            holder.user_message = (TextView) view.findViewById(R.id.textView_message);
            holder.date = (TextView) view.findViewById(R.id.textView_date);
            holder.delete = (ImageButton) view.findViewById(R.id.delete_text);
            holder.delete.setTag(i);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("@@ deleted clicked "+parselist.size() +" index : "+i);
                        parselist.get(i).deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e == null){

                                System.out.println("@@ deleted ");
                                    ((Messaging)context).loadMessage();
                                } else{

                                System.out.println("@@ Exception : "+e.getMessage());
                                }
                            }
                        });
                }
            });
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        System.out.println("@@ username : ");
        // Set the results into TextViews
        holder.username.setText(ParseUser.getCurrentUser().getString("name"));
        holder.user_message.setText(messagelist.get(i).getMessage());
        holder.date.setText( messagelist.get(i).getTime().toString());

        return view;
    }
}
