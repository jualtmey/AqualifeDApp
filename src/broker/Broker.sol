pragma solidity ^0.4.24;

import "./FishBase.sol";

contract Broker {

    // === STRUCTS ===

    struct Client {
        uint256 tankId;
        address left;
        address right;
        bool registered;
    }

    // === EVENTS ===

    event Register(
        address indexed recipient,
        uint256 tankId
    );

    event HandoffFish(
        address indexed recipient,
        uint256 tokenId,
        string fishName,
        uint256 ownerTankId,
        int y,
        int8 direction
    );

    // === CONSTANTS ===

    int constant LEFT = -1;
    int constant RIGHT = 1;

    // === STORAGE ===

    mapping (address => Client) public clients;
    mapping (address => uint256) public balanceOf;

    uint public clientIdCounter = 1;
    uint public size;
    address public firstClient;
    address public lastClient;

    FishBase public fishBase;

    // === MODIFIER ===

    modifier whenRegistered {
        require(clients[msg.sender].registered, "You are not registered.");
        _;
    }

    modifier whenNotRegistered {
        require(!clients[msg.sender].registered, "You are already registered.");
        _;
    }

    // === CONSTRUCTOR ===

    constructor() public {
        fishBase = new FishBase(address(this));
    }

    // === FUNCTIONS ===

    function register() public whenNotRegistered {
        if (size == 0) {
            firstClient = msg.sender;
            lastClient = msg.sender;
        }

        uint256 tankId = clients[msg.sender].tankId;
        if (tankId == 0) {
            tankId = clientIdCounter;
            balanceOf[msg.sender] = 5;
            clientIdCounter++;
        }

        clients[msg.sender] = Client(tankId, lastClient, firstClient, true);
        clients[firstClient].left = msg.sender;
        clients[lastClient].right =msg.sender;

        lastClient = msg.sender;

        emit Register(msg.sender, clientIdCounter);

        size++;
    }

    function deregister() public whenRegistered {
        Client storage client = clients[msg.sender];

        if (msg.sender == firstClient) {
            firstClient = client.right;
        }
        if (msg.sender == lastClient) {
            lastClient = client.left;
        }

        clients[client.left].right = client.right;
        clients[client.right].left = client.left;
        client.registered = false;

        size--;
    }

    function createFish(string name) public whenRegistered {
        uint256 fishPrice = 5;

        require(balanceOf[msg.sender] >= fishPrice, "Not enough money.");

        balanceOf[msg.sender] -= fishPrice;
        uint256 tokenId = fishBase.createFish(msg.sender, name);

        emit HandoffFish(msg.sender, tokenId, name, clients[msg.sender].tankId, 0, 0);
    }

    function handoffFish(uint256 _tokenId, int _y, int8 _direction) public whenRegistered {
        address to;

        if (_direction == LEFT) {
            to = clients[msg.sender].left;
        } else { // right
            to = clients[msg.sender].right;
        }

        fishBase.handoffFish(msg.sender, to, _tokenId);

        string memory fishName = fishBase.fishName(_tokenId);
        uint256 ownerTankId = clients[fishBase.ownerOf(_tokenId)].tankId;

        balanceOf[msg.sender]++;

        emit HandoffFish(to, _tokenId, fishName, ownerTankId, _y, _direction);
    }

    function summonFish(uint256 _tokenId) public whenRegistered {
        require(msg.sender == fishBase.ownerOf(_tokenId), "Only for owners of this fish.");

        fishBase.handoffFish(msg.sender, _tokenId);

        emit HandoffFish(msg.sender, _tokenId, fishBase.fishName(_tokenId), clients[msg.sender].tankId, 0, 0);
    }

}