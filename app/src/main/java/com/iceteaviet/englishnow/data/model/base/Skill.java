package com.iceteaviet.englishnow.data.model.base;

import com.google.firebase.database.PropertyName;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class Skill {
    @PropertyName("name")
    protected String skillName;

    @PropertyName("rate_value")
    protected int rateValue;

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }
}
