package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.keys.KeyAction;

import java.util.Collections;
import java.util.List;

public class KeyboardMessage extends Message {

    private List<Action> actions;
    private String player;

    public KeyboardMessage(){
    }

    public KeyboardMessage(List<Action> actions, String player){
        this.actions = actions;
        this.player = player;
    }

    public List<Action> getActions(){
        return actions;
    }

    public void setActions(List<Action> actions){
        this.actions = actions;
    }

    public String getPlayer(){
        return player;
    }

    public void setPlayer(String player){
        this.player = player;
    }

    @Override
    protected boolean validateStructure(){
        return actions != null || player != null;
    }

    public static class Action {

        private KeyAction action;
        private List<String> keys;
        private String payload;

        public Action(){
        }

        public Action(KeyAction action, String keys, String payload){
            this.action = action;
            this.keys = Collections.singletonList(keys);
            this.payload = payload;
        }

        public Action(KeyAction action, List<String> keys, String payload){
            this.action = action;
            this.keys = keys;
            this.payload = payload;
        }

        public KeyAction getAction(){
            return action;
        }

        public void setAction(KeyAction action){
            this.action = action;
        }

        public List<String> getKeys(){
            return keys;
        }

        public void setKeys(List<String> keys){
            this.keys = keys;
        }

        public String getPayload(){
            return payload;
        }

        public void setPayload(String payload){
            this.payload = payload;
        }

    }

}
