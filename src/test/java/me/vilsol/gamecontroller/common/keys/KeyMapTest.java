package me.vilsol.gamecontroller.common.keys;

import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class KeyMapTest {

    @Test
    void getKey(){
        assertNull(KeyMap.getKey("hello"));
        assertNull(KeyMap.getKey("world"));
        assertNull(KeyMap.getKey("asdf1234"));

        assertEquals(KeyMap.getKey("enter"), new Key(KeyEvent.VK_ENTER, true));
        assertEquals(KeyMap.getKey("space"), new Key(KeyEvent.VK_SPACE, true));

        assertNotEquals(KeyMap.getKey("space"), new Key(KeyEvent.VK_ENTER, true));
        assertNotEquals(KeyMap.getKey("enter"), new Key(KeyEvent.VK_SPACE, true));

        assertEquals(KeyMap.getKey("{"), new Key((int) '{', false));
        assertEquals(KeyMap.getKey("\u00EA"), new Key((int) '\u00EA', false));

        assertNotEquals(KeyMap.getKey("\u00EA"), new Key((int) '{', false));
        assertNotEquals(KeyMap.getKey("{"), new Key((int) '\u00EA', false));
    }

    @Test
    void getName(){
        assertEquals("enter", KeyMap.getName(KeyMap.getKey("enter")));
        assertEquals("space", KeyMap.getName(KeyMap.getKey("space")));
        assertEquals("escape", KeyMap.getName(KeyMap.getKey("escape")));

        assertNull(KeyMap.getName(null));
        assertNull(KeyMap.getName(new Key(123456, true)));

        assertEquals("a", KeyMap.getName(KeyMap.getKey("a")));
        assertEquals("{", KeyMap.getName(KeyMap.getKey("{")));
        assertEquals("\u00EA", KeyMap.getName(KeyMap.getKey("\u00EA")));
    }

}