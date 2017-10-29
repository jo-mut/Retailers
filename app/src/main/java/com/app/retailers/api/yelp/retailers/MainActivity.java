package com.app.retailers.api.yelp.retailers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.passwordEditText)EditText mPasswordEditText;
    @Bind(R.id.emailEditText)EditText mEmailEditText;
    @Bind(R.id.logInButton)Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v == mLoginButton){
            Intent intent = new Intent(MainActivity.this, RetailersListActivity.class);
            startActivity(intent);
        }
    }
}
