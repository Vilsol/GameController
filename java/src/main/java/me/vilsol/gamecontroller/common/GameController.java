package me.vilsol.gamecontroller.common;

public class GameController {

    private static PlayerResolver playerResolver;

    public static BasePlayer resolvePlayer(String name){
        if(playerResolver == null){
            throw new RuntimeException("Player resolver not set");
        }

        return playerResolver.resolvePlayer(name);
    }

    public static void setPlayerResolver(PlayerResolver playerResolver){
        GameController.playerResolver = playerResolver;
    }

}
