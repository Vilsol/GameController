# Examples

## Server

```java
import me.vilsol.gamecontroller.server.Server;
import me.vilsol.gamecontroller.server.core.Player;
import me.vilsol.gamecontroller.server.core.PlayerManager;

import java.awt.event.KeyEvent;

public class ServerExample {

    public static void main(String[] args){
        Player playerOne = PlayerManager.newPlayer("playerOne");

        playerOne.onKey('a', keyAction -> System.out.println("a was: " + keyAction));
        playerOne.onKey('A', keyAction -> System.out.println("A was: " + keyAction));

        playerOne.onKey(KeyEvent.VK_ENTER, SampleData.class, (keyAction, payload) -> {
            System.out.println("Enter was: " + keyAction + ", with message: " + payload.getMessage());
        });

        Server.start(8080);
    }

    public static class SampleData {

        private String message;

        public String getMessage(){
            return message;
        }
        
    }

}
```

Extended example [here](/java/examples/ServerExample.java)

## Client

```java
import me.vilsol.gamecontroller.client.Player;

public class ClientExample {

    public static void main(String[] args){
        Player playerOne = new Player("playerOne", "ws://localhost:8080/socket");
        playerOne.clickKeys(new SampleData("Hello World!"), "enter", "a", "A");
    }

    public static class SampleData {

        private String message;

        public SampleData(String message){
            this.message = message;
        }

    }

}
```

Extended example [here](/java/examples/ClientExample.java)

## TODO

* Add mouse support
* Add event support