package com.iceteaviet.englishnow.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class SpeakingSkill extends Skill implements Parcelable {
    public static final Creator<SpeakingSkill> CREATOR = new Creator<SpeakingSkill>() {
        @Override
        public SpeakingSkill createFromParcel(Parcel in) {
            return new SpeakingSkill(in);
        }

        @Override
        public SpeakingSkill[] newArray(int size) {
            return new SpeakingSkill[size];
        }
    };

    protected SpeakingSkill(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
