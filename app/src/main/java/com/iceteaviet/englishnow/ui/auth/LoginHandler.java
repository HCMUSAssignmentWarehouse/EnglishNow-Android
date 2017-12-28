package com.iceteaviet.englishnow.ui.auth;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface LoginHandler {
    void navigateToPostLoginScreen();

    void handleError(Throwable throwable);

    void login();

    void navigateToSignUpScreen();
}
