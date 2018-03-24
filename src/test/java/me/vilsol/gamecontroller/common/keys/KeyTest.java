package me.vilsol.gamecontroller.common.keys;

import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {

    @Test
    void toKeys(){
        assertNotNull(Key.toKeys(Arrays.asList(
                "a",
                ";",
                "}",
                "'",
                "+",
                "1",
                "~",
                "\\",
                "\u00EA"
        )));

        assertNotNull(Key.toKeys(Arrays.asList(
                "escape",
                "delete",
                "f1",
                "f24",
                "insert",
                "space",
                "up",
                "down",
                "left",
                "right",
                "tab",
                "capslock",
                "shift",
                "control",
                "alt",
                "pageup",
                "pagedown",
                "home",
                "end",
                "prtsc",
                "pause",
                "enter"
        )));

        assertNull(Key.toKeys(Collections.singletonList("hello")));
        assertNull(Key.toKeys(Collections.singletonList("world")));
        assertNull(Key.toKeys(Collections.singletonList("asdf1234")));

        assertEquals(KeyMap.getKey("enter"), new Key(KeyEvent.VK_ENTER, true));
        assertEquals(KeyMap.getKey("space"), new Key(KeyEvent.VK_SPACE, true));

        assertNotEquals(KeyMap.getKey("space"), new Key(KeyEvent.VK_ENTER, true));
        assertNotEquals(KeyMap.getKey("enter"), new Key(KeyEvent.VK_SPACE, true));

        assertEquals(KeyMap.getKey("{"), new Key((int) '{', false));
        assertEquals(KeyMap.getKey("\u00EA"), new Key((int) '\u00EA', false));

        assertNotEquals(KeyMap.getKey("\u00EA"), new Key((int) '{', false));
        assertNotEquals(KeyMap.getKey("{"), new Key((int) '\u00EA', false));
    }

}