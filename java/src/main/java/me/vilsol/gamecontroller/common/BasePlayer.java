package me.vilsol.gamecontroller.common;

import me.vilsol.gamecontroller.common.keys.Key;
import me.vilsol.gamecontroller.common.keys.KeyAction;
import me.vilsol.gamecontroller.common.messages.*;
import me.vilsol.gamecontroller.common.mouse.MouseAction;
import me.vilsol.gamecontroller.common.mouse.MousePositionType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class BasePlayer {

    private final Map<Key, CallbackData<KeyAction, ?>> keyCallbacks = new HashMap<>();
    private final Map<String, CallbackData<?, ?>> payloadCallbacks = new HashMap<>();
    private final Map<String, CallbackData<?, ?>> eventCallbacks = new HashMap<>();
    private CallbackData<MouseMessage, ?> mouseCallback;

    private String name;

    public BasePlayer(String name){
        this.name = name;
    }

    public void onKey(char character, Consumer<KeyAction> callback){
        keyCallbacks.put(new Key((int) character, false), new CallbackData<>(callback, null, KeyAction.class, null));
    }

    public <T> void onKey(char character, Class<T> type, BiConsumer<KeyAction, T> callback){
        keyCallbacks.put(new Key((int) character, false), new CallbackData<>(null, callback, KeyAction.class, type));
    }

    public void onKey(int key, Consumer<KeyAction> callback){
        keyCallbacks.put(new Key(key, true), new CallbackData<>(callback, null, KeyAction.class, null));
    }

    public <T> void onKey(int key, Class<T> type, BiConsumer<KeyAction, T> callback){
        keyCallbacks.put(new Key(key, true), new CallbackData<>(null, callback, KeyAction.class, type));
    }

    public <T> void processKeyboard(KeyboardMessage message){
        message.getActions().forEach(action -> {
            if(action.getAction() == null || action.getKeys() == null){
                return;
            }

            List<Key> validKeys = Key.toKeys(action.getKeys());
            if(validKeys == null){
                return;
            }

            validKeys.forEach(key -> {
                if(!keyCallbacks.containsKey(key)){
                    return;
                }

                T payload = (T) GsonUtils.getPayload(keyCallbacks.get(key).getBType(), action, key);

                if(keyCallbacks.containsKey(key)){
                    if(action.getAction() == KeyAction.CLICKED){
                        ((CallbackData<KeyAction, T>) keyCallbacks.get(key)).execute(KeyAction.PRESSED, payload);
                        ((CallbackData<KeyAction, T>) keyCallbacks.get(key)).execute(KeyAction.RELEASED, payload);
                    }else{
                        ((CallbackData<KeyAction, T>) keyCallbacks.get(key)).execute(action.getAction(), payload);
                    }
                }
            });
        });
    }

    public void pressKeys(String... keys){
        sendKeyAction(KeyAction.PRESSED, null, keys);
    }

    public void pressKeys(Object payload, String... keys){
        sendKeyAction(KeyAction.PRESSED, payload, keys);
    }

    public void releaseKeys(String... keys){
        sendKeyAction(KeyAction.RELEASED, null, keys);
    }

    public void releaseKeys(Object payload, String... keys){
        sendKeyAction(KeyAction.RELEASED, payload, keys);
    }

    public void clickKeys(String... keys){
        sendKeyAction(KeyAction.CLICKED, null, keys);
    }

    public void clickKeys(Object payload, String... keys){
        sendKeyAction(KeyAction.CLICKED, payload, keys);
    }

    public void sendKeyAction(KeyAction action, Object payload, String... keys){
        KeyboardMessage message = new KeyboardMessage(name, new ArrayList<>());
        String payloadString = null;

        if(payload != null){
            payloadString = GsonUtils.GSON.toJson(payload);
        }

        for(String key : keys){
            message.getActions().add(new KeyboardMessage.Action(action, key, payloadString));
        }

        sendData(Message.getMessageMapping(KeyboardMessage.class) + GsonUtils.GSON.toJson(message));
    }

    public <T> void onMouse(Class<T> payloadClass, BiConsumer<MouseMessage, T> callback){
        this.mouseCallback = new CallbackData<>(null, callback, MouseMessage.class, payloadClass);
    }

    public <T> void processMouse(MouseMessage message){
        if(mouseCallback == null){
            return;
        }

        if(mouseCallback.getBType() == null || message.getPayload() == null){
            mouseCallback.execute(message, null);
            return;
        }

        Object payload = GsonUtils.GSON.fromJson(message.getPayload(), (Type) mouseCallback.getBType());
        ((CallbackData<MouseMessage, Object>) mouseCallback).execute(message, payload);
    }

    public <T> void pressMouse(MousePositionType positionType, int x, int y, T payload){
        sendMouseAction(MouseAction.PRESSED, positionType, x, y, payload);
    }

    public <T> void releaseMouse(MousePositionType positionType, int x, int y, T payload){
        sendMouseAction(MouseAction.RELEASED, positionType, x, y, payload);
    }

    public <T> void clickMouse(MousePositionType positionType, int x, int y, T payload){
        sendMouseAction(MouseAction.CLICKED, positionType, x, y, payload);
    }

    public <T> void moveMouse(MousePositionType positionType, int x, int y, T payload){
        sendMouseAction(MouseAction.MOVED, positionType, x, y, payload);
    }

    public <T> void sendMouseAction(MouseAction action, MousePositionType positionType, int x, int y, T payload){
        MouseMessage mouseMessage = new MouseMessage(name, action, new MouseMessage.Position(positionType, x, y), GsonUtils.GSON.toJson(payload));
        sendData(Message.getMessageMapping(MouseMessage.class) + GsonUtils.GSON.toJson(mouseMessage));
    }

    public <T> void onPayload(String payloadType, Class<T> payloadClass, Consumer<T> callback){
        payloadCallbacks.put(payloadType, new CallbackData(callback, null, payloadClass, null));
    }

    public <T> void processPayload(PayloadMessage message){
        CallbackData<?, ?> callback = payloadCallbacks.get(message.getPayloadType());

        if(callback == null){
            return;
        }

        if(callback.getAType() == null){
            callback.execute(null, null);
            return;
        }

        T payload = GsonUtils.GSON.fromJson(message.getPayload(), (Type) callback.getAType());
        ((CallbackData<T, ?>) callback).execute(payload, null);
    }

    public <T> void sendPayload(String type, T payload){
        PayloadMessage payloadMessage = new PayloadMessage(name, GsonUtils.GSON.toJson(payload), type);
        sendData(Message.getMessageMapping(PayloadMessage.class) + GsonUtils.GSON.toJson(payloadMessage));
    }

    public <T> void onEvent(String event, Class<T> payloadClass, Consumer<T> callback){
        eventCallbacks.put(event, new CallbackData(callback, null, payloadClass, null));
    }

    public <T> void processEvent(EventMessage message){
        CallbackData<?, ?> callback = eventCallbacks.get(message.getEvent());

        if(callback == null){
            return;
        }

        if(callback.getAType() == null){
            callback.execute(null, null);
            return;
        }

        T payload = GsonUtils.GSON.fromJson(message.getPayload(), (Type) callback.getAType());
        ((CallbackData<T, ?>) callback).execute(payload, null);
    }

    public <T> void sendEvent(String event, T payload){
        EventMessage eventMessage = new EventMessage(name, event, GsonUtils.GSON.toJson(payload));
        sendData(Message.getMessageMapping(EventMessage.class) + GsonUtils.GSON.toJson(eventMessage));
    }

    public abstract void sendData(String data);

}
