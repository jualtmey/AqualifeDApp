pragma solidity ^0.4.23;

contract Broker {

    struct Client {
        uint id;
        address left;
        address right;
    }

    // funktioniert nicht als Parameter für Funktionen
    // UND "infinite" Gas Verbrauch entsteht ?
    struct Fish {
        string id;
        int x;
        int y;
        int direction;
    }

    event Register(
        address indexed recipient,
        uint id
    );

    event HandoffFish(
        address indexed recipient,
        string id,
        int x,
        int y,
        int direction
    );

    int constant LEFT = -1;
    int constant RIGHT = 1;

    mapping (address => Client) public clients;

    uint public clientIdCounter = 1;
    uint public size;
    address public firstClient;
    address public lastClient;

    function register() public {
        if (size == 0) {
            firstClient = msg.sender;
            lastClient = msg.sender;
        }

        clients[msg.sender] = Client(clientIdCounter, lastClient, firstClient);
        clients[firstClient].left = msg.sender;
        clients[lastClient].right =msg.sender;

        lastClient = msg.sender;

        emit Register(msg.sender, clientIdCounter);

        clientIdCounter++;
        size++;
    }

    function deregister() public {
        Client storage client = clients[msg.sender];

        if (msg.sender == firstClient) {
            firstClient = client.right;
        }
        if (msg.sender == lastClient) {
            lastClient = client.left;
        }

        clients[client.left].right = client.right;
        clients[client.right].left = client.left;
        delete clients[msg.sender]; // nötig?

        size--;
    }

    function handoffFish(string id, int x, int y, int direction) public {
        // TODO: nur wenn Client registriert ist bzw. size > 0

        if (direction == LEFT) {
            emit HandoffFish(clients[msg.sender].left, id, x, y, direction);
        } else { // right
            emit HandoffFish(clients[msg.sender].right, id, x, y, direction);
        }
    }

}