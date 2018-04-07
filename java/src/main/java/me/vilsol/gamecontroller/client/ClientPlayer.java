package me.vilsol.gamecontroller.client;

import me.vilsol.gamecontroller.common.BasePlayer;
import me.vilsol.gamecontroller.common.GameController;
import me.vilsol.gamecontroller.common.PlayerResolver;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

public class ClientPlayer extends BasePlayer implements PlayerResolver {

    private WebSocketClient webSocketClient;

    public ClientPlayer(String name, String endpoint){
        super(name);

        GameController.setPlayerResolver(this);

        try{
            this.webSocketClient = new WebsocketHandler(this, new URI(endpoint));
        }catch(URISyntaxException e){
            throw new RuntimeException(e);
        }

        this.webSocketClient.connect();
    }

    @Override
    public void sendData(String data){
        this.webSocketClient.send(data);
    }

    @Override
    public BasePlayer resolvePlayer(String name){
        return this;
    }

}
