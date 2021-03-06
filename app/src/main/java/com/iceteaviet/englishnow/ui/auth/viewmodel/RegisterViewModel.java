package com.iceteaviet.englishnow.ui.auth.viewmodel;

import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.User;
import com.iceteaviet.englishnow.data.model.firebase.message.RegisterMessage;
import com.iceteaviet.englishnow.ui.auth.RegisterNavigator;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.utils.InputUtils;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

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
                .registerFirebaseWithEmail(new RegisterMessage.ServerRequest(email, username, password))
                .subscribe(authResult -> {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser firebaseUser = authResult.getUser();

                    if (firebaseUser != null) {
                        //push new user to firebase
                        User user = new User.Builder()
                                .setEmail(email)
                                .setUsername(username)
                                .setProfilePic(firebaseUser.getPhotoUrl() == null ? "" : firebaseUser.getPhotoUrl().toString())
                                .build();
                        getDataManager().getUserRepository().createOrUpdate(firebaseUser.getUid(), user);

                        //Go to post login activity
                        getNavigator().navigateToPostLoginScreen();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    // If register fails, display a message to the user.
                    getNavigator().handleError(throwable);
                    setIsLoading(false);
                }));
    }
}
