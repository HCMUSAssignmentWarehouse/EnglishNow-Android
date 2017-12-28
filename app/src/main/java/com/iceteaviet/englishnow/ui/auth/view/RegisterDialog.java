package com.iceteaviet.englishnow.ui.auth.view;

import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.DialogSignUpBinding;
import com.iceteaviet.englishnow.ui.auth.RegisterHandler;
import com.iceteaviet.englishnow.ui.auth.viewmodel.RegisterViewModel;
import com.iceteaviet.englishnow.ui.base.BaseDialog;
import com.iceteaviet.englishnow.ui.others.view.PostLoginDialog;
import com.iceteaviet.englishnow.utils.CommonUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Genius Doan on 12/23/2017.
 */

public class RegisterDialog extends BaseDialog implements RegisterHandler {
    public static final String TAG = RegisterDialog.class.getSimpleName();

    @Inject
    RegisterViewModel registerViewModel;

    DialogSignUpBinding signUpBinding;

    static RegisterDialog newInstance() {
        RegisterDialog d = new RegisterDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        d.setArguments(args);

        return d;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        signUpBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_sign_up, container, false);
        View convertView = signUpBinding.getRoot();

        AndroidInjection.inject(this);

        signUpBinding.setViewModel(registerViewModel);
        registerViewModel.setHandler(this);

        return convertView;
    }

    @Override
    public void register() {
        //Check info
        String email = signUpBinding.edtEmail.getText().toString();
        String username = signUpBinding.edtUsername.getText().toString();
        String password = signUpBinding.edtPassword.getText().toString();
        String rePassword = signUpBinding.edtRePassword.getText().toString();

        if (!registerViewModel.isEmailValid(email)) {
            signUpBinding.emailInputLayout.setError(getString(R.string.invalid_email));
        } else if (!registerViewModel.isUsernameValid(username)) {
            signUpBinding.usernameInputLayout.setError(getString(R.string.invalid_username));
        } else if (!registerViewModel.isPasswordValid(password)) {
            signUpBinding.passwordInputLayout.setError(getString(R.string.invalid_password));
        } else if (!password.equals(rePassword)) {
            signUpBinding.rePasswordInputLayout.setError(getString(R.string.re_password_not_match));
        } else {
            signUpBinding.emailInputLayout.setErrorEnabled(false);
            signUpBinding.passwordInputLayout.setErrorEnabled(false);
            signUpBinding.rePasswordInputLayout.setErrorEnabled(false);
            signUpBinding.usernameInputLayout.setErrorEnabled(false);
            registerViewModel.doRegister(email, username, password);
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
        CommonUtils.showAlertDialog(getActivity(), throwable.getMessage());
        signUpBinding.edtPassword.setText("");
        signUpBinding.edtRePassword.setText("");
    }

    @Override
    public void navigateToPostLoginScreen() {
        this.dismiss();
        PostLoginDialog d = PostLoginDialog.newInstance();
        d.show(getFragmentManager());
    }
}
