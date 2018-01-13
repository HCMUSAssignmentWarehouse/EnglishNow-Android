package com.iceteaviet.englishnow.ui.auth.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityLoginBinding;
import com.iceteaviet.englishnow.ui.auth.LoginNavigator;
import com.iceteaviet.englishnow.ui.auth.viewmodel.LoginViewModel;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.others.view.PostLoginDialog;
import com.iceteaviet.englishnow.utils.CommonUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

//TODO: Add Smart Lock for passwords: https://developers.google.com/identity/smartlock-passwords/android/
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator, HasFragmentInjector {
    @Inject
    protected LoginViewModel loginViewModel;

    @Inject
    protected DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private ActivityLoginBinding activityLoginBinding;

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = getViewDataBinding();
        loginViewModel.setNavigator(this);
        bindViews();
    }

    private void bindViews() {
        emailInput = activityLoginBinding.edtEmail;
        passwordInput = activityLoginBinding.edtPassword;
        emailInputLayout = activityLoginBinding.emailInputLayout;
        passwordInputLayout = activityLoginBinding.passwordInputLayout;
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
        PostLoginDialog d = PostLoginDialog.newInstance();
        d.show(getFragmentManager());
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
        CommonUtils.showAlertDialog(this, getString(R.string.email_password_incorrect));
        passwordInput.setText("");
    }

    @Override
    public void login() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!loginViewModel.isEmailValid(email)) {
            emailInputLayout.setError(getString(R.string.invalid_email));
        } else if (!loginViewModel.isPasswordValid(password)) {
            passwordInputLayout.setError(getString(R.string.invalid_password));
        } else {
            emailInputLayout.setErrorEnabled(false);
            passwordInputLayout.setErrorEnabled(false);
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
