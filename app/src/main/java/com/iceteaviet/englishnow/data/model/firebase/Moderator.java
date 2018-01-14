package com.iceteaviet.englishnow.data.model.firebase;

import com.google.firebase.database.IgnoreExtraProperties;
import com.iceteaviet.englishnow.data.model.firebase.base.AbstractUser;

import java.io.Serializable;

/**
 * Created by Genius Doan on 11/01/2018.
 *
 * Model for storing moderator information
 */

@IgnoreExtraProperties
public class Moderator extends AbstractUser implements Serializable {
}
