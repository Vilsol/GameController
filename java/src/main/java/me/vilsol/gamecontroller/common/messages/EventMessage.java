package me.vilsol.gamecontroller.common.messages;

import java.util.Objects;

public class EventMessage extends Message {

    private String player;
    private String event;
    private String payload;

    public EventMessage(){
    }

    public EventMessage(String player, String event, String payload){
        this.player = player;
        this.event = event;
        this.payload = payload;
    }

    public String getPlayer(){
        return player;
    }

    public void setPlayer(String player){
        this.player = player;
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
        return player != null && event != null;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        EventMessage that = (EventMessage) o;
        return Objects.equals(player, that.player) &&
                Objects.equals(event, that.event) &&
                Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode(){
        return Objects.hash(player, event, payload);
    }

}
