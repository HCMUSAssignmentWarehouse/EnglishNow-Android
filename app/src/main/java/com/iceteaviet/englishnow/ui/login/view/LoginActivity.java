package com.iceteaviet.englishnow.ui.login.view;

import android.os.Bundle;
import android.view.View;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityLoginBinding;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.login.LoginHandler;
import com.iceteaviet.englishnow.ui.login.viewmodel.LoginViewModel;
import com.iceteaviet.englishnow.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;

//TODO: Add Smart Lock for passwords: https://developers.google.com/identity/smartlock-passwords/android/
// Disable login button base on input
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginHandler {
    @Inject
    LoginViewModel loginViewModel;

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = getViewDataBinding();
        loginViewModel.setHandler(this);

        ButterKnife.bind(this);
    }

    @Override
    public void showLoading() {
        activityLoginBinding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        activityLoginBinding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public LoginViewModel getViewModel() {
        return loginViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void navigateToPostLoginScreen() {
        PostLoginDialog d = PostLoginDialog.showDialog(this);
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
        CommonUtils.showAlertDialog(this, getString(R.string.email_password_incorrect));
        activityLoginBinding.edtPassword.setText("");
    }

    @Override
    public void login() {
        String email = activityLoginBinding.edtEmail.getText().toString();
        String password = activityLoginBinding.edtPassword.getText().toString();

        if (!loginViewModel.isEmailValid(email)) {
            activityLoginBinding.emailInputLayout.setError(getString(R.string.invalid_email));
        } else if (!loginViewModel.isPasswordValid(password)) {
            activityLoginBinding.passwordInputLayout.setError(getString(R.string.invalid_password));
        } else {
            activityLoginBinding.emailInputLayout.setErrorEnabled(false);
            activityLoginBinding.passwordInputLayout.setErrorEnabled(false);
            hideKeyboard();
            loginViewModel.doLogin(email, password);
        }
    }

    @Override
    public void signup() {
        SignUpDialog dialog = SignUpDialog.showDialog(this);
        dialog.setOnRegisterResultListener(task -> {
            if (task.isSuccessful()) {
                //If successful, it also user has signed in into the app
                navigateToPostLoginScreen();
            } else {
                CommonUtils.showAlertDialog(this, task.getException().getMessage());
            }
        });
    }
}
