package me.vilsol.gamecontroller.client;

import me.vilsol.gamecontroller.common.CallbackData;
import me.vilsol.gamecontroller.common.GsonUtils;
import me.vilsol.gamecontroller.common.keys.KeyAction;
import me.vilsol.gamecontroller.common.messages.KeyboardMessage;
import me.vilsol.gamecontroller.common.messages.MessageType;
import me.vilsol.gamecontroller.common.messages.PayloadMessage;
import org.java_websocket.client.WebSocketClient;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Player {

    private final Map<String, CallbackData<?, ?>> payloadCallbacks = new HashMap<>();

    private String name;
    private String endpoint;
    private WebSocketClient webSocketClient;

    public Player(String name, String endpoint){
        this.name = name;
        this.endpoint = endpoint;
        reconnect();
    }

    private void reconnect(){
        try{
            webSocketClient = new WebsocketHandler(this, new URI(endpoint));

            webSocketClient.connect();

            for(int i = 0; i < 10; i++){
                if(!webSocketClient.isOpen()){
                    Thread.sleep(500);
                }else{
                    return;
                }
            }

            throw new RuntimeException("Failed to connect to the websocket!");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void pressKeys(String... keys){
        executeKeyAction(KeyAction.PRESSED, null, keys);
    }

    public void pressKeys(Object payload, String... keys){
        executeKeyAction(KeyAction.PRESSED, payload, keys);
    }

    public void releaseKeys(String... keys){
        executeKeyAction(KeyAction.RELEASED, null, keys);
    }

    public void releaseKeys(Object payload, String... keys){
        executeKeyAction(KeyAction.RELEASED, payload, keys);
    }

    public void clickKeys(String... keys){
        executeKeyAction(KeyAction.CLICKED, null, keys);
    }

    public void clickKeys(Object payload, String... keys){
        executeKeyAction(KeyAction.CLICKED, payload, keys);
    }

    public void executeKeyAction(KeyAction action, Object payload, String... keys){
        KeyboardMessage message = new KeyboardMessage(new ArrayList<>(), name);
        String payloadString = null;

        if(payload != null){
            payloadString = GsonUtils.GSON.toJson(payload);
        }

        for(String key : keys){
            message.getActions().add(new KeyboardMessage.Action(action, key, payloadString));
        }

        webSocketClient.send(MessageType.KEYBOARD.ordinal() + GsonUtils.GSON.toJson(message));
    }

    public <T> void onPayload(String payloadType, Class<T> payloadClass, Consumer<T> callback){
        payloadCallbacks.put(payloadType, new CallbackData(callback, null, payloadClass, null));
    }

    protected <T> void processPayload(PayloadMessage message){
        CallbackData<?, ?> callback = payloadCallbacks.get(message.getPayloadType());

        if(callback == null){
            return;
        }

        T payload = GsonUtils.GSON.fromJson(message.getPayload(), (Type) callback.getAType());
        ((CallbackData<T, ?>) callback).execute(payload, null);
    }

    public <T> void sendPayload(String type, T payload){
        PayloadMessage payloadMessage = new PayloadMessage(name, GsonUtils.GSON.toJson(payload), type);
        webSocketClient.send(MessageType.PAYLOAD.ordinal() + GsonUtils.GSON.toJson(payloadMessage));
    }

}
