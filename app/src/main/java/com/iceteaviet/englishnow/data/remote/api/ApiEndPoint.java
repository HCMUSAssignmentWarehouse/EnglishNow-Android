package com.iceteaviet.englishnow.data.remote.api;

/**
 * Created by Genius Doan on 23/12/2017.
 */

public class ApiEndPoint {
    public static final String OPENTOK_BASE_URL = "https://englishnow.herokuapp.com/";
    //Return an OpenTok API key, session ID, and token.
    //This will always return the same session ID, but will produce a new token each time it's called
    // — this results in each client receiving a unique token.
    public static final String ENDPOINT_SESSION = OPENTOK_BASE_URL + "/session";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}
