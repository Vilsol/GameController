package me.vilsol.gamecontroller.server;

import spark.Spark;

public class Server {

    public static void start(int port){
        Spark.port(port);
        Spark.webSocket("/socket", WebsocketHandler.class);
        Spark.init();
    }

}
