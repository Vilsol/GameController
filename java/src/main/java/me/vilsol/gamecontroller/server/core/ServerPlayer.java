package me.vilsol.gamecontroller.server.core;

import me.vilsol.gamecontroller.common.BasePlayer;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class ServerPlayer extends BasePlayer {

    private Session session;

    public ServerPlayer(String name){
        super(name);
    }

    public Session getSession(){
        return session;
    }

    public void setSession(Session session){
        this.session = session;
    }

    @Override
    public void sendData(String data){
        try{
            session.getRemote().sendString(data);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
