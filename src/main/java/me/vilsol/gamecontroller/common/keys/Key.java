package me.vilsol.gamecontroller.common.keys;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Key {

    private int code;
    private boolean special;

    public Key(int code, boolean special){
        this.code = code;
        this.special = special;
    }

    public int getCode(){
        return code;
    }

    public boolean isSpecial(){
        return special;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        Key key = (Key) o;

        return code == key.code &&
                special == key.special;
    }

    @Override
    public int hashCode(){
        return Objects.hash(code, special);
    }

    @Nullable
    public static List<Key> toKeys(List<String> keys){
        List<Key> validKeys = new ArrayList<>();

        for(String key : keys){
            Key realKey = KeyMap.getKey(key);

            if(realKey == null){
                return null;
            }

            validKeys.add(realKey);
        }

        return validKeys;
    }

    @Override
    public String toString(){
        return "Key{" +
                "code=" + code +
                ", special=" + special +
                '}';
    }

}
