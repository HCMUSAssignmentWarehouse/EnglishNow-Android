package com.iceteaviet.englishnow.ui.auth.view;

import android.app.Fragment;
import android.os.Bundle;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityLoginBinding;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.auth.LoginHandler;
import com.iceteaviet.englishnow.ui.auth.viewmodel.LoginViewModel;
import com.iceteaviet.englishnow.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

//TODO: Add Smart Lock for passwords: https://developers.google.com/identity/smartlock-passwords/android/
// Disable login button base on input
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginHandler, HasFragmentInjector {
    @Inject
    LoginViewModel loginViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = getViewDataBinding();
        loginViewModel.setHandler(this);

        ButterKnife.bind(this);
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
    public void navigateToSignUpScreen() {
        RegisterDialog dialog = RegisterDialog.newInstance();
        dialog.show(getFragmentManager());
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
