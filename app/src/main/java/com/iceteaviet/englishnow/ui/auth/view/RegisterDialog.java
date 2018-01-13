package com.iceteaviet.englishnow.ui.auth.view;

import android.app.Dialog;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.DialogSignUpBinding;
import com.iceteaviet.englishnow.ui.auth.RegisterNavigator;
import com.iceteaviet.englishnow.ui.auth.viewmodel.RegisterViewModel;
import com.iceteaviet.englishnow.ui.base.BaseDialog;
import com.iceteaviet.englishnow.ui.others.view.PostLoginDialog;
import com.iceteaviet.englishnow.utils.CommonUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Genius Doan on 12/23/2017.
 */

public class RegisterDialog extends BaseDialog implements RegisterNavigator {
    public static final String TAG = RegisterDialog.class.getSimpleName();

    @Inject
    protected RegisterViewModel registerViewModel;

    private DialogSignUpBinding signUpBinding;

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText userNameInput;
    private TextInputEditText rePasswordInput;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout userNameInputLayout;
    private TextInputLayout rePasswordInputLayout;

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
        return signUpBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        AndroidInjection.inject(this);

        signUpBinding.setViewModel(registerViewModel);
        registerViewModel.setNavigator(this);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    private void bindViews() {
        emailInput = signUpBinding.edtEmail;
        passwordInput = signUpBinding.edtPassword;
        rePasswordInput = signUpBinding.edtRePassword;
        userNameInput = signUpBinding.edtUsername;

        emailInputLayout = signUpBinding.emailInputLayout;
        passwordInputLayout = signUpBinding.passwordInputLayout;
        rePasswordInputLayout = signUpBinding.rePasswordInputLayout;
        userNameInputLayout = signUpBinding.usernameInputLayout;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    @Override
    public void register() {
        //Check info
        String email = emailInput.getText().toString();
        String username = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String rePassword = rePasswordInput.getText().toString();

        if (!registerViewModel.isEmailValid(email)) {
            emailInputLayout.setError(getString(R.string.invalid_email));
        } else if (!registerViewModel.isUsernameValid(username)) {
            userNameInputLayout.setError(getString(R.string.invalid_username));
        } else if (!registerViewModel.isPasswordValid(password)) {
            passwordInputLayout.setError(getString(R.string.invalid_password));
        } else if (!password.equals(rePassword)) {
            rePasswordInputLayout.setError(getString(R.string.re_password_not_match));
        } else {
            emailInputLayout.setErrorEnabled(false);
            passwordInputLayout.setErrorEnabled(false);
            rePasswordInputLayout.setErrorEnabled(false);
            userNameInputLayout.setErrorEnabled(false);
            registerViewModel.doRegister(email, username, password);
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
        CommonUtils.showAlertDialog(getActivity(), throwable.getMessage());
        passwordInput.setText("");
        rePasswordInput.setText("");
    }

    @Override
    public void navigateToPostLoginScreen() {
        this.dismiss();
        PostLoginDialog d = PostLoginDialog.newInstance();
        d.show(getFragmentManager());
    }
}
