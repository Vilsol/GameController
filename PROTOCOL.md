# Protocol Spec

The protocol follows a very simple structure. All messages sent over the WS are an integer (the message type) followed by a json structure (the message).

The available message types are:

* 0: Keyboard
* 1: Mouse
* 2: Payload
* 3: Event

## Special Keys

There are multiple special keys. The server will convert any special keys to lowercase for compatibility.

* Enter
* Space
* Shift
* Control
* Alt
* AltGr
* Capslock
* Tab
* Delete
* Escape
* F1 to F24
* Backspace
* PrtSc
* ScrLk
* Pause
* Insert
* Delete
* PageUp
* PageDown
* Home
* End
* Up
* Down
* Left
* Right

## Messages

### Keyboard Message

```json
{
    "player": "string",          # Player name
    "actions": [                 # Array of action objects
        {
            "action": "string",  # One of PRESSED, RELEASED or CLICKED
            "payload": "string", # Serialized JSON payload (can be omitted)
            "keys": []           # Array of keys as single characters or special names
        }
    ]
}
```

### Mouse Message

```json
{
    "player": "string",  # Player name
    "action": "string",  # One of PRESSED, RELEASED, CLICKED or MOVED
    "position": {        # Position of the action (can be omitted)
        "type": "string" # One of ABSOLUTE or REALTIVE
        "x": "integer"   # X Coordinate (widthwise)
        "y": "integer"   # Y Coordinate (heightwise)
    }
    "payload": "string"  # Serialized JSON payload (can be omitted)
}
```

### Payload Message

```json
{
    "player": "string",      # Player name
    "payloadType": "string", # Custom payload type
    "payload": "string"      # Serialized JSON payload
}
```

### Event Message

```json
{
    "player": "string", # Player name
    "event": "string",  # Event type
    "payload": "string" # Serialized JSON payload (can be omitted)
}
```