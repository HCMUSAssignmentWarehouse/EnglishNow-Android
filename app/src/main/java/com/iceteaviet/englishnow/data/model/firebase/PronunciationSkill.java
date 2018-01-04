package com.iceteaviet.englishnow.data.model.firebase;

import com.iceteaviet.englishnow.data.model.base.Skill;

import java.io.Serializable;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class PronunciationSkill extends Skill implements Serializable {
    public PronunciationSkill() {
        skillName = "Pronunciation";
        rateValue = 0;
    }
}
