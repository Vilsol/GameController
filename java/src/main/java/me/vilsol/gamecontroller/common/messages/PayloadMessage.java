package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.BasePlayer;
import me.vilsol.gamecontroller.common.GameController;

import java.util.Objects;

public class PayloadMessage extends PlayerMessage {

    private String payload;
    private String payloadType;

    public PayloadMessage(){
    }

    public PayloadMessage(String player, String payload, String payloadType){
        super(player);
        this.payload = payload;
        this.payloadType = payloadType;
    }

    public String getPayload(){
        return payload;
    }

    public void setPayload(String payload){
        this.payload = payload;
    }

    public String getPayloadType(){
        return payloadType;
    }

    public void setPayloadType(String payloadType){
        this.payloadType = payloadType;
    }

    @Override
    protected boolean validateStructure(){
        return super.validateStructure() && payload != null && payloadType != null;
    }

    @Override
    public void process(){
        BasePlayer player = GameController.resolvePlayer(getPlayer());

        if(player == null){
            return;
        }

        player.processPayload(this);
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

        PayloadMessage that = (PayloadMessage) o;
        return Objects.equals(payload, that.payload) &&
                Objects.equals(payloadType, that.payloadType);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), payload, payloadType);
    }

}
