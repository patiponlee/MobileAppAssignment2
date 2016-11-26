package com.egco428.a23265.mobileappassignment2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by USER on 6/11/2559.
 */
public class CustomAdapter extends ArrayAdapter<Comment> {
    Context context;
    List<Comment> objects;

    public CustomAdapter(Context context, int resource, List<Comment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Comment comment = objects.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE); //link with interface
        View view = inflater.inflate(R.layout.list,null);

        TextView txt = (TextView)view.findViewById(R.id.usernamelist);
        txt.setText(comment.getUsername());
        return view;
    }

}

