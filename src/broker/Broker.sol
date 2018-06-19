pragma solidity ^0.4.24;

import "./Ownable.sol";
import "./FishBase.sol";

contract Broker is Ownable {

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
        int y,
        int8 direction
    );

    event SummonFish(
        address indexed recipient,
        uint256 tokenId
    );

    // === CONSTANTS ===

    int constant DIRECTION_LEFT = -1;
    int constant DIRECTION_RIGHT = 1;

    // === STORAGE ===

    mapping (address => Client) public clients;
    mapping (uint256 => address) public tokenIdToCurrentTank;

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

    modifier holds(uint256 tokenId) {
        require(tokenIdToCurrentTank[tokenId] == msg.sender, "Fish isn't in your tank.");
        _;
    }

    // === CONSTRUCTOR ===

    constructor() public {
        // fishBase = new FishBase(address(this));
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

    function handoffFish(uint256 _tokenId, int _y, int8 _direction) external whenRegistered holds(_tokenId) {
        // TODO: check whether tokenId exists?
        // TODO: direction and y could be manipulated by malicious clients (e.g. two adjacent clients hold all fishies by sending them left/right)
        address to;

        if (_direction == DIRECTION_LEFT) {
            to = clients[msg.sender].left;
        } else { // right
            to = clients[msg.sender].right;
        }

        tokenIdToCurrentTank[_tokenId] = to;

        emit HandoffFish(to, _tokenId, _y, _direction);
    }

    function summonFish(uint256 _tokenId) external whenRegistered {
        require(tokenIdToCurrentTank[_tokenId] != msg.sender, "Tank holds fish already.");
        // require(msg.sender == fishBase.ownerOf(_tokenId), "For owner of this fish only.");

        tokenIdToCurrentTank[_tokenId] = msg.sender;

        emit SummonFish(msg.sender, _tokenId);
    }

    function setFishBase(address _newAddress) external onlyOwner {
        fishBase = FishBase(_newAddress);
    }

}