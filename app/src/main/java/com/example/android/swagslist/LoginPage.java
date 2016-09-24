package com.example.android.swagslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    String username;
    String password;

    public void setData (View view) {
        EditText usernameBox = (EditText) findViewById(R.id.usernameInput);
        username = usernameBox.getText().toString();

        EditText passwordBox = (EditText) findViewById(R.id.passwordInput);
        password = passwordBox.getText().toString();

        goToMaps(view);
    }

    public void goToMaps (View view) {
        Intent intent = new Intent (this, MapsActivity.class);

        startActivity(intent);
    }
}
