package me.vilsol.gamecontroller.common.messages;

import me.vilsol.gamecontroller.common.GsonUtils;
import me.vilsol.gamecontroller.common.mouse.MouseAction;
import me.vilsol.gamecontroller.common.mouse.MousePositionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MouseMessageTest {

    @Test
    void validateStructure(){
        MouseMessage message = new MouseMessage();
        assertFalse(message.validateStructure());

        message.setPlayer("Someone");
        assertFalse(message.validateStructure());

        message.setAction(MouseAction.PRESSED);
        assertFalse(message.validateStructure());

        message.setPosition(new MouseMessage.Position(MousePositionType.ABSOLUTE, 0, 0));
        assertTrue(message.validateStructure());

        message = new MouseMessage("1", MouseAction.PRESSED, new MouseMessage.Position(MousePositionType.ABSOLUTE, 0, 0), "4");
        assertTrue(message.validateStructure());

        message.setPlayer(null);
        assertFalse(message.validateStructure());
        message.setPlayer("1");

        message.setAction(null);
        assertFalse(message.validateStructure());
        message.setAction(MouseAction.PRESSED);

        message.setPosition(null);
        assertFalse(message.validateStructure());
        message.setPosition(new MouseMessage.Position(MousePositionType.ABSOLUTE, 0, 0));

        assertTrue(message.validateStructure());

        assertEquals("1", message.getPlayer());
        assertEquals(MouseAction.PRESSED, message.getAction());
        assertEquals(new MouseMessage.Position(MousePositionType.ABSOLUTE, 0, 0), message.getPosition());

        MouseMessage.Position position = new MouseMessage.Position();
        message.setPosition(position);
        assertFalse(message.validateStructure());

        position.setType(MousePositionType.ABSOLUTE);
        assertFalse(message.validateStructure());

        position.setX(1);
        assertFalse(message.validateStructure());

        position.setY(2);
        assertTrue(message.validateStructure());

        assertEquals(MousePositionType.ABSOLUTE, position.getType());
        assertEquals(new Integer(1), position.getX());
        assertEquals(new Integer(2), position.getY());
    }

    @Test
    void encode(){
        MouseMessage.Position position = new MouseMessage.Position(MousePositionType.ABSOLUTE, 1, 2);
        MouseMessage message = new MouseMessage("Someone", MouseAction.PRESSED, position, "{\"something\": \"darkside\"}");
        assertEquals("{\"player\":\"Someone\",\"action\":\"PRESSED\",\"position\":{\"type\":\"ABSOLUTE\",\"x\":1,\"y\":2},\"payload\":\"{\\\"something\\\": \\\"darkside\\\"}\"}", GsonUtils.GSON.toJson(message));
    }

    @Test
    void decode(){
        assertNull(Message.decode("1"));
        assertNull(Message.decode("1{}"));
        assertNull(Message.decode("1{\"player\":\"Someone\"}"));
        assertNull(Message.decode("1{\"player\":\"Someone\",\"action\":\"PRESSED\"}"));
        assertNull(Message.decode("1{\"player\":\"Someone\",\"action\":\"PRESSED\",\"position\":{\"type\":\"ABSOLUTE\"}\"}}"));
        assertNull(Message.decode("1{\"player\":\"Someone\",\"action\":\"PRESSED\",\"position\":{\"type\":\"ABSOLUTE\",\"x\":1}\"}}"));
        assertNotNull(Message.decode("1{\"player\":\"Someone\",\"action\":\"PRESSED\",\"position\":{\"type\":\"ABSOLUTE\",\"x\":1,\"y\":2}}"));
        assertNotNull(Message.decode("1{\"player\":\"Someone\",\"action\":\"PRESSED\",\"position\":{\"type\":\"ABSOLUTE\",\"x\":1,\"y\":2},\"payload\":\"{\\\"something\\\": \\\"darkside\\\"}\"}"));

        MouseMessage.Position position = new MouseMessage.Position(MousePositionType.ABSOLUTE, 1, 2);
        MouseMessage message = new MouseMessage("Someone", MouseAction.PRESSED, position, "{\"something\": \"darkside\"}");
        MouseMessage decoded = Message.decode("1{\"player\":\"Someone\",\"action\":\"PRESSED\",\"position\":{\"type\":\"ABSOLUTE\",\"x\":1,\"y\":2},\"payload\":\"{\\\"something\\\": \\\"darkside\\\"}\"}");
        assertEquals(message, decoded);
    }

}