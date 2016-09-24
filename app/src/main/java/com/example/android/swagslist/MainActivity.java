package com.example.android.swagslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLoginPage(View view) {
        Intent intent = new Intent(this, LoginPage.class);

        startActivity(intent);
    }

    public void goToRegisterPage(View view) {
        Intent intent = new Intent(this, RegisterPage.class);

        startActivity(intent);
    }
}
