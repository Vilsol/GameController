package me.vilsol.gamecontroller.client;

import me.vilsol.gamecontroller.common.messages.EventMessage;
import me.vilsol.gamecontroller.common.messages.Message;
import me.vilsol.gamecontroller.common.messages.PayloadMessage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebsocketHandler extends WebSocketClient {

    private Player player;

    public WebsocketHandler(Player player, URI serverUri){
        super(serverUri);
        this.player = player;
    }

    @Override
    public void onOpen(ServerHandshake handshake){
    }

    @Override
    public void onMessage(String data){
        Message message = Message.decode(data);

        if(message instanceof PayloadMessage){
            player.processPayload((PayloadMessage) message);
        }else if(message instanceof EventMessage){
            player.processEvent((EventMessage) message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote){
    }

    @Override
    public void onError(Exception ex){
    }

}
