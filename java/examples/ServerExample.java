import me.vilsol.gamecontroller.common.messages.MouseMessage;
import me.vilsol.gamecontroller.server.Server;
import me.vilsol.gamecontroller.server.core.ServerPlayer;
import me.vilsol.gamecontroller.server.core.PlayerManager;

import java.awt.event.KeyEvent;

public class ServerExample {

    public static void main(String[] args){
        ServerPlayer playerOne = PlayerManager.newPlayer("playerOne");

        playerOne.onKey('a', keyAction -> System.out.println("a was: " + keyAction));
        playerOne.onKey('A', keyAction -> System.out.println("A was: " + keyAction));

        playerOne.onKey(KeyEvent.VK_ENTER, SampleData.class, (keyAction, payload) -> {
            System.out.println("Enter was: " + keyAction + ", with message: " + payload.message);

            playerOne.sendPayload("ping", new PingMessage("PING!"));
        });

        playerOne.onPayload("pong", PongMessage.class, pongMessage -> {
            System.out.println("Received Pong: " + pongMessage.pong);

            playerOne.sendEvent("frame", null);
        });

        playerOne.onMouse(null, ((message, payload) -> {
            MouseMessage.Position position = message.getPosition();
            System.out.println("Mouse was " + message.getAction() + " to/at " + position.getType() + " " + position.getX() + "," + position.getY());
        }));

        Server.start(8080);
    }

    public static class SampleData {

        private String message;

    }

    public static class PingMessage {

        private String ping;

        public PingMessage(String ping){
            this.ping = ping;
        }

    }

    public static class PongMessage {

        private String pong;

    }

}
