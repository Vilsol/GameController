# Examples

## Server

```C#
using GameController;

public class GameControllerPlugInExample {

    public static class ServerThreadExample {

        private String message;
        GameContoller myGameController = new GameController();
        public void ProcessMessage(string clientMessage)
        {
            Message clientsMessage = myGameController.JsonToMessage(clientMessage);
            GameController.GiveMessage(Message clientJsonToMessage);
        }


    }

}
```



## TODO

* Add mouse support
* Add event support
