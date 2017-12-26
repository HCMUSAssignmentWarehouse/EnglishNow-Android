package com.iceteaviet.englishnow.ui.splash.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.EnglishNowApp;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.ui.intro.view.IntroActivity;
import com.iceteaviet.englishnow.ui.login.view.LoginActivity;
import com.iceteaviet.englishnow.ui.login.view.PostLoginDialog;
import com.iceteaviet.englishnow.utils.AppConstants;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity {

    @Inject
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // assign singleton instances to fields
        // We need to cast to `MyApp` in order to get the right method
        ((EnglishNowApp) getApplication()).getAppComponent().inject(this);

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
            if (sharedPreferences.getBoolean(AppConstants.KEY_APP_LAUNCH_FIRST_TIME, true)) {
                //Show intro activity
                Intent intent = new Intent(this, IntroActivity.class);
                startActivity(intent);

                //Write new value
                sharedPreferences.edit()
                        .putBoolean(AppConstants.KEY_APP_LAUNCH_FIRST_TIME, false)
                        .apply();
            } else {
                //start login activity
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } else {
            //start navigate activity
            PostLoginDialog d = PostLoginDialog.showDialog(this);
        }
    }
}
