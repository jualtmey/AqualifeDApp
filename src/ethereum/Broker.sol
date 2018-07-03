pragma solidity ^0.4.24;

import "./Ownable.sol";
import "./FishBase.sol";

contract Broker is Ownable {

    // === STRUCTS ===

    struct Client {
        uint256 left;
        uint256 right;
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

    uint256 constant INACTIVE_AFTER_BLOCKS = 10;

    int constant DIRECTION_LEFT = -1;
    int constant DIRECTION_RIGHT = 1;

    // === STORAGE ===

    Client[] public clients;

    mapping (address => uint256) public tankIdOf;
    mapping (uint256 => address) public addressOf;
    mapping (uint256 => uint256) public tokenIdToCurrentTank;
    mapping (uint256 => uint256) public tokensInTank;

    mapping (uint256 => uint256) public lastHandoff; // block number

    uint256 public firstClient;
    uint256 public lastClient;
    uint256 public size;

    FishBase public fishBase;

    // === MODIFIER ===

    modifier whenRegistered() {
        require(clients[tankIdOf[msg.sender]].registered, "You are not registered.");
        _;
    }

    modifier whenNotRegistered() {
        require(!clients[tankIdOf[msg.sender]].registered, "You are already registered.");
        _;
    }

    modifier holds(uint256 _tokenId) {
        require(tokenIdToCurrentTank[_tokenId] == tankIdOf[msg.sender], "Fish isn't in your tank.");
        _;
    }

    // === CONSTRUCTOR ===

    constructor() public {
        clients.push(Client(0, 0, false));
    }

    // === FUNCTIONS ===

    function register() public whenNotRegistered {
        uint256 tankId = tankIdOf[msg.sender];

        // client registers for the first time
        if (tankId == 0) {
            tankId = clients.push(Client(0, 0, false)) - 1;
            tankIdOf[msg.sender] = tankId;
            addressOf[tankId] = msg.sender;
        }

        if (size == 0) {
            firstClient = tankId;
            lastClient = tankId;
        }

        clients[tankId] = Client(lastClient, firstClient, true);

        clients[firstClient].left = tankId;
        clients[lastClient].right = tankId;

        lastClient = tankId;

        emit Register(msg.sender, tankId);

        size++;
    }

    function deregister() external whenRegistered {
        _deregister(tankIdOf[msg.sender]);
    }

    function deregister(uint256 _tankId) external {
        require(isInactive(_tankId), "Only if client is inactive.");
        require(clients[_tankId].registered, "Only if client is registered.");
        _deregister(_tankId);
    }

    function _deregister(uint256 _tankId) private {
        Client storage client = clients[_tankId];

        if (_tankId == firstClient) {
            firstClient = client.right;
        }
        if (_tankId == lastClient) {
            lastClient = client.left;
        }

        clients[client.left].right = client.right;
        clients[client.right].left = client.left;
        client.registered = false;

        size--;
    }

    function handoffFish(uint256 _tokenId, int _y, int8 _direction) external
    whenRegistered holds(_tokenId) {
        // TODO: check whether tokenId exists?
        // TODO: direction and y could be manipulated by malicious clients (e.g. two adjacent clients hold all fishies by sending them left/right)
        uint256 fromTankId = tankIdOf[msg.sender];
        uint256 toTankId;

        if (_direction == DIRECTION_LEFT) {
            toTankId = clients[fromTankId].left;
        } else { // right
            toTankId = clients[fromTankId].right;
        }

        lastHandoff[fromTankId] = block.number;
        if (tokensInTank[toTankId] == 0) {
            lastHandoff[toTankId] = block.number;
        }

        tokenIdToCurrentTank[_tokenId] = toTankId;
        tokensInTank[fromTankId]--;
        tokensInTank[toTankId]++;

        emit HandoffFish(addressOf[toTankId], _tokenId, _y, _direction);
    }

    function summonFish(uint256 _tokenId) external whenRegistered {
        uint256 senderTank = tankIdOf[msg.sender];
        uint256 currentTank = tokenIdToCurrentTank[_tokenId];

        require(currentTank != senderTank, "Tank holds fish already.");
        require(senderTank == tankIdOf[fishBase.ownerOf(_tokenId)] || isInactive(currentTank));

        if (currentTank != 0) {
            tokensInTank[currentTank]--;
        }
        tokenIdToCurrentTank[_tokenId] = senderTank;
        tokensInTank[senderTank]++;

        lastHandoff[senderTank] = block.number;

        emit SummonFish(msg.sender, _tokenId);
    }

    function getAllTokensInTank(uint256 _tankId) external view returns (uint256[]) {
        uint256 tokenCount = tokensInTank[_tankId];

        if (tokenCount == 0) {
            return new uint256[](0);
        } else {
            uint256[] memory result = new uint256[](tokenCount);
            uint256 totalFishToken = fishBase.totalSupply();
            uint256 resultIndex = 0;

            for (uint256 tokenId = 0; tokenId < totalFishToken; tokenId++) {
                if (tokenIdToCurrentTank[tokenId] == _tankId) {
                    result[resultIndex] = tokenId;
                    resultIndex++;
                }
            }

            return result;
        }
    }

    function isInactive(uint256 _tankId) public view returns (bool) {
        if (tokensInTank[_tankId] > 0) {
            if (lastHandoff[_tankId] + INACTIVE_AFTER_BLOCKS < block.number) {
                return true;
            }
        }

        return false;
    }

    function setFishBase(address _newAddress) external onlyOwner {
        fishBase = FishBase(_newAddress);
    }

}