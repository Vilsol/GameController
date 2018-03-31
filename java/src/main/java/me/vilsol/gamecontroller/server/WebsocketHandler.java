package me.vilsol.gamecontroller.server;

import me.vilsol.gamecontroller.common.messages.KeyboardMessage;
import me.vilsol.gamecontroller.common.messages.Message;
import me.vilsol.gamecontroller.common.messages.PayloadMessage;
import me.vilsol.gamecontroller.server.core.MessageProcessor;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebsocketHandler {

    @OnWebSocketMessage
    public void message(Session session, String data){
        Message message = Message.decode(data);

        if(message instanceof KeyboardMessage){
            MessageProcessor.processKeyboardMessage(session, (KeyboardMessage) message);
        }else if(message instanceof PayloadMessage){
            MessageProcessor.processPayloadMessage(session, (PayloadMessage) message);
        }

    }

}
