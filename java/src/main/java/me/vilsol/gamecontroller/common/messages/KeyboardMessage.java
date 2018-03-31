package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.keys.KeyAction;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class KeyboardMessage extends Message {

    private String player;
    private List<Action> actions;

    public KeyboardMessage(){
    }

    public KeyboardMessage(String player, List<Action> actions){
        this.player = player;
        this.actions = actions;
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
        if(actions == null || player == null){
            return false;
        }

        for(Action action : actions){
            if(!action.validateStructure()){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(o == null || getClass() != o.getClass()){
            return false;
        }

        KeyboardMessage that = (KeyboardMessage) o;
        return Objects.equals(actions, that.actions) &&
                Objects.equals(player, that.player);
    }

    @Override
    public int hashCode(){
        return Objects.hash(actions, player);
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

        public boolean validateStructure(){
            return action != null && keys != null;
        }

        @Override
        public boolean equals(Object o){
            if(this == o){
                return true;
            }

            if(o == null || getClass() != o.getClass()){
                return false;
            }

            Action action1 = (Action) o;
            return action == action1.action &&
                    Objects.equals(keys, action1.keys) &&
                    Objects.equals(payload, action1.payload);
        }

        @Override
        public int hashCode(){
            return Objects.hash(action, keys, payload);
        }

    }

}
