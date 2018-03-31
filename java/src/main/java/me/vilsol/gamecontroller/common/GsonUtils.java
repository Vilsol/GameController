package me.vilsol.gamecontroller.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.vilsol.gamecontroller.common.keys.Key;
import me.vilsol.gamecontroller.common.messages.KeyboardMessage;
import me.vilsol.gamecontroller.server.core.Player;

public class GsonUtils {

    public static final Gson GSON = new GsonBuilder().create();

    public static Object getPayload(Player player, KeyboardMessage.Action action, Key key){
        Class<?> payloadType = player.getPayloadType(key);

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
