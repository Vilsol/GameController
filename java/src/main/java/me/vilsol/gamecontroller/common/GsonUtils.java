package me.vilsol.gamecontroller.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.vilsol.gamecontroller.common.keys.Key;
import me.vilsol.gamecontroller.common.messages.KeyboardMessage;

public class GsonUtils {

    public static final Gson GSON = new GsonBuilder().create();

    public static Object getPayload(Class<?> payloadType, KeyboardMessage.Action action, Key key){
        if(payloadType == null){
            return null;
        }

        try{
            return GSON.fromJson(action.getPayload(), payloadType);
        }catch(Exception ignored){
            return null;
        }
    }

}
