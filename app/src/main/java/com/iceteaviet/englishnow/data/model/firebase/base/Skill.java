package com.iceteaviet.englishnow.data.model.firebase.base;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by Genius Doan on 11/01/2018.
 *
 * Model for storing English skill information (Speaking, Writing,..)
 *
 * Implements Serializable to mark that this object can stream into to a sequence of byte
 * and restore these objects from this stream of bytes
 */

@IgnoreExtraProperties
public class Skill implements Serializable {
    @PropertyName("name")
    protected String skillName;

    @PropertyName("rate_value")
    protected int rateValue;

    @PropertyName("name")
    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @PropertyName("rate_value")
    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }
}
