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
        Direction direction;
    }
    
    enum Direction {
        left,
        right
    }
    
    event HandoffFishEvent(
        address recipient,
        string id,
        int x,
        int y,
        Direction d
    );
    
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
    
    function handoffFish(string id, int x, int y, Direction d) public {
        // TODO: nur wenn Client registriert ist bzw. size > 0
        
        if (d == Direction.left) {
            emit HandoffFishEvent(clients[msg.sender].left, id, x, y, d);
        } else { // right
            emit HandoffFishEvent(clients[msg.sender].right, id, x, y, d);
        }
    }
    
}