package com.iceteaviet.englishnow.ui.login.view;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iceteaviet.englishnow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO: Add Smart Lock for passwords: https://developers.google.com/identity/smartlock-passwords/android/
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_login) protected Button loginButton;
    @BindView(R.id.txt_email) protected TextInputEditText emailEditText;
    @BindView(R.id.txt_password) protected TextInputEditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
        });
    }
}
