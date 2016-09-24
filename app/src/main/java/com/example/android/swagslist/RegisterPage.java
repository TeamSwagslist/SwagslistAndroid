package com.example.android.swagslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.aidancbrady.swagslist.Account;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
    }

    String username;
    String password;
    String repassword;

    public void setData (View view) {
        EditText usernameBox = (EditText) findViewById(R.id.usernameInput);
        username = usernameBox.getText().toString();

        EditText passwordBox = (EditText) findViewById(R.id.passwordInput);
        password = passwordBox.getText().toString();

        EditText repasswordBox = (EditText) findViewById(R.id.repasswordInput);
        repassword = repasswordBox.getText().toString();

        if (repassword.equals(password)) {
            goToMaps(view);
        }
        else {
            TextView textBox = (TextView) findViewById(R.id.textBox);
            textBox.setText("Passwords do not match.");
        }

    }

    public void goToMaps (View view) {
        Intent intent = new Intent (this, MapsActivity.class);

        startActivity(intent);
    }
}
