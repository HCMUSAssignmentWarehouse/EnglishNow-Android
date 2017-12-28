package com.iceteaviet.englishnow.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class PronunciationSkill extends Skill implements Parcelable {
    public static final Creator<PronunciationSkill> CREATOR = new Creator<PronunciationSkill>() {
        @Override
        public PronunciationSkill createFromParcel(Parcel in) {
            return new PronunciationSkill(in);
        }

        @Override
        public PronunciationSkill[] newArray(int size) {
            return new PronunciationSkill[size];
        }
    };

    protected PronunciationSkill(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
