package me.vilsol.gamecontroller.server.core;

import me.vilsol.gamecontroller.common.CallbackData;
import me.vilsol.gamecontroller.common.DoubleConsumer;
import me.vilsol.gamecontroller.common.GsonUtils;
import me.vilsol.gamecontroller.common.keys.Key;
import me.vilsol.gamecontroller.common.keys.KeyAction;
import me.vilsol.gamecontroller.common.messages.MessageType;
import me.vilsol.gamecontroller.common.messages.PayloadMessage;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Player {

    private final Map<Key, CallbackData<KeyAction, ?>> keyCallbacks = new HashMap<>();
    private final Map<String, CallbackData<?, ?>> payloadCallbacks = new HashMap<>();

    private String name;

    private Session session;

    public Player(String name){
        this.name = name;
    }

    public Session getSession(){
        return session;
    }

    public void setSession(Session session){
        this.session = session;
    }

    public void onKey(char character, Consumer<KeyAction> callback){
        keyCallbacks.put(new Key((int) character, false), new CallbackData<>(callback, null, KeyAction.class, null));
    }

    public <T> void onKey(char character, Class<T> type, DoubleConsumer<KeyAction, T> callback){
        keyCallbacks.put(new Key((int) character, false), new CallbackData<>(null, callback, KeyAction.class, type));
    }

    public void onKey(int key, Consumer<KeyAction> callback){
        keyCallbacks.put(new Key(key << 16, true), new CallbackData<>(callback, null, KeyAction.class, null));
    }

    public <T> void onKey(int key, Class<T> type, DoubleConsumer<KeyAction, T> callback){
        keyCallbacks.put(new Key(key << 16, true), new CallbackData<>(null, callback, KeyAction.class, type));
    }

    public <T> void executeKey(Key key, KeyAction action, T payload){
        if(keyCallbacks.containsKey(key)){
            if(action == KeyAction.CLICKED){
                ((CallbackData<KeyAction, T>) keyCallbacks.get(key)).execute(KeyAction.PRESSED, payload);
                ((CallbackData<KeyAction, T>) keyCallbacks.get(key)).execute(KeyAction.RELEASED, payload);
            }else{
                ((CallbackData<KeyAction, T>) keyCallbacks.get(key)).execute(action, payload);
            }
        }
    }

    public void executeKey(Key key, KeyAction action){
        if(keyCallbacks.containsKey(key)){
            keyCallbacks.get(key).execute(action, null);
        }
    }

    public Class<?> getPayloadType(Key key){
        if(keyCallbacks.containsKey(key)){
            return keyCallbacks.get(key).getBType();
        }

        return null;
    }

    public <T> void onPayload(String payloadType, Class<T> payloadClass, Consumer<T> callback){
        payloadCallbacks.put(payloadType, new CallbackData(callback, null, payloadClass, null));
    }

    public <T> void sendPayload(String payloadType, T payload){
        if(session == null || !session.isOpen()){
            return;
        }

        PayloadMessage payloadMessage = new PayloadMessage(name, GsonUtils.GSON.toJson(payload), payloadType);

        try{
            session.getRemote().sendString(MessageType.PAYLOAD.ordinal() + GsonUtils.GSON.toJson(payloadMessage));
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public CallbackData<?, ?> getPayloadCallback(String payloadType){
        return payloadCallbacks.get(payloadType);
    }

}
