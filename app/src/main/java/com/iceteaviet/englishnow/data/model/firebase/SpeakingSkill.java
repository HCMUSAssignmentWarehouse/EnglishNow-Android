package com.iceteaviet.englishnow.data.model.firebase;

import com.iceteaviet.englishnow.data.model.base.Skill;

import java.io.Serializable;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class SpeakingSkill extends Skill implements Serializable {
    public SpeakingSkill() {
        skillName = "Speaking";
        rateValue = 0;
    }
}
