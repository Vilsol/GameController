package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.GsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventMessageTest {

    @Test
    void validateStructure(){
        EventMessage message = new EventMessage();
        assertFalse(message.validateStructure());

        message.setPlayer("Someone");
        assertFalse(message.validateStructure());

        message.setEvent("Memes");
        assertTrue(message.validateStructure());

        message = new EventMessage("1", "2", "3");
        assertTrue(message.validateStructure());

        message.setPlayer(null);
        assertFalse(message.validateStructure());
        message.setPlayer("1");

        message.setEvent(null);
        assertFalse(message.validateStructure());
        message.setEvent("2");

        message.setPayload(null);
        assertTrue(message.validateStructure());
        message.setPayload("3");

        assertTrue(message.validateStructure());

        assertEquals("1", message.getPlayer());
        assertEquals("2", message.getEvent());
        assertEquals("3", message.getPayload());
    }

    @Test
    void encode(){
        EventMessage message = new EventMessage("Someone", "test", "Hello World");
        assertEquals("{\"player\":\"Someone\",\"event\":\"test\",\"payload\":\"Hello World\"}", GsonUtils.GSON.toJson(message));
    }

    @Test
    void decode(){
        assertNull(Message.decode("3"));
        assertNull(Message.decode("3{}"));
        assertNull(Message.decode("3{\"player\":\"Someone\"}"));
        assertNotNull(Message.decode("3{\"player\":\"Someone\",\"event\":\"test\"}"));

        EventMessage message = new EventMessage("Someone", "test", "Hello World");
        EventMessage decoded = Message.decode("3{\"player\":\"Someone\",\"event\":\"test\",\"payload\":\"Hello World\"}");
        assertEquals(message, decoded);
    }

}