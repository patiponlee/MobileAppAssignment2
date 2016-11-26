package com.egco428.a23265.mobileappassignment2;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class signup extends AppCompatActivity implements SensorEventListener {

    EditText usernamesignup;
    EditText passwordsignup;
    EditText latitude;
    EditText longtitude;
    Button add;
    Button random;
    private SensorManager sensorManager;
    private long lastUpdate;
    float minlong = -179.0f;
    float maxlong = 179.0f;
    float minla = -85.0f;
    float maxla = 85.0f;
    Intent intent;
    public static long id = 1;
    public static String username = "username";
    public static String latitudedb = "latitudedb";
    public static String password = "password";
    public static String longtitudedb = "longtitudedb";
    CommentDataSource commentDataSource;
    protected List<Comment> data = new ArrayList<>();
    ArrayAdapter<Comment> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);
        usernamesignup = (EditText) findViewById(R.id.signupusername);
        passwordsignup = (EditText) findViewById(R.id.signuppassword);
        latitude = (EditText) findViewById(R.id.latitude);
        longtitude = (EditText) findViewById(R.id.longtitude);
        random = (Button) findViewById(R.id.randombtn);
        add = (Button) findViewById(R.id.addbtn);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        commentDataSource = new CommentDataSource(this);
        commentDataSource.open();
        data = commentDataSource.getAllComments();
        adapter = new CustomAdapter(this, 0, data);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                float i = random.nextFloat() * (maxla - minla) + minla;
                float j = random.nextFloat() * (maxlong - minlong) + minlong;
                latitude.setText("" + i);
                longtitude.setText("" + j);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                username = usernamesignup.getText().toString();
                password = passwordsignup.getText().toString();
                latitudedb = latitude.getText().toString();
                longtitudedb = longtitude.getText().toString();
                if (password.equals("") || username.equals("") || latitudedb.equals("") || longtitudedb.equals("")) {
                    Toast.makeText(getApplicationContext(), "please fill another blank", Toast.LENGTH_LONG).show();
                } else if (!check()) {
                    intent = new Intent(signup.this,MainActivity.class);
                    commentDataSource.createMessage(username, password, latitudedb, longtitudedb);
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                    commentDataSource.close();
                    startActivity(intent);
                }
            }

        });
    }

    public boolean check() {
        int i;
        int a = data.size();
        for (i = 0; i < a; i++) {
            final Comment check = data.get(i);
            if (check.getUsername().equals(username)) {
                Toast.makeText(signup.this, "Use difference Username", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
        getAccelerometer(event);
        }
    }
    private void getAccelerometer(SensorEvent event){
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            Random random = new Random();
            float i = random.nextFloat() * (maxla - minla)+ minla;
            float j = random.nextFloat() * (maxlong - minlong) + minlong;
            latitude.setText(""+i);
            longtitude.setText(""+j);
            lastUpdate = actualTime;
        }

    }
    @Override
    public  void onAccuracyChanged(Sensor sensor,int accuracy){

    }
    @Override
    protected  void onResume(){
        super.onResume();
        commentDataSource.open();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        commentDataSource.close();
        sensorManager.unregisterListener(this);
    }
}
