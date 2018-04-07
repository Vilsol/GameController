import me.vilsol.gamecontroller.client.ClientPlayer;
import me.vilsol.gamecontroller.common.mouse.MousePositionType;

public class ClientExample {

    public static void main(String[] args){
        ClientPlayer playerOne = new ClientPlayer("playerOne", "ws://localhost:8080/socket");

        playerOne.onPayload("ping", PingMessage.class, pingMessage -> {
            System.out.println("Received Ping: " + pingMessage.ping);

            playerOne.sendPayload("pong", new PongMessage("PONG!"));
        });

        playerOne.onEvent("frame", null, (payload) -> {
            System.out.println("New Frame!");

            playerOne.moveMouse(MousePositionType.RELATIVE, 123, 456, null);
        });

        playerOne.clickKeys(new SampleData("Hello World!"), "enter", "a", "A");
    }

    public static class SampleData {

        private String message;

        public SampleData(String message){
            this.message = message;
        }

    }

    public static class PingMessage {

        private String ping;

    }

    public static class PongMessage {

        private String pong;

        public PongMessage(String pong){
            this.pong = pong;
        }

    }

}
