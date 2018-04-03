using System;
using System.Collections.Generic;
using CSharp;

namespace GameControllerUnitTest
{
    class GameControllerExample
    {
        static void Main(string[] args)
    {

            
        GameController cont = new GameController();
        TestingGame myTestGame = new TestingGame();
        TestingAI myAI1 = new TestingAI("1");
        TestingAI myAI2 = new TestingAI("2");
        TestingAI myAI3 = new TestingAI("3");
        TestingAI myAI4 = new TestingAI("4");
        TestingAI myAI5 = new TestingAI("5");
        List<Message> curMessage = new List<Message>();

        for (int i = 0; i < 100; i++)
        {
            curMessage.AddRange(myTestGame.getOutput(curMessage.ToArray()));
            curMessage.AddRange(myAI1.getOutput(curMessage.ToArray()));
            curMessage.AddRange(myAI2.getOutput(curMessage.ToArray()));
            curMessage.AddRange(myAI3.getOutput(curMessage.ToArray()));
            curMessage.AddRange(myAI4.getOutput(curMessage.ToArray()));
            curMessage.AddRange(myAI5.getOutput(curMessage.ToArray()));
            List<string> messageJson = new List<string>();
            foreach (Message c in curMessage)
            {
                messageJson.Add(cont.MessageToJson(c));
            }

            curMessage.Clear();
            foreach (string c in messageJson)
            {
                Console.WriteLine(c);
                curMessage.Add(cont.JsonToMessage(c));
            }
            List<string> secondTryToJson = new List<string>();
            foreach (Message c in curMessage)
            {
                secondTryToJson.Add(cont.MessageToJson(c));
            }
            string[] firstTry = messageJson.ToArray();
            string[] secondTry = secondTryToJson.ToArray();

            if (firstTry.Length != secondTry.Length)
            {
                Console.WriteLine("Something weird happened: first try and second try are not equal length.");

            }

            for (int b = 0; b < firstTry.Length - 1; b++)
            {
                if (firstTry[b] == secondTry[b])
                {
                    Console.WriteLine("It worked this time!");
                }
            }


        }


        Console.ReadKey();

    }

    //Generates stochastic EventMessages and PayloadMessages
    public class TestingGame
    {
        EventMessage myEventMessage;
        PayloadMessage myPayloadMessage;
        Random random;
        public TestingGame()
        {
            myEventMessage = new EventMessage();
            myPayloadMessage = new PayloadMessage();
            random = new Random();
        }

        private PayloadMessage getPayLoadMessage()
        {
            myPayloadMessage.player = Convert.ToString(random.Next(1, 10));
            myPayloadMessage.payloadType = Convert.ToString(random.Next(100));
            myPayloadMessage.payload = "PAYLOAD";
            return myPayloadMessage;
        }

        private EventMessage getEventMessage()
        {
            myEventMessage.player = Convert.ToString(random.Next(1, 10));
            myEventMessage.m_event = Convert.ToString(random.Next(1, 100));
            myEventMessage.payload = "PAYLOAD";
            return myEventMessage;
        }

        public Message[] getOutput(Message[] AIMessages)
        {
            List<Message> theseMessages = new List<Message>();
            for (int i = 0; i < random.Next(1, 25); i++)
            {
                switch (random.Next(0, 1))
                {
                    case 0:
                        theseMessages.Add(getEventMessage());
                        break;

                    case 1:
                        theseMessages.Add(getPayLoadMessage());
                        break;
                }
            }

            return theseMessages.ToArray();
        }

    }

    private class TestingAI
    {
        Position myPos;
        Actions myActions;
        Random random;
        KeyboardMessage myKeyboardMessage;
        MouseMessage myMouseMessage;
        string myPlayer;

        public TestingAI(string playerNum)
        {
            myPos = new Position();
            myActions = new Actions();
            random = new Random();
            myKeyboardMessage = new KeyboardMessage();
            myMouseMessage = new MouseMessage();
            myPlayer = playerNum;
        }

        private Position GetPos()
        {
            string[] options = { "ABSOLUTE", "REALITIVE" };
            myPos.setPosition(options[random.Next(0, 1)], random.Next(0, 1080), random.Next(0, 1000));
            return myPos;
        }

        private Actions GetActions()
        {
            string[] options = { "PRESSED", "RELEASED", "CLICKED" };
            List<Keys> keys = new List<Keys>();
            for (int i = 0; i < random.Next(25); i++)
            {
                keys.Add(GetKey());
            }
            myActions.setActions(options[random.Next(0, 2)], "PAYLOAD", keys.ToArray());

            return myActions;
        }
        private Actions[] GetActions(int i)
        {
            List<Actions> theseActions = new List<Actions>();
            for (int c = 0; c < i; c++)
            {
                theseActions.Add(GetActions());
            }
            return theseActions.ToArray();
        }

        private Keys GetKey()
        {
            char[] chars = { 'z', 'x', 'c', 'v', 'b', 'n', 'm', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

            switch (random.Next(0, 1))
            {
                case 0:
                    return new Keys(chars[random.Next(chars.Length - 1)]);

                case 1:
                    return new Keys(Keys.SpecialKeys()[random.Next(Keys.SpecialKeys().Length - 1)]);

                default:
                    return null;
            }

        }

        public Message[] getOutput(Message[] gameMessages)
        {
            List<Message> myMessages = new List<Message>();
            string[] mouseOptions = { "PRESSED", "RELEASED", "CLICKED", "MOVED" };

            for (int i = 0; i < random.Next(25); i++)
            {
                switch (random.Next(0, 1))
                {
                    case 0:
                        myKeyboardMessage.player = myPlayer;
                        myKeyboardMessage.actions = GetActions(random.Next(1, 25));
                        myMessages.Add(myKeyboardMessage);
                        break;

                    case 1:
                        myMouseMessage.player = myPlayer;
                        myMouseMessage.action = mouseOptions[random.Next(3)];
                        myMouseMessage.position = GetPos();
                        myMouseMessage.payload = "PAYLOAD";
                        break;
                }

            }
            return myMessages.ToArray();


        }

    }



    private class FakeAI
    {
        Position myPos = new Position();
        public string getKey()
        {
            return "a";
        }

        public Position getMousePos()
        {
            myPos.setPosition("ABSOLUTE", 3, 4);
            return myPos;
        }

        private void sendInput(Message[] input)
        {
            GameController myTestControll = new GameController();
            foreach (Message c in input)
            {
                Console.WriteLine("AI Input: " + myTestControll.MessageToJson(c));
            }
        }

        private Message[] getOutput()
        {
            Position myPos = new Position();
            Actions myActions = new Actions();
            myPos.setPosition("ABSOLUTE", 3, 4);
            MouseMessage myMessage = new MouseMessage();
            myMessage.player = "1";
            myMessage.position = myPos;
            myMessage.action = "PRESSED";
            myMessage.payload = "PAYLOAD";
            List<Keys> KeysEntered = new List<Keys>();

            KeysEntered.Add(new Keys('a'));
            KeysEntered.Add(new Keys("Right"));
            KeysEntered.Add(new Keys('b'));

            myActions.setActions("PRESSED", "PAYLOAD", KeysEntered.ToArray());
            Actions mySecondActions = new Actions();
            mySecondActions.setActions("RELEASED", "PAYLOAD12", KeysEntered.ToArray());
            KeyboardMessage myKeyboardMessage = new KeyboardMessage();
            myKeyboardMessage.player = "1";
            myKeyboardMessage.actions = new Actions[] { myActions, mySecondActions };
            return new Message[] { myKeyboardMessage, myMessage };
        }



        public Message[] NextFrame(Message[] input)
        {
            this.sendInput(input);

            return getOutput();
        }

    }


}
}
