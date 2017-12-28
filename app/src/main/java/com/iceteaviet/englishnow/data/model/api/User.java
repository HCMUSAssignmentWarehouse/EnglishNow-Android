package com.iceteaviet.englishnow.data.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class User extends AbstractUser implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    @SerializedName("skill")
    List<Skill> skillList;
    @SerializedName("conversations")
    private int conversations;
    @SerializedName("drinks")
    private int drinks;

    public User() {
        //For deserialize
    }

    public User(String email, String username, String profilePic) {
        this.email = email;
        this.username = username;
        this.profilePic = profilePic;
    }

    protected User(Parcel in) {
        conversations = in.readInt();
        drinks = in.readInt();
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public int getConversations() {
        return conversations;
    }

    public void setConversations(int conversations) {
        this.conversations = conversations;
    }

    public int getDrinks() {
        return drinks;
    }

    public void setDrinks(int drinks) {
        this.drinks = drinks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(conversations);
        parcel.writeInt(drinks);
    }
}
