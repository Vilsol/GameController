package me.vilsol.gamecontroller.common.messages;

import java.util.Objects;

public abstract class PlayerMessage extends Message {

    private String player;

    public PlayerMessage(){
    }

    public PlayerMessage(String player){
        this.player = player;
    }

    public String getPlayer(){
        return player;
    }

    public void setPlayer(String player){
        this.player = player;
    }

    @Override
    protected boolean validateStructure(){
        return player != null;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        PlayerMessage that = (PlayerMessage) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode(){
        return Objects.hash(player);
    }

}
