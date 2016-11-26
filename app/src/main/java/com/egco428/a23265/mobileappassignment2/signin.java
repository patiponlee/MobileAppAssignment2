package com.egco428.a23265.mobileappassignment2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class signin extends AppCompatActivity {

    protected List<Comment> data = new ArrayList<>();
    ArrayAdapter<Comment> adapter;
    private CommentDataSource commentDataSource;
    public static final long id = 1;
    Intent intent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listView = (ListView)findViewById(R.id.listofusername);
        commentDataSource = new CommentDataSource(this);
        commentDataSource.open();
        data = commentDataSource.getAllComments();
        adapter = new CustomAdapter(this,0,data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long id) {
                Comment values = data.get(position);

                String username = values.getUsername();
                String latitude = values.getLatitudedb();
                String longtitude  = values.getLongtitudedb();

                String user = "username";
                String lat = "latitude";
                String longt = "longtitude";

                Intent intent = new Intent(signin.this, MapsActivity.class);
                intent.putExtra(user,username);
                intent.putExtra(lat,latitude);
                intent.putExtra(longt,longtitude);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to Logout?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(signin.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Alert");
                //do something

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume(){
        super.onResume();
        commentDataSource.open();

    }

    @Override
    protected void onPause(){
        super.onPause();
        commentDataSource.close();

    }

}
