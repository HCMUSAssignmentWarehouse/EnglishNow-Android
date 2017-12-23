package com.iceteaviet.englishnow.ui.login.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.utils.InputUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Genius Doan on 12/23/2017.
 */

public class SignUpDialog extends DialogFragment {
    public static final String TAG = SignUpDialog.class.getSimpleName();
    @BindView(R.id.edt_email)
    protected TextInputEditText emailInput;
    @BindView(R.id.edt_username)
    protected TextInputEditText usernameInput;
    @BindView(R.id.edt_password)
    protected TextInputEditText passwordInput;
    @BindView(R.id.edt_re_password)
    protected TextInputEditText rePasswordInput;
    @BindView(R.id.email_input_layout)
    protected TextInputLayout emailInputLayout;
    @BindView(R.id.username_input_layout)
    protected TextInputLayout usernameInputLayout;
    @BindView(R.id.password_input_layout)
    protected TextInputLayout passwordInputLayout;
    @BindView(R.id.re_password_input_layout)
    protected TextInputLayout rePasswordInputLayout;
    @BindView(R.id.btn_sign_up)
    protected Button btnSignUp;

    private OnRegisterCompleteListener listener;
    private FirebaseAuth mAuth;

    static SignUpDialog newInstance() {
        SignUpDialog d = new SignUpDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        d.setArguments(args);

        return d;
    }

    public static SignUpDialog showDialog(Activity activity) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment prev = activity.getFragmentManager().findFragmentByTag(TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        SignUpDialog dialog = SignUpDialog.newInstance();
        dialog.show(ft, TAG);

        return dialog;
    }

    public void setOnRegisterResultListener(OnRegisterCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.dialog_sign_in, container, false);
        ButterKnife.bind(this, convertView);

        btnSignUp.setOnClickListener(view -> {
            onSignUpButtonClicked();
        });

        return convertView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void onSignUpButtonClicked() {
        //Check info
        String email = emailInput.getText().toString();
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String rePassword = rePasswordInput.getText().toString();

        if (!InputUtils.validateEmail(email)) {
            emailInputLayout.setError(getString(R.string.invalid_email));
        } else if (!InputUtils.validateUsername(username)) {
            usernameInputLayout.setError(getString(R.string.invalid_username));
        } else if (!InputUtils.validatePassword(password)) {
            passwordInputLayout.setError(getString(R.string.invalid_password));
        } else if (!password.equals(rePassword)) {
            rePasswordInputLayout.setError(getString(R.string.re_password_not_match));
        } else {
            emailInputLayout.setErrorEnabled(false);
            passwordInputLayout.setErrorEnabled(false);
            rePasswordInputLayout.setErrorEnabled(false);
            usernameInputLayout.setErrorEnabled(false);
            doRegister(email, username, password);
        }
    }

    private void doRegister(String email, String username, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }

                        listener.onComplete(task);
                    }
                });
    }

    interface OnRegisterCompleteListener {
        public void onComplete(@NonNull Task<AuthResult> task);
    }
}
