using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace GameControllerCSharp
{

    /// <summary>
    /// Lib class for converting game data to Json for sending to server.
    /// Requires Newtonsoft.Json package.
    /// Possible future additions: Make ability to initialize pre-made messages for developers to initialize all valid messages for game from beginning.
    /// </summary>
    public class GameController
    {
        private KeyboardMessage m_KeyboardMessage;
        private MouseMessage m_MouseMessage;
        private PayloadMessage m_PayloadMessage;
        private EventMessage m_EventMessage;
        
        

        /// <summary>
        /// Basic Constructor to initialize GameController.
        /// </summary>
        public GameController()
        {
            m_KeyboardMessage = new KeyboardMessage();
            m_MouseMessage = new MouseMessage();
            m_PayloadMessage = new PayloadMessage();
            m_EventMessage = new EventMessage();
            
        }


        /// <summary>
        /// Returns string containing KeyboardMessageToJson data converted to Json
        /// </summary>
        /// <param name="player"></param>
        /// <param name="actions"></param>
        /// <returns></returns>
        public string KeyboardMessageToJson(string player, Actions[] actions)
        {
            m_KeyboardMessage.player = player;
            m_KeyboardMessage.actions = actions;

            return "0" + JsonConvert.SerializeObject(m_KeyboardMessage);
        }

        
        /// <summary>
        /// Returns string containing MouseMessage data converted to Json
        /// </summary>
        /// <param name="player"></param>
        /// <param name="action"></param>
        /// <param name="position"></param>
        /// <param name="payload"></param>
        /// <returns></returns>
        public string MouseMessageToJson(string player, string action, Position position, string payload)
        {
            m_MouseMessage.player = player;
            m_MouseMessage.action = action;
            m_MouseMessage.position = position;
            m_MouseMessage.payload = payload;

            return "1" + JsonConvert.SerializeObject(m_MouseMessage);
        }
        
        /// <summary>
        /// Returns string containing PayloadMessage data converted to Json
        /// </summary>
        /// <param name="player"></param>
        /// <param name="payloadType"></param>
        /// <param name="payload"></param>
        /// <returns></returns>
        public string PayloadMessageToJson(string player, string payloadType, string payload)
        {
            m_PayloadMessage.player = player;
            m_PayloadMessage.payloadType = payloadType;
            m_PayloadMessage.payload = payload;

            return "2" + JsonConvert.SerializeObject(m_PayloadMessage);
        }

        /// <summary>
        /// Returns string containing EventMessage data converted to Json
        /// </summary>
        /// <param name="player"></param>
        /// <param name="Event"></param>
        /// <param name="payload"></param>
        /// <returns></returns>
        public string EventMessageToJson(string player, string Event, string payload)
        {
            m_EventMessage.player = player;
            m_EventMessage.m_event = Event;
            m_EventMessage.payload = payload;
            return "3" + JsonConvert.SerializeObject(m_EventMessage);
        }

        /// <summary>
        /// Returns Json-formatted string of Message object.
        /// </summary>
        /// <param name="messageObj"></param>
        /// <returns></returns>
        public string EventMessageToJson(Message messageObj)
        {
            switch(messageObj.GetType().Name)
            {
                case "EventMessage" :
                    return "3" + JsonConvert.SerializeObject(messageObj);

                case "PayloadMessage":
                    return "2" + JsonConvert.SerializeObject(messageObj);
                case "MouseMessage":
                    return "1" + JsonConvert.SerializeObject(messageObj);
                case "KeyboardMessage":
                    return "0" + JsonConvert.SerializeObject(messageObj);
                default:
                    throw new Exception("Object is not a message type.");
            }
        }

        public object JsonToMessage(string message)
        {
            string messageType = message.Remove(0, 1);

            switch (messageType)
            {
                case "0":
                    return JsonConvert.DeserializeObject<KeyboardMessage>(message);

                case "1":
                    return JsonConvert.DeserializeObject<MouseMessage>(message);

                case "2":
                    
                    return JsonConvert.DeserializeObject<PayloadMessage>(message);
                    
                case "3":
                    return JsonConvert.DeserializeObject<EventMessage>(message);

                default:
                    throw new Exception("Invalid message type");
            }


        }

        

        

    }

    /// <summary>
    /// Data structure that contains string action, string payload, and string keys[] variables for Actions
    /// Use setActions() to set variables. 
    /// </summary>
    public class Actions
    {
        public string action { get; private set; }
        public string payload { get; private set; }
        public string[] keys { get; private set; }

        /// <summary>
        /// Copy constructor to set allocated space.
        /// Variables are set to "PRESSED", "EMPTY", and {"a"} respectively.
        /// </summary>
        public Actions()
        {
            action = "PRESSED";
            payload = "EMPTY";
            keys = new string[]{ "a"};
        }

        /// <summary>
        /// Constructor overload to initialize object with variables.
        /// Action must equal "PRESSED", "RELEASED", or "CLICKED".
        /// </summary>
        /// <param name="Action"></param>
        /// <param name="Payload"></param>
        /// <param name="Keys"></param>
        public Actions(string Action, string Payload, Keys[] Keys)
        {
            if (Action.ToUpper() != "PRESSED" && Action.ToUpper() != "RELEASED" && Action.ToUpper() != "CLICKED")
            {
                throw new Exception("Incorrect action entered.");
            }
            else
            {
                action = Action;
                payload = Payload;
                keys = new string[Keys.Length];
                for(int i = 0; i < Keys.Length; i++)
                {
                    keys[i] = Keys[i].key;
                }
            }

        }

        /// <summary>
        /// Setter for Actions variables.
        /// Action must equal "PRESSED", "RELEASED", or "CLICKED".
        /// </summary>
        /// <param name="Action"></param>
        /// <param name="Payload"></param>
        /// <param name="Keys"></param>
        public void setActions(string Action, string Payload, Keys[] Keys)
        {
            if (Action.ToUpper() != "PRESSED" && Action.ToUpper() != "RELEASED" && Action.ToUpper() != "CLICKED")
            {
                throw new Exception("Incorrect action entered.");
            }
            else
            {
                action = Action;
                payload = Payload;
                keys = new string[Keys.Length];
                for (int i = 0; i < Keys.Length; i++)
                {
                    keys[i] = Keys[i].key;
                }
            }
        }
    }

    /// <summary>
    /// Data structure that contains type, x-pos, and y-pos to format mouse data into Json.
    /// Use setPosition(string, int, int) to set variables.
    /// </summary>
    public class Position
    {
        private string m_Type;

        public string type {
            get { return m_Type; }
            private set
            {
                if (value.ToUpper() == "ABSOLUTE" || value.ToUpper() == "REALTIVE")
                {
                    m_Type = value.ToUpper();
                }

                else
                {
                    throw new Exception("Incorrect position type entered.");

                }
            }
        }
        /// <summary>
        /// x-coordinate integer of mouse stored as string.
        /// Use setPosition() to change.
        /// </summary>
        public string x { get; private set; }
        
        /// <summary>
        /// y-coordinate integer of mouse stored as string.
        /// Use setPosition() to change.
        /// </summary>
        public string y { get; private set; }

        /// <summary>
        /// Sets Position variables to respective inputs.
        /// newType must equal "ABSOLUTE" or "REALTIVE"
        /// </summary>
        /// <param name="newType"></param>
        /// <param name="X"></param>
        /// <param name="Y"></param>
        public void setPosition(string newType, int X, int Y)
        {
            type = newType;
            x = Convert.ToString(X);
            y = Convert.ToString(Y);
        }
    }

    /// <summary>
    /// Object for inheritance. All Message objects inherit Message.
    /// </summary>
    public class Message
    {

    }

    /// <summary>
    /// Data structure for Keyboard Message
    /// Contains string player and Actions[] actions.
    /// </summary>
    public class KeyboardMessage : Message
    {
        public string player { get; set; }
        public Actions[] actions { get; set; }

        
       
    }
    /// <summary>
    /// Data structure for EventMessage.
    /// Contains string player, string event, and string payload.
    /// </summary>
    public class EventMessage : Message 
    {
        public string player { get; set; }

        [JsonProperty("event")]
        public string m_event { get; set; }
        public string payload { get; set; }
    }

    /// <summary>
    /// Data structure for MouseMessage.
    /// Contains string player, string action, Position position, and string payload.
    /// </summary>
    public class MouseMessage : Message
    {
        private string m_action;
        public string player { get; set; }
        public string action
        {
            get
            {
                return m_action;
            }
            set
            {
                if (value.ToUpper() == "PRESSED" || value.ToUpper() == "RELEASED" || value.ToUpper() == "CLICKED" || value.ToUpper() == "MOVED")
                {
                    m_action = value.ToUpper();
                }
                else
                {
                    throw new Exception("Incorrect action entered.");
                }
            }
        }
        public Position position { get; set; }
        public string payload { get; set; }
    }
     
    /// <summary>
    /// Data structure for Payload Message.
    /// Contains string player, string payloadType, and string payload.
    /// </summary>
    public class PayloadMessage : Message
    {
        public string player { get; set; }
        public string payloadType { get; set; }
        public string payload { get; set; }
    }

    /// <summary>
    /// Data structure to hold string for key used by GameController.
    /// Features two overloads: (string) or (char).
    /// string overload is for Special Keys, char overload is for normal keys.
    /// Valid input for special keys is defined in static string[] SpecialKeys.
    /// Valid input for normal keys is any char.
    /// </summary>
    public class Keys
    {
        /// <summary>
        /// string variable that contains the entered key.
        /// Use setKey() to set key.
        /// </summary>
        public string key { get; private set; }

        /// <summary>
        /// Constructor for creating a key for a Special Key
        /// </summary>
        /// <param name="Key"></param>
        public Keys(string Key)
        {
            if (SpecialKeys().Contains<string>(Key.ToLower()))
            {
                key = Key.ToLower();
            }
            else
            {
                throw new Exception("Incorrect special key entered.");
            }
        }

        /// <summary>
        /// Basic constructor. Key is instantiated at "a". 
        /// </summary>
        public Keys()
        {
            key = "a";
        }

        /// <summary>
        /// Keys constructor for entering a normal key.
        /// char is converted to string.
        /// </summary>
        /// <param name="Key"></param>
        public Keys(char Key)
        {
            key = Convert.ToString(Key);
        }

        /// <summary>
        /// Keys setter for entering a normal key.
        /// char is converted to string.
        /// </summary>
        /// <param name="Key"></param>
        public void setKey(char Key)
        {
            key = Convert.ToString(Key);
        }

        /// <summary>
        /// Keys setter for entering a special key.
        /// Must be contained in string SpecialKeys to be valid.
        /// </summary>
        /// <param name="Key"></param>
        public void setKey(string Key)
        {
            if (SpecialKeys().Contains<string>(Key.ToLower()))
            {
                key = Key.ToLower();
            }
            else
            {
                throw new Exception("Incorrect special key entered.");
            }
        }

        /// <summary>
        /// static string that defines the only valid Special Keys.
        /// </summary>
        /// <returns></returns>
        public static string[] SpecialKeys()
        {
            return new string[]
            {
            "enter",
            "space",
            "shift",
            "control",
            "alt",
            "altgr",
            "capslock",
            "tab",
            "delete",
            "escape",
            "f1",
            "f2",
            "f3",
            "f4",
            "f5",
            "f6",
            "f7",
            "f8",
            "f9",
            "f10",
            "f11",
            "f12",
            "f13",
            "f14",
            "f15",
            "f16",
            "f17","f18",
            "f19",
            "f20",
            "f21",
            "f22",
            "f23",
            "f24",
            "backspace",
            "prtsc",
            "pause",
            "insert",
            "delete",
            "pageup",
            "pagedown",
            "home",
            "end",
            "up",
            "down",
            "left",
            "right"
            };
        }
    }


    
    

}
