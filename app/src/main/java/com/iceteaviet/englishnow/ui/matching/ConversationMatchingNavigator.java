package com.iceteaviet.englishnow.ui.matching;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public interface ConversationMatchingNavigator {
    void requestPermissions();

    boolean selfCheckRequiredPermissions();

    void handleError(Throwable throwable);

    void navigateToVideoCallScreen(String sessionId, String token);

    void changeViewsToFindingMode();

    void changeViewsToNormalMode();
}
