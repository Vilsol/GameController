package me.vilsol.gamecontroller.common.messages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class MessageTest {

    @Test
    void decode(){
        assertNull(Message.decode(null));
        assertNull(Message.decode(null));
        assertNull(Message.decode(""));
        assertNull(Message.decode("0{invalid json"));
        assertNull(Message.decode("{}"));
        assertNull(Message.decode("0"));
    }

}