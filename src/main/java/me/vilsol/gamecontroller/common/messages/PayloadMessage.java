package me.vilsol.gamecontroller.common.messages;

public class PayloadMessage extends Message {

    private String player;
    private String payload;
    private String payloadType;

    public PayloadMessage(){
    }

    public PayloadMessage(String player, String payload, String payloadType){
        this.player = player;
        this.payload = payload;
        this.payloadType = payloadType;
    }

    public String getPlayer(){
        return player;
    }

    public void setPlayer(String player){
        this.player = player;
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
        return player != null && payload != null && payloadType != null;
    }

}
