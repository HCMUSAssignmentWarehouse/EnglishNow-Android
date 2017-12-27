package com.iceteaviet.englishnow.ui.login.viewmodel;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.login.LoginHandler;
import com.iceteaviet.englishnow.utils.InputUtils;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public class LoginViewModel extends BaseViewModel<LoginHandler> {
    public LoginViewModel(AppDataSource repo, SchedulerProvider schedulerProvider) {
        super(repo, schedulerProvider);
    }

    public boolean isEmailValid(String email) {
        return InputUtils.validateEmail(email);
    }

    public boolean isPasswordValid(String password) {
        return InputUtils.validatePassword(password);
    }

    public void onServerLoginClicked() {
        getHandler().login();
    }

    public void onSignUpButtonClicked() {
        getHandler().signup();
    }

    public void doLogin(String email, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getAppDataSource()
                .doServerLoginFirebaseCall(new LoginRequest.ServerLoginRequest(email, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<AuthResult>() {
                    @Override
                    public void accept(AuthResult authResult) throws Exception {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = authResult.getUser();

                        if (user != null) {
                            //Go to post login activity
                            getHandler().navigateToPostLoginScreen();
                        }
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // If sign in fails, display a message to the user.
                        getHandler().handleError(throwable);
                        setIsLoading(false);
                    }
                }));
    }
}
