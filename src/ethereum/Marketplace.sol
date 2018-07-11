pragma solidity ^0.4.24;

import "./Broker.sol";
import "./FishBase.sol";
import "./Ownable.sol";

contract Marketplace is Ownable {

    // === STRUCTS ===

    struct Offering {
        address seller;
        uint256 price;
    }

    // === EVENTS ===

    event Offer(
        address indexed seller,
        uint256 indexed tokenId,
        uint256 price
    );

    event Sale(
        address indexed seller,
        address indexed buyer,
        uint256 indexed tokenId,
        uint256 price
    );

    event Cancellation(
        uint256 indexed tokenId
    );

    // === STORAGE ===

    mapping (uint256 => Offering) public saleOf;
    uint256 private numberOfSales;

    uint public newFishPrice = 1e17; // in Wei (1e18 Wei = 1 Ether)

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

        // price update of an existing offer
        if (!isForSale(_tokenId)) {
            numberOfSales++;
        }

        saleOf[_tokenId] = Offering(msg.sender, _price);

        emit Offer(msg.sender, _tokenId, _price);
    }

    function cancelFishOffer(uint256 _tokenId) external {
        require(saleOf[_tokenId].seller == msg.sender);

        emit Cancellation(_tokenId);

        delete saleOf[_tokenId];
        numberOfSales--;
    }

    function buyNewFish(string _name) external costs(newFishPrice) payable {
        fishBase.mintToken(msg.sender, _name);
    }

    function buyFish(uint256 _tokenId) external payable {
        Offering storage sale = saleOf[_tokenId];

        require(isForSale(_tokenId), "Token is not for sale.");
        require(msg.value >= sale.price, "Not enough money.");
        // require(broker.tokenIdToCurrentTank(_tokenId) == msg.sender);

        fishBase.transferFrom(sale.seller, msg.sender, _tokenId);
        sale.seller.transfer(sale.price);

        emit Sale(sale.seller, msg.sender, _tokenId, sale.price);

        delete saleOf[_tokenId];
        numberOfSales--;
    }

    function isForSale(uint256 _tokenId) public view returns (bool) {
        return saleOf[_tokenId].seller != address(0);
    }

    function listAllSales() external view returns (uint256[]) {
        uint256[] memory sales = new uint256[](numberOfSales);
        uint256 maxTokenId = fishBase.totalSupply();

        uint256 resultIndex = 0;
        for (uint256 tokenId = 0; tokenId < maxTokenId; tokenId++) {
            if (isForSale(tokenId)) {
                sales[resultIndex] = tokenId;
                resultIndex++;
            }
        }

        return sales;
    }

    function transferContractBalanceToOwner() external onlyOwner {
        owner.transfer(address(this).balance);
    }

    function setBroker(address _newAddress) external onlyOwner {
        broker = Broker(_newAddress);
    }

    function setFishBase(address _newAddress) external onlyOwner {
        fishBase = FishBase(_newAddress);
    }

}