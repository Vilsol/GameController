package me.vilsol.gamecontroller.common;

@FunctionalInterface
public interface DoubleConsumer<T, M> {

    void accept(T t, M m);

}
