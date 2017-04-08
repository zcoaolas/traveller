package com.eis.czc.util;

/**
 * Created by zcoaolas on 2017/4/4.
 */
public enum SystemRole {

    USER(1), REVIEWER(2), EDITOR(4), ADMIN(8);

    private int character;

    SystemRole(int v){
        this.character = v;
    }

    public int getCharacter() {
        return character;
    }
}
