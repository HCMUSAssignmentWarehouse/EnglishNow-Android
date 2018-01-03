package com.iceteaviet.englishnow.ui.auth.viewmodel;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;
import com.iceteaviet.englishnow.data.model.firebase.User;
import com.iceteaviet.englishnow.ui.auth.RegisterNavigator;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.utils.InputUtils;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {
    public RegisterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public boolean isEmailValid(String email) {
        return InputUtils.validateEmail(email);
    }

    public boolean isPasswordValid(String password) {
        return InputUtils.validatePassword(password);
    }

    public boolean isUsernameValid(String username) {
        return InputUtils.validateUsername(username);
    }

    public void onSignUpButtonClicked() {
        getNavigator().register();
    }

    public void doRegister(String email, String username, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doServerRegisterFirebaseCall(new RegisterRequest.ServerRegisterRequest(email, username, password))
                .subscribe(new Consumer<AuthResult>() {
                    @Override
                    public void accept(AuthResult authResult) throws Exception {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser firebaseUser = authResult.getUser();

                        if (firebaseUser != null) {
                            //push new user to firebase
                            User user = new User(firebaseUser.getEmail(),
                                    firebaseUser.getDisplayName(),
                                    firebaseUser.getPhotoUrl() == null ? "" : firebaseUser.getPhotoUrl().toString()
                            );
                            getDataManager().doPushUserToFirebase(firebaseUser.getUid(), user);

                            //Go to post login activity
                            getNavigator().navigateToPostLoginScreen();
                        }
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // If register fails, display a message to the user.
                        getNavigator().handleError(throwable);
                        setIsLoading(false);
                    }
                }));
    }
}
