package com.iceteaviet.englishnow.data.model.firebase;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.iceteaviet.englishnow.data.model.firebase.base.AbstractUser;
import com.iceteaviet.englishnow.data.model.firebase.base.NewsFeedItem;
import com.iceteaviet.englishnow.data.model.firebase.base.Skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genius Doan on 28/12/2017.
 */

@IgnoreExtraProperties
public class User extends AbstractUser implements Serializable {
    @PropertyName("skills")
    private List<Skill> skillList;

    @PropertyName("conversations")
    private int conversations;

    @PropertyName("drinks")
    private int drinks;

    @PropertyName("pizzas")
    private int pizzas;

    @PropertyName("newsfeed_item")
    private List<NewsFeedItem> newsFeedItems;

    public User() {
        //For deserialize
    }

    public User(String email, String username, String profilePic) {
        this(email, username, profilePic, 0, 0, new ArrayList<>());
    }

    public User(String email, String username, String profilePic, int drinks, int pizzas, List<NewsFeedItem> newsFeedItems) {
        super(email, username, profilePic);
        this.drinks = drinks;
        this.pizzas = pizzas;
        this.newsFeedItems = newsFeedItems;
        this.skillList = new ArrayList<>();

        //Add default skill
        skillList.add(new SpeakingSkill());
        skillList.add(new ListeningSkill());
        skillList.add(new PronunciationSkill());
    }

    public User(Builder builder) {
        super(builder);
        this.skillList = builder.skillList;
        this.conversations = builder.conversations;
        this.drinks = builder.drinks;
        this.pizzas = builder.pizzas;
        this.newsFeedItems = builder.newsFeedItems;
    }

    @PropertyName("skills")
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

    public int getPizzas() {
        return pizzas;
    }

    public void setPizzas(int pizzas) {
        this.pizzas = pizzas;
    }

    @PropertyName("newsfeed_item")
    public List<NewsFeedItem> getNewsFeedItems() {
        return newsFeedItems;
    }

    public void setNewsFeedItems(List<NewsFeedItem> newsFeedItems) {
        this.newsFeedItems = newsFeedItems;
    }

    public float getOverallRating() {
        float res = 0;

        for (Skill skill : skillList) {
            res += skill.getRateValue();
        }

        return res / skillList.size();
    }

    public static class Builder extends AbstractUser.Builder<Builder> {
        private List<Skill> skillList;
        private int conversations;
        private int drinks;
        private int pizzas;
        private List<NewsFeedItem> newsFeedItems;


        public Builder() {

        }

        public Builder setSkillList(List<Skill> skillList) {
            this.skillList = skillList;
            return this;
        }

        public Builder setConversations(int conversations) {
            this.conversations = conversations;
            return this;
        }

        public Builder setDrinks(int drinks) {
            this.drinks = drinks;
            return this;
        }

        public Builder setPizzas(int pizzas) {
            this.pizzas = pizzas;
            return this;
        }

        public Builder setNewsFeedItems(List<NewsFeedItem> newsFeedItems) {
            this.newsFeedItems = newsFeedItems;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
