package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.GsonUtils;

public abstract class Message {

    public static <T extends Message> T decode(Class<T> clazz, String message){
        T decoded;

        try{
            decoded = GsonUtils.GSON.fromJson(message, clazz);
        }catch(Exception ignored){
            return null;
        }

        if(!decoded.validateStructure()){
            return null;
        }

        return decoded;
    }

    protected abstract boolean validateStructure();

}
