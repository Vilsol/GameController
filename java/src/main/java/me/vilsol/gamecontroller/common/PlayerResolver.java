package me.vilsol.gamecontroller.common;

@FunctionalInterface
public interface PlayerResolver {

    BasePlayer resolvePlayer(String name);

}
