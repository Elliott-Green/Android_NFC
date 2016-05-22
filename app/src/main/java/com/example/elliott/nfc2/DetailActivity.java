package com.example.elliott.nfc2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Elliott on 10/05/2016.
 */
public class DetailActivity extends Activity
{
    String passedHash = "";

    private EditText username, password, roleID;
    private TextView hash;
    private Button add_button, back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final DB db = new DB();

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        hash = (TextView)findViewById(R.id.hash);
        roleID = (EditText)findViewById(R.id.roleID);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            passedHash = getIntent().getExtras().getString("hash");
            hash.setText(passedHash);
        }



        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        Log.w("Database - Username", username.getText().toString().trim());
                        Log.w("Database - Password", password.getText().toString().trim());
                        Log.w("Database - Hash", hash.getText().toString().trim());
                        db.setupUser(username.getText().toString().trim(),
                                   password.getText().toString().trim(),
                                   hash.getText().toString().trim(),
                                   Integer.parseInt(roleID.getText().toString()));

                    }
                }).start();

            }
        });
    }
}
