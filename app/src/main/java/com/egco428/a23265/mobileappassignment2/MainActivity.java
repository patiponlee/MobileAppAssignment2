package com.egco428.a23265.mobileappassignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button signupbtn;
    Button signinbtn;
    Button cancelbtn;
    EditText usernamesignin;
    EditText passwordsignin;
    String usernamestr;
    String passwordstr;
    Intent intent;
    CommentDataSource commentDataSource;
    public static long id = 1;
    protected List<Comment> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernamesignin = (EditText)findViewById(R.id.username);
        passwordsignin = (EditText)findViewById(R.id.password);

        commentDataSource = new CommentDataSource(this);
        commentDataSource.open();
        data = commentDataSource.getAllComments();


        signupbtn = (Button)findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,signup.class);
                startActivity(intent);
            }
        });
        cancelbtn = (Button)findViewById(R.id.cancelbtn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernamesignin.setText("");
                passwordsignin.setText("");
            }
        });
        signinbtn = (Button)findViewById(R.id.signinbtn);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernamestr = usernamesignin.getText().toString();
                passwordstr = passwordsignin.getText().toString();
                check();
            }
        });
    }
    public void check() {
        int i;
        int a = data.size();
        for(i=0;i<a;i++) {

            final Comment check= data.get(i);
            if (check.getUsername().equals(usernamestr)&&check.getPassword().equals(passwordstr)) {
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                intent = new Intent(MainActivity.this, signin.class);
                startActivity(intent);
                return;

            }
        }
        Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();

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
