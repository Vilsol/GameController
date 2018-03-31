package me.vilsol.gamecontroller.common.messages;

public enum MessageType {

    KEYBOARD(KeyboardMessage.class),
    MOUSE(MouseMessage.class),
    PAYLOAD(PayloadMessage.class),
    EVENT(EventMessage.class);

    private Class<? extends Message> messageClass;

    MessageType(Class<? extends Message> messageClass) {
        this.messageClass = messageClass;
    }

    public Class<? extends Message> getMessageClass(){
        return messageClass;
    }

}
