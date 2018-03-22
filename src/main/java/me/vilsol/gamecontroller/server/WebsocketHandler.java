package me.vilsol.gamecontroller.server;

import me.vilsol.gamecontroller.common.messages.KeyboardMessage;
import me.vilsol.gamecontroller.common.messages.Message;
import me.vilsol.gamecontroller.common.messages.MessageType;
import me.vilsol.gamecontroller.common.messages.PayloadMessage;
import me.vilsol.gamecontroller.server.core.MessageProcessor;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebsocketHandler {

    @OnWebSocketMessage
    public void message(Session session, String message){
        int jsonStart = message.indexOf("{");

        if(jsonStart < 0){
            return;
        }

        MessageType type;

        try{
            type = MessageType.values()[Integer.parseInt(message.substring(0, jsonStart))];
        }catch(Exception ignored){
            return;
        }

        switch(type){
            case KEYBOARD:
                KeyboardMessage keyboardMessage = Message.decode(KeyboardMessage.class, message.substring(jsonStart));

                if(keyboardMessage == null){
                    return;
                }

                MessageProcessor.processKeyboardMessage(session, keyboardMessage);
                break;
            case PAYLOAD:
                PayloadMessage payloadMessage = Message.decode(PayloadMessage.class, message.substring(jsonStart));

                if(payloadMessage == null){
                    return;
                }

                MessageProcessor.processPayloadMessage(session, payloadMessage);
                break;
        }
    }

}
