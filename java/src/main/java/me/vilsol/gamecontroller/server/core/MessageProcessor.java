package me.vilsol.gamecontroller.server.core;

import me.vilsol.gamecontroller.common.CallbackData;
import me.vilsol.gamecontroller.common.GsonUtils;
import me.vilsol.gamecontroller.common.keys.Key;
import me.vilsol.gamecontroller.common.messages.KeyboardMessage;
import me.vilsol.gamecontroller.common.messages.PayloadMessage;
import org.eclipse.jetty.websocket.api.Session;

import java.lang.reflect.Type;
import java.util.List;

public class MessageProcessor {

    public static void processKeyboardMessage(Session session, KeyboardMessage keyboardMessage){
        Player player = PlayerManager.getPlayer(keyboardMessage.getPlayer());

        if(player == null){
            return;
        }

        player.setSession(session);

        keyboardMessage.getActions().forEach(action -> {
            if(action.getAction() == null || action.getKeys() == null){
                return;
            }

            List<Key> validKeys = Key.toKeys(action.getKeys());
            if(validKeys == null){
                return;
            }

            validKeys.forEach(key -> player.executeKey(key, action.getAction(), GsonUtils.getPayload(player, action, key)));
        });
    }

    public static <T> void processPayloadMessage(Session session, PayloadMessage payloadMessage){
        Player player = PlayerManager.getPlayer(payloadMessage.getPlayer());

        if(player == null){
            return;
        }

        player.setSession(session);

        CallbackData<?, ?> callback = player.getPayloadCallback(payloadMessage.getPayloadType());

        if(callback == null){
            return;
        }

        T payload = GsonUtils.GSON.fromJson(payloadMessage.getPayload(), (Type) callback.getAType());
        ((CallbackData<T, ?>) callback).execute(payload, null);
    }

}
