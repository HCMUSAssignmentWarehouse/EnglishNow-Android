package com.iceteaviet.englishnow.data.model.firebase;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by Genius Doan on 04/01/2018.
 */

public class Comment implements Serializable {
    @PropertyName("content")
    private String content;

    @PropertyName("time")
    private long timestamp;

    @PropertyName("userId")
    private String userId;

    @PropertyName("username")
    private String userName;
}
