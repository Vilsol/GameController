package me.vilsol.gamecontroller.client;

import me.vilsol.gamecontroller.common.messages.Message;
import me.vilsol.gamecontroller.common.messages.MessageType;
import me.vilsol.gamecontroller.common.messages.PayloadMessage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebsocketHandler extends WebSocketClient {

    private Player player;

    public WebsocketHandler(Player player, URI serverUri){
        super(serverUri);
        this.player = player;
        reconnect();
    }

    @Override
    public void onOpen(ServerHandshake handshake){
    }

    @Override
    public void onMessage(String message){
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
            case PAYLOAD:
                PayloadMessage payloadMessage = Message.decode(PayloadMessage.class, message.substring(jsonStart));

                if(payloadMessage == null){
                    return;
                }

                player.processPayload(payloadMessage);
                break;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote){
    }

    @Override
    public void onError(Exception ex){
    }

}
