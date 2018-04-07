package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.BasePlayer;
import me.vilsol.gamecontroller.common.GameController;

import java.util.Objects;

public class EventMessage extends PlayerMessage {

    private String event;
    private String payload;

    public EventMessage(){
    }

    public EventMessage(String player, String event, String payload){
        super(player);
        this.event = event;
        this.payload = payload;
    }

    public String getEvent(){
        return event;
    }

    public void setEvent(String event){
        this.event = event;
    }

    public String getPayload(){
        return payload;
    }

    public void setPayload(String payload){
        this.payload = payload;
    }

    @Override
    protected boolean validateStructure(){
        return super.validateStructure() && event != null;
    }

    @Override
    public void process(){
        BasePlayer player = GameController.resolvePlayer(getPlayer());

        if(player == null){
            return;
        }

        player.processEvent(this);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        if(!super.equals(o)){
            return false;
        }

        EventMessage that = (EventMessage) o;
        return Objects.equals(event, that.event) &&
                Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), event, payload);
    }

}
