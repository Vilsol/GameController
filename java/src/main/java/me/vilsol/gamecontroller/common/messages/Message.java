package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.GsonUtils;

public abstract class Message {

    public static <T extends Message> T decode(String data){
        if(data == null){
            return null;
        }

        int jsonStart = data.indexOf("{");

        if(jsonStart < 0){
            return null;
        }

        MessageType type;

        try{
            type = MessageType.values()[Integer.parseInt(data.substring(0, jsonStart))];
        }catch(Exception ignored){
            return null;
        }

        Class<T> messageClass;

        try{
            messageClass = (Class<T>) type.getMessageClass();
        }catch(Exception ignored){
            return null;
        }

        if(messageClass == null || messageClass == Message.class){
            return null;
        }

        T decoded;

        try{
            decoded = GsonUtils.GSON.fromJson(data.substring(jsonStart), messageClass);
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
