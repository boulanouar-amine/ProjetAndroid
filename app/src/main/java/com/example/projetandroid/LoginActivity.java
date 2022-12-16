package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        Button b1 = (Button) findViewById(R.id.button);
        DatabaseHandler db = new DatabaseHandler(this);


        //login button functionality
        b1.setOnClickListener(view -> {
            User loginUser = new User(username.getText().toString(),password.getText().toString());
            if (db.checkUsernamePassword(loginUser))
            {
                Toast.makeText(LoginActivity.this, "logged in as " +loginUser.getUsername(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, FormActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });


    }


}

