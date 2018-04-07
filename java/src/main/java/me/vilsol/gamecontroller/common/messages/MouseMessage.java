package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.BasePlayer;
import me.vilsol.gamecontroller.common.GameController;
import me.vilsol.gamecontroller.common.mouse.MouseAction;
import me.vilsol.gamecontroller.common.mouse.MousePositionType;

import java.util.Objects;

public class MouseMessage extends PlayerMessage {

    private MouseAction action;
    private Position position;
    private String payload;

    public MouseMessage(){
    }

    public MouseMessage(String player, MouseAction action, Position position, String payload){
        super(player);
        this.action = action;
        this.position = position;
        this.payload = payload;
    }

    public MouseAction getAction(){
        return action;
    }

    public void setAction(MouseAction action){
        this.action = action;
    }

    public Position getPosition(){
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public String getPayload(){
        return payload;
    }

    public void setPayload(String payload){
        this.payload = payload;
    }

    @Override
    protected boolean validateStructure(){
        return super.validateStructure() && action != null && position != null && position.validateStructure();
    }

    @Override
    public void process(){
        BasePlayer player = GameController.resolvePlayer(getPlayer());

        if(player == null){
            return;
        }

        player.processMouse(this);
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

        MouseMessage that = (MouseMessage) o;
        return action == that.action &&
                Objects.equals(position, that.position) &&
                Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), action, position, payload);
    }

    public static class Position {

        private MousePositionType type;
        private Integer x;
        private Integer y;

        public Position(){
        }

        public Position(MousePositionType type, Integer x, Integer y){
            this.type = type;
            this.x = x;
            this.y = y;
        }

        public MousePositionType getType(){
            return type;
        }

        public void setType(MousePositionType type){
            this.type = type;
        }

        public Integer getX(){
            return x;
        }

        public void setX(Integer x){
            this.x = x;
        }

        public Integer getY(){
            return y;
        }

        public void setY(Integer y){
            this.y = y;
        }

        public boolean validateStructure(){
            return type != null && x != null && y != null;
        }

        @Override
        public boolean equals(Object o){
            if(this == o){
                return true;
            }
            if(o == null || getClass() != o.getClass()){
                return false;
            }
            Position position = (Position) o;
            return type == position.type &&
                    Objects.equals(x, position.x) &&
                    Objects.equals(y, position.y);
        }

        @Override
        public int hashCode(){
            return Objects.hash(type, x, y);
        }

    }


}
