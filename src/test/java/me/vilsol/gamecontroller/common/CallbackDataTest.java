package me.vilsol.gamecontroller.common;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class CallbackDataTest {

    @Test
    void test(){
        AtomicReference<String> returnString = new AtomicReference<>();
        AtomicReference<Integer> returnInteger = new AtomicReference<>();

        Consumer<String> singleConsumer = returnString::set;
        DoubleConsumer<String, Integer> doubleConsumer = (resultString, resultInteger) -> {
            returnString.set(resultString);
            returnInteger.set(resultInteger);
        };

        (new CallbackData<>(singleConsumer, null, String.class, Integer.class)).execute("A", 1);
        assertEquals("A", returnString.get());
        assertNull(returnInteger.get());

        returnString.set(null);
        returnInteger.set(null);

        (new CallbackData<>(singleConsumer, doubleConsumer, String.class, Integer.class)).execute("A", 1);
        assertEquals("A", returnString.get());
        assertEquals(1, (int) returnInteger.get());

        (new CallbackData<>(null, null, String.class, Integer.class)).execute(null, null);
        assertEquals("A", returnString.get());
        assertEquals(1, (int) returnInteger.get());

        (new CallbackData<>(singleConsumer, null, String.class, Integer.class)).execute(null, null);
        assertNull(returnString.get());
        assertEquals(1, (int) returnInteger.get());

        (new CallbackData<>(singleConsumer, doubleConsumer, String.class, Integer.class)).execute("A", null);
        assertEquals("A", returnString.get());
        assertNull(returnInteger.get());

        CallbackData<String, Integer> callback = new CallbackData<>(singleConsumer, doubleConsumer, String.class, Integer.class);
        assertEquals(String.class, callback.getAType());
        assertEquals(Integer.class, callback.getBType());
    }

}