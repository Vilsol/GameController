package me.vilsol.gamecontroller.common.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class MessageTest {

    @Test
    void decode(){
        assertNull(Message.decode(null, null));
        assertNull(Message.decode(Message.class, null));
        assertNull(Message.decode(Message.class, ""));
        assertNull(Message.decode(Message.class, "invalid json"));
        assertNull(Message.decode(Message.class, "{}"));
        assertNull(Message.decode(PayloadMessage.class, null));
        assertNull(Message.decode(PayloadMessage.class, "invalid json"));
    }

}