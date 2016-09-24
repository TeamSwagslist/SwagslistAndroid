package com.example.android.swagslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aidancbrady.swagslist.client.ClientNetworkHandler;
import com.google.android.gms.vision.text.Text;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    public void setData (View view) {
        EditText usernameBox = (EditText) findViewById(R.id.usernameInput);
        String username = usernameBox.getText().toString();

        EditText passwordBox = (EditText) findViewById(R.id.passwordInput);
        String password = passwordBox.getText().toString();

        ClientNetworkHandler.Response response = ClientNetworkHandler.login(username, password);

        if(response.accept) {
            goToMaps(view);
        }
        else {
            TextView incorrect = (TextView) findViewById(R.id.incorrectPassword);
            incorrect.setText(response.message);
        }
    }

    public void goToMaps (View view) {
        Intent intent = new Intent (this, MapsActivity.class);

        startActivity(intent);
    }
}
