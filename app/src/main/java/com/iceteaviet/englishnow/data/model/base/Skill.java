package com.iceteaviet.englishnow.data.model.base;

import com.google.firebase.database.PropertyName;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public abstract class Skill {
    @PropertyName("name")
    String skillName;

    @PropertyName("rate_value")
    int rateValue;
}
