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
        uint32 fishUniqueData,
        uint256 ownerTankId,
        int y,
        int8 direction
    );

    // === CONSTANTS ===

    int constant LEFT = -1;
    int constant RIGHT = 1;

    // === STORAGE ===

    mapping (address => Client) public clients;

    uint public clientIdCounter = 1;
    uint public size;
    address public firstClient;
    address public lastClient;

    FishBase public fishBase;

    uint public createFishPrice = 1e18; // in Wei (1e18 Wei = 1 Ether)

    // === MODIFIER ===

    modifier whenRegistered {
        require(clients[msg.sender].registered, "You are not registered.");
        _;
    }

    modifier whenNotRegistered {
        require(!clients[msg.sender].registered, "You are already registered.");
        _;
    }

    modifier costs(uint price) {
        require(msg.value >= price, "Not enough money.");
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
            clientIdCounter++;
        }

        clients[msg.sender] = Client(tankId, lastClient, firstClient, true);
        clients[firstClient].left = msg.sender;
        clients[lastClient].right =msg.sender;

        lastClient = msg.sender;

        emit Register(msg.sender, tankId);

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

    function createFish(string _name) public payable whenRegistered costs(createFishPrice) {
        uint256 tokenId = fishBase.createFish(msg.sender, _name);

        (string memory fishName, uint32 fishUniqueData, ) = fishBase.getFishToken(tokenId);

        emit HandoffFish(msg.sender, tokenId, fishName, fishUniqueData, clients[msg.sender].tankId, 0, 0);
    }

    function handoffFish(uint256 _tokenId, int _y, int8 _direction) public whenRegistered {
        address to;

        if (_direction == LEFT) {
            to = clients[msg.sender].left;
        } else { // right
            to = clients[msg.sender].right;
        }

        fishBase.handoffFish(msg.sender, to, _tokenId);

        (string memory fishName, uint32 fishUniqueData, ) = fishBase.getFishToken(_tokenId);
        uint256 ownerTankId = clients[fishBase.ownerOf(_tokenId)].tankId;

        emit HandoffFish(to, _tokenId, fishName, fishUniqueData, ownerTankId, _y, _direction);
    }

    function summonFish(uint256 _tokenId) public whenRegistered {
        require(msg.sender == fishBase.ownerOf(_tokenId), "Only for owners of this fish.");

        fishBase.handoffFish(msg.sender, _tokenId);

        (string memory fishName, uint32 fishUniqueData, ) = fishBase.getFishToken(_tokenId);

        emit HandoffFish(msg.sender, _tokenId, fishName, fishUniqueData, clients[msg.sender].tankId, 0, 0);
    }

}