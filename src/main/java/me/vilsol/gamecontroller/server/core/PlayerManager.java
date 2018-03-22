package me.vilsol.gamecontroller.server.core;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private static final Map<String, Player> PLAYERS = new HashMap<>();

    public static Player newPlayer(String name){
        return PLAYERS.computeIfAbsent(name, Player::new);
    }

    public static Player getPlayer(String player){
        return PLAYERS.getOrDefault(player, null);
    }

}
