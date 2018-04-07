package me.vilsol.gamecontroller.server.core;

import me.vilsol.gamecontroller.common.BasePlayer;
import me.vilsol.gamecontroller.common.GameController;
import me.vilsol.gamecontroller.common.PlayerResolver;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements PlayerResolver {

    private static final Map<String, ServerPlayer> PLAYERS = new HashMap<>();

    static{
        GameController.setPlayerResolver(new PlayerManager());
    }

    public static ServerPlayer newPlayer(String name){
        return PLAYERS.computeIfAbsent(name, ServerPlayer::new);
    }

    public static ServerPlayer getPlayer(String player){
        return PLAYERS.getOrDefault(player, null);
    }

    @Override
    public BasePlayer resolvePlayer(String name){
        return PLAYERS.get(name);
    }

}
