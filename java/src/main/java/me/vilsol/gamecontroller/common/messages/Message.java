package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.GsonUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class Message {

    private static Map<Integer, Class<? extends Message>> MESSAGE_MAPPINGS = new HashMap<>();
    private static Map<Class<? extends Message>, Integer> MESSAGE_MAPPINGS_REVERSE = new HashMap<>();

    static{
        setMessageMapping(0, KeyboardMessage.class);
        setMessageMapping(1, MouseMessage.class);
        setMessageMapping(2, PayloadMessage.class);
        setMessageMapping(3, EventMessage.class);
    }

    public static void setMessageMapping(Integer code, Class<? extends Message> clazz){
        MESSAGE_MAPPINGS.put(code, clazz);
        MESSAGE_MAPPINGS_REVERSE.put(clazz, code);
    }

    public static Class<? extends Message> getMessageMapping(Integer code){
        return MESSAGE_MAPPINGS.get(code);
    }

    public static Integer getMessageMapping(Class<? extends Message> clazz){
        return MESSAGE_MAPPINGS_REVERSE.get(clazz);
    }

    public static <T extends Message> T decode(String data){
        if(data == null){
            return null;
        }

        int jsonStart = data.indexOf("{");

        if(jsonStart < 0){
            return null;
        }

        Class<T> messageClass;

        try{
            messageClass = (Class<T>) getMessageMapping(Integer.parseInt(data.substring(0, jsonStart)));
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

    public abstract void process();

}
