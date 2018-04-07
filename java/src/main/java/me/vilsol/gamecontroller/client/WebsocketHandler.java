package me.vilsol.gamecontroller.client;

import me.vilsol.gamecontroller.common.messages.Message;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebsocketHandler extends WebSocketClient {

    private ClientPlayer player;

    public WebsocketHandler(ClientPlayer player, URI serverUri){
        super(serverUri);
        this.player = player;
    }

    @Override
    public void onOpen(ServerHandshake handshake){
    }

    @Override
    public void onMessage(String data){
        Message message = Message.decode(data);

        if(message == null){
            return;
        }

        message.process();
    }

    @Override
    public void onClose(int code, String reason, boolean remote){
    }

    @Override
    public void onError(Exception ex){
    }

}
