package me.vilsol.gamecontroller.server.core;

import me.vilsol.gamecontroller.common.GsonUtils;
import me.vilsol.gamecontroller.common.keys.Key;
import me.vilsol.gamecontroller.common.messages.KeyboardMessage;

public class Utils {

    public static Object getPayload(Player player, KeyboardMessage.Action action, Key key){
        Class<?> payloadType = player.getPayloadType(key);

        if(payloadType == null){
            return null;
        }

        try{
            return GsonUtils.GSON.fromJson(action.getPayload(), payloadType);
        }catch(Exception ignored){
            return null;
        }
    }

}
