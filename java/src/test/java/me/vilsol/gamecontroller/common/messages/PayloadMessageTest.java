package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.GsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayloadMessageTest {

    @Test
    void validateStructure(){
        PayloadMessage message = new PayloadMessage();
        assertFalse(message.validateStructure());

        message.setPlayer("Someone");
        assertFalse(message.validateStructure());

        message.setPayloadType("Memes");
        assertFalse(message.validateStructure());

        message.setPayload("Hello World");
        assertTrue(message.validateStructure());

        message = new PayloadMessage("1", "2", "3");
        assertTrue(message.validateStructure());

        message.setPlayer(null);
        assertFalse(message.validateStructure());
        message.setPlayer("1");

        message.setPayload(null);
        assertFalse(message.validateStructure());
        message.setPayload("2");

        message.setPayloadType(null);
        assertFalse(message.validateStructure());
        message.setPayloadType("3");

        assertTrue(message.validateStructure());

        assertEquals("1", message.getPlayer());
        assertEquals("2", message.getPayload());
        assertEquals("3", message.getPayloadType());
    }

    @Test
    void encode(){
        PayloadMessage message = new PayloadMessage("Someone", "Hello World", "test");
        assertEquals("{\"player\":\"Someone\",\"payload\":\"Hello World\",\"payloadType\":\"test\"}", GsonUtils.GSON.toJson(message));
    }

    @Test
    void decode(){
        assertNull(Message.decode("2"));
        assertNull(Message.decode("2{}"));
        assertNull(Message.decode("2{\"player\":\"Someone\"}"));
        assertNull(Message.decode("2{\"player\":\"Someone\",\"payload\":\"Hello World\"}"));

        PayloadMessage message = new PayloadMessage("Someone", "Hello World", "test");
        PayloadMessage decoded = Message.decode("2{\"player\":\"Someone\",\"payload\":\"Hello World\",\"payloadType\":\"test\"}");
        assertEquals(message, decoded);
    }

}