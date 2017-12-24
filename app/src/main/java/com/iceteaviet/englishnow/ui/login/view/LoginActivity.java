package com.iceteaviet.englishnow.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.utils.CommonUtils;
import com.iceteaviet.englishnow.utils.InputUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO: Add Smart Lock for passwords: https://developers.google.com/identity/smartlock-passwords/android/
// Disable login button base on input
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    protected RelativeLayout loginButton;
    @BindView(R.id.email_input_layout)
    protected TextInputLayout emailInputLayout;
    @BindView(R.id.password_input_layout)
    protected TextInputLayout passwordInputLayout;
    @BindView(R.id.edt_email)
    protected TextInputEditText emailInput;
    @BindView(R.id.edt_password)
    protected TextInputEditText passwordInput;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.btn_sign_up)
    protected TextView btnSignUp;
    @BindView(R.id.btn_forgot_password)
    protected TextView btnForgotPassword;
    @BindView(R.id.btn_try_anonymous)
    protected TextView btnTryAnonymous;

    //Variables
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(view -> {
            onLoginButtonClicked();
        });

        btnSignUp.setOnClickListener(view -> {
            onSignUpButtonClicked();
        });
    }

    private void onSignUpButtonClicked() {
        SignUpDialog dialog = SignUpDialog.showDialog(this);
        dialog.setOnRegisterResultListener(task -> {
            if (task.isSuccessful()) {
                //If successful, it also user has signed in into the app
                doPostLogin();
            } else {
                CommonUtils.showAlertDialog(this, task.getException().getMessage());
            }
        });
    }

    private void onLoginButtonClicked() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!InputUtils.validateEmail(email)) {
            emailInputLayout.setError(getString(R.string.invalid_email));
        } else if (!InputUtils.validatePassword(password)) {
            passwordInputLayout.setError(getString(R.string.invalid_password));
        } else {
            emailInputLayout.setErrorEnabled(false);
            passwordInputLayout.setErrorEnabled(false);
            doLogin(email, password);
        }
    }

    private void doLogin(String email, String password) {
        CommonUtils.hideKeyboard(this, this.getCurrentFocus());
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            //Go to post login activity
                            doPostLogin();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        CommonUtils.showAlertDialog(this, getString(R.string.email_password_incorrect));
                        passwordInput.setText("");
                    }
                    progressBar.setVisibility(View.GONE);
                });
    }

    private void doPostLogin() {
        PostLoginDialog d = PostLoginDialog.showDialog(this);
    }
}
