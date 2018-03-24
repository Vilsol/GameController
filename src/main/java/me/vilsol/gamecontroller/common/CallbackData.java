package me.vilsol.gamecontroller.common;

import java.util.function.Consumer;

public class CallbackData<A, B> {

    private final Consumer<A> singleConsumer;
    private final DoubleConsumer<A, B> doubleConsumer;

    private final Class<A> aType;
    private final Class<B> bType;

    public CallbackData(Consumer<A> singleConsumer, DoubleConsumer<A, B> doubleConsumer, Class<A> aType, Class<B> bType){
        this.singleConsumer = singleConsumer;
        this.doubleConsumer = doubleConsumer;
        this.aType = aType;
        this.bType = bType;
    }

    public void execute(A a, B b){
        if(doubleConsumer != null){
            doubleConsumer.accept(a, b);
            return;
        }

        if(singleConsumer != null){
            singleConsumer.accept(a);
        }
    }

    public Class<A> getAType(){
        return aType;
    }

    public Class<B> getBType(){
        return bType;
    }

}