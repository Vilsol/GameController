package me.vilsol.gamecontroller.server;

import me.vilsol.gamecontroller.common.BasePlayer;
import me.vilsol.gamecontroller.common.GameController;
import me.vilsol.gamecontroller.common.messages.Message;
import me.vilsol.gamecontroller.common.messages.PlayerMessage;
import me.vilsol.gamecontroller.server.core.ServerPlayer;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebsocketHandler {

    @OnWebSocketMessage
    public void message(Session session, String data){
        Message message = Message.decode(data);

        if(message == null){
            return;
        }

        if(message instanceof PlayerMessage){
            BasePlayer player = GameController.resolvePlayer(((PlayerMessage) message).getPlayer());

            if(player instanceof ServerPlayer){
                ((ServerPlayer) player).setSession(session);
            }
        }

        message.process();
    }

}
