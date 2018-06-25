pragma solidity ^0.4.24;

import "./Broker.sol";
import "./FishBase.sol";
import "./Ownable.sol";

contract Marketplace is Ownable {

    // === STRUCTS ===

    struct Sale {
        address seller;
        uint256 price;
    }

    mapping (uint256 => Sale) public saleOf;

    uint public newFishPrice = 0; // 1e18; // in Wei (1e18 Wei = 1 Ether)

    Broker public broker;
    FishBase public fishBase;

    // === MODIFIER ===

    modifier costs(uint price) {
        require(msg.value >= price, "Not enough money.");
        _;
    }

    // === CONSTRUCTOR ===

    constructor() public {

    }

    // === FUNCTIONS ===

    function offerFish(uint256 _tokenId, uint256 _price) external {
        require(fishBase.ownerOf(_tokenId) == msg.sender);

        saleOf[_tokenId] = Sale(msg.sender, _price);
    }

    function removeFishSell(uint256 _tokenId) public {
        require(saleOf[_tokenId].seller == msg.sender);

        delete saleOf[_tokenId];
    }

    function buyNewFish(string _name) external costs(newFishPrice) payable {
        fishBase.mintToken(msg.sender, _name);
    }

    function buyFish(uint256 _tokenId) external payable {
        Sale storage sale = saleOf[_tokenId];

        require(fishBase.getApproved(_tokenId) == address(this));
        require(sale.seller != address(0));
        require(msg.value >= sale.price);
        require(broker.tokenIdToCurrentTank(_tokenId) == msg.sender);

        fishBase.transferFrom(sale.seller, msg.sender, _tokenId);
        sale.seller.transfer(sale.price);

        delete saleOf[_tokenId];
    }

    function setBroker(address _newAddress) external onlyOwner {
        broker = Broker(_newAddress);
    }

    function setFishBase(address _newAddress) external onlyOwner {
        fishBase = FishBase(_newAddress);
    }

}