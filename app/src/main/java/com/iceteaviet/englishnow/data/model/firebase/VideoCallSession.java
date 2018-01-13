package com.iceteaviet.englishnow.data.model.firebase;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by Genius Doan on 11/01/2018.
 */

@IgnoreExtraProperties
public class VideoCallSession implements Serializable {
    @PropertyName("learner_uid")
    private String learnerUid;

    @PropertyName("speaker_uid")
    private String speakerUid;

    @PropertyName("learner_token")
    private String learnerToken;

    @PropertyName("speaker_token")
    private String speakerToken;

    @PropertyName("session_id")
    private String sessionId;

    public VideoCallSession() {

    }

    public String getLearnerUid() {
        return learnerUid;
    }

    public void setLearnerUid(String learnerUid) {
        this.learnerUid = learnerUid;
    }

    public String getSpeakerUid() {
        return speakerUid;
    }

    public void setSpeakerUid(String speakerUid) {
        this.speakerUid = speakerUid;
    }

    public String getLearnerToken() {
        return learnerToken;
    }

    public void setLearnerToken(String learnerToken) {
        this.learnerToken = learnerToken;
    }

    public String getSpeakerToken() {
        return speakerToken;
    }

    public void setSpeakerToken(String speakerToken) {
        this.speakerToken = speakerToken;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
