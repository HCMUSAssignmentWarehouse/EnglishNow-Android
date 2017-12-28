package com.iceteaviet.englishnow.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class ListeningSkill extends Skill implements Parcelable {
    public static final Creator<ListeningSkill> CREATOR = new Creator<ListeningSkill>() {
        @Override
        public ListeningSkill createFromParcel(Parcel in) {
            return new ListeningSkill(in);
        }

        @Override
        public ListeningSkill[] newArray(int size) {
            return new ListeningSkill[size];
        }
    };

    protected ListeningSkill(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
