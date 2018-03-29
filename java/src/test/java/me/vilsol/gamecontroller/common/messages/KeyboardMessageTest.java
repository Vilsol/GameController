package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.GsonUtils;
import me.vilsol.gamecontroller.common.keys.KeyAction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class KeyboardMessageTest {

    @Test
    void validateStructure(){
        KeyboardMessage message = new KeyboardMessage();
        assertFalse(message.validateStructure());

        message.setPlayer("Someone");
        assertFalse(message.validateStructure());

        message.setActions(new ArrayList<>());
        assertTrue(message.validateStructure());

        message = new KeyboardMessage("1", new ArrayList<>());
        assertTrue(message.validateStructure());

        message.setPlayer(null);
        assertFalse(message.validateStructure());
        message.setPlayer("1");

        message.setActions(null);
        assertFalse(message.validateStructure());
        message.setActions(new ArrayList<>());

        assertTrue(message.validateStructure());

        assertEquals("1", message.getPlayer());
        assertEquals(new ArrayList<>(), message.getActions());

        KeyboardMessage.Action action = new KeyboardMessage.Action();
        message.getActions().add(action);
        assertFalse(message.validateStructure());

        action.setAction(KeyAction.PRESSED);
        assertFalse(message.validateStructure());

        action.setKeys(Collections.singletonList("enter"));
        assertTrue(message.validateStructure());

        action.setPayload("{}");
        assertTrue(message.validateStructure());

        assertEquals(KeyAction.PRESSED, action.getAction());
        assertEquals(Collections.singletonList("enter"), action.getKeys());
        assertEquals("{}", action.getPayload());
    }

    @Test
    void encode(){
        KeyboardMessage.Action action = new KeyboardMessage.Action(KeyAction.PRESSED, Collections.singletonList("enter"), "{\"something\": \"darkside\"}");
        KeyboardMessage message = new KeyboardMessage("Someone", Collections.singletonList(action));
        assertEquals("{\"player\":\"Someone\",\"actions\":[{\"action\":\"PRESSED\",\"keys\":[\"enter\"],\"payload\":\"{\\\"something\\\": \\\"darkside\\\"}\"}]}", GsonUtils.GSON.toJson(message));
    }

    @Test
    void decode(){
        assertNull(Message.decode(KeyboardMessage.class, "{}"));
        assertNull(Message.decode(KeyboardMessage.class, "{\"player\":\"Someone\"}"));
        assertNull(Message.decode(KeyboardMessage.class, "{\"player\":\"Someone\",\"actions\":[{}]}"));
        assertNull(Message.decode(KeyboardMessage.class, "{\"player\":\"Someone\",\"actions\":[{\"action\":\"PRESSED\"}\"}]}"));
        assertNotNull(Message.decode(KeyboardMessage.class, "{\"player\":\"Someone\",\"actions\":[{\"action\":\"PRESSED\",\"keys\":[\"enter\"]}]}"));
        assertNotNull(Message.decode(KeyboardMessage.class, "{\"player\":\"Someone\",\"actions\":[{\"action\":\"PRESSED\",\"keys\":[\"enter\"],\"payload\":\"{\\\"something\\\": \\\"darkside\\\"}\"}]}"));

        KeyboardMessage.Action action = new KeyboardMessage.Action(KeyAction.PRESSED, Collections.singletonList("enter"), "{\"something\": \"darkside\"}");
        KeyboardMessage message = new KeyboardMessage("Someone", Collections.singletonList(action));
        KeyboardMessage decoded = Message.decode(KeyboardMessage.class, "{\"player\":\"Someone\",\"actions\":[{\"action\":\"PRESSED\",\"keys\":[\"enter\"],\"payload\":\"{\\\"something\\\": \\\"darkside\\\"}\"}]}");
        assertEquals(message, decoded);
    }

}