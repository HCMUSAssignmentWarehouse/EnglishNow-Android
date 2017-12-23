package com.iceteaviet.englishnow.ui.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.ui.login.view.LoginActivity;
import com.iceteaviet.englishnow.ui.login.view.PostLoginNavigateActivity;
import com.iceteaviet.englishnow.R;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            //start login activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            //start navigate activity
            Intent intent = new Intent(this, PostLoginNavigateActivity.class);
            startActivity(intent);
        }
    }
}
