package me.vilsol.gamecontroller.common.keys;

import com.sun.istack.internal.Nullable;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyMap {

    private static final Map<String, Integer> SPECIAL_KEYS = new HashMap<>();
    private static final Map<Integer, String> SPECIAL_KEYS_REVERSE = new HashMap<>();

    static{
        addKey("enter", KeyEvent.VK_ENTER);
        addKey("space", KeyEvent.VK_SPACE);
        addKey("shift", KeyEvent.VK_SHIFT);
        addKey("control", KeyEvent.VK_CONTROL);
        addKey("alt", KeyEvent.VK_ALT);
        addKey("altgr", KeyEvent.VK_ALT_GRAPH);
        addKey("capslock", KeyEvent.VK_CAPS_LOCK);
        addKey("tab", KeyEvent.VK_TAB);
        addKey("delete", KeyEvent.VK_DELETE);
        addKey("escape", KeyEvent.VK_ESCAPE);
        addKey("f1", KeyEvent.VK_F1);
        addKey("f2", KeyEvent.VK_F2);
        addKey("f3", KeyEvent.VK_F3);
        addKey("f4", KeyEvent.VK_F4);
        addKey("f5", KeyEvent.VK_F5);
        addKey("f6", KeyEvent.VK_F6);
        addKey("f7", KeyEvent.VK_F7);
        addKey("f8", KeyEvent.VK_F8);
        addKey("f9", KeyEvent.VK_F9);
        addKey("f10", KeyEvent.VK_F10);
        addKey("f11", KeyEvent.VK_F11);
        addKey("f12", KeyEvent.VK_F12);
        addKey("f13", KeyEvent.VK_F13);
        addKey("f14", KeyEvent.VK_F14);
        addKey("f15", KeyEvent.VK_F15);
        addKey("f16", KeyEvent.VK_F16);
        addKey("f17", KeyEvent.VK_F17);
        addKey("f18", KeyEvent.VK_F18);
        addKey("f19", KeyEvent.VK_F19);
        addKey("f20", KeyEvent.VK_F20);
        addKey("f21", KeyEvent.VK_F21);
        addKey("f22", KeyEvent.VK_F22);
        addKey("f23", KeyEvent.VK_F23);
        addKey("f24", KeyEvent.VK_F24);
        addKey("backspace", KeyEvent.VK_BACK_SPACE);
        addKey("prtsc", KeyEvent.VK_PRINTSCREEN);
        addKey("srclk", KeyEvent.VK_SCROLL_LOCK);
        addKey("pause", KeyEvent.VK_PAUSE);
        addKey("insert", KeyEvent.VK_INSERT);
        addKey("delete", KeyEvent.VK_DELETE);
        addKey("pageup", KeyEvent.VK_PAGE_UP);
        addKey("pagedown", KeyEvent.VK_PAGE_DOWN);
        addKey("home", KeyEvent.VK_HOME);
        addKey("end", KeyEvent.VK_END);
        addKey("up", KeyEvent.VK_UP);
        addKey("down", KeyEvent.VK_DOWN);
        addKey("left", KeyEvent.VK_LEFT);
        addKey("right", KeyEvent.VK_RIGHT);
    }

    private static void addKey(String key, Integer code){
        SPECIAL_KEYS.put(key, code << 16);
        SPECIAL_KEYS_REVERSE.put(code << 16, key);
    }

    @Nullable
    public static Key getKey(String key){
        if(SPECIAL_KEYS.containsKey(key.toLowerCase())){
            return new Key(SPECIAL_KEYS.get(key.toLowerCase()), true);
        }

        if(key.length() != 1){
            return null;
        }

        return new Key((int) key.charAt(0), false);
    }

    @Nullable
    public static String getName(
            @Nullable
                    Key key){
        if(key == null){
            return null;
        }

        if(key.isSpecial()){
            if(SPECIAL_KEYS_REVERSE.containsKey(key.getCode())){
                return SPECIAL_KEYS_REVERSE.get(key.getCode());
            }

            return null;
        }

        return String.valueOf((char) key.getCode());
    }

}
