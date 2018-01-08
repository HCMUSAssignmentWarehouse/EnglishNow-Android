package com.iceteaviet.englishnow.data.model.firebase;

import com.iceteaviet.englishnow.data.model.firebase.base.Skill;

import java.io.Serializable;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class ListeningSkill extends Skill implements Serializable {
    public ListeningSkill() {
        skillName = "Listening";
        rateValue = 0;
    }
}
