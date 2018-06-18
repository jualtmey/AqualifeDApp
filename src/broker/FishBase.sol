pragma solidity ^0.4.24;

import "./ERC721.sol";
import "./ERC721Metadata.sol";

contract FishBase is ERC721, ERC721Metadata {

    // === STRUCTS ===

    struct FishToken {
        string name;
        uint32 uniqueData; // color information of fish
        address currentTank;
    }

    // === CONSTANTS ===

    string constant private tokenName = "FishToken";
    string constant private tokenSymbol = "FT";

    // === STORAGE ===

    address public broker;

    FishToken[] private fishies;

    mapping (uint256 => address) private fishIndexToOwner;
    mapping (address => uint256) private ownershipTokenCount;

    // === MODIFIER ===

    modifier onlyBroker {
        require(msg.sender == broker);
        _;
    }

    // === CONSTRUCTOR ===

    constructor(address _broker) public {
        broker = _broker;
    }

    // === FUNCTIONS ===

    function createFish(address _owner, string _name) public onlyBroker returns (uint256) {
        FishToken memory fish = FishToken(_name, generateUniqueData(), _owner);

        uint256 newFishId = fishies.push(fish) - 1;

        transfer(0, _owner, newFishId);

        return newFishId;
    }

    function generateUniqueData() private returns (uint32) {
        // random is not simple to implement
        // TODO: what if more than one fish are generated in same block?
        // Do they have the same data?
        return uint32(blockhash(block.number - 1)); // converts last 4 bytes of hash to uint32
    }

    function handoffFish(address _from, address _to, uint256 _tokenId) public onlyBroker {
        require(fishies[_tokenId].currentTank == _from, "Fish is not in your tank.");

        fishies[_tokenId].currentTank = _to;
    }

    function handoffFish(address _to, uint256 _tokenId) public onlyBroker {
        fishies[_tokenId].currentTank = _to;
    }

    function transfer(address _from, address _to, uint256 _tokenId) private {
        ownershipTokenCount[_from]--;
        ownershipTokenCount[_to]++;
        fishIndexToOwner[_tokenId] = _to;

        emit Transfer(_from, _to, _tokenId);
    }

    function getFishToken(uint256 _tokenId) public view returns (string, uint32, address) {
        FishToken storage fish = fishies[_tokenId];
        return (fish.name, fish.uniqueData, fish.currentTank);
    }

    function tokensOfOwner(address _owner) public view returns (uint256[] ownerTokens) {
        uint256 tokenCount = ownershipTokenCount[_owner];

        if (tokenCount == 0) {
            return new uint256[](0);
        } else {
            uint256[] memory result = new uint256[](tokenCount);
            uint256 totalFishToken = fishies.length;
            uint256 resultIndex = 0;

            uint256 tokenId;

            for (tokenId = 0; tokenId < totalFishToken; tokenId++) {
                if (fishIndexToOwner[tokenId] == _owner) {
                    result[resultIndex] = tokenId;
                    resultIndex++;
                }
            }

            return result;
        }
    }

    // === ERC721 FUNCTIONS ===

    function balanceOf(address _owner) external view returns (uint256) {
        return ownershipTokenCount[_owner];
    }

    function ownerOf(uint256 _tokenId) external view returns (address) {
        return fishIndexToOwner[_tokenId];
    }

    function safeTransferFrom(address _from, address _to, uint256 _tokenId, bytes data) external payable {revert("Not implemented.");}
    function safeTransferFrom(address _from, address _to, uint256 _tokenId) external payable {revert("Not implemented.");}

    function transferFrom(address _from, address _to, uint256 _tokenId) external payable {
        require(msg.sender == _from); // because approved or authorized transfers are not supported
        require(msg.sender == fishIndexToOwner[_tokenId]); // sender is owner and also tokenId exists (zero-address if not)
        require(_to != address(0));
        require(_to != address(this)); // don't transfer to this contract

        transfer(_from, _to, _tokenId);
    }

    function approve(address _approved, uint256 _tokenId) external payable {revert("Not implemented.");}
    function setApprovalForAll(address _operator, bool _approved) external {revert("Not implemented.");}
    function getApproved(uint256 _tokenId) external view returns (address) {revert("Not implemented.");}
    function isApprovedForAll(address _owner, address _operator) external view returns (bool) {revert("Not implemented.");}

    // === ERC721Metadata FUNCTIONS ===

    function name() external view returns (string _name) {
        return tokenName;
    }

    function symbol() external view returns (string _symbol) {
        return tokenSymbol;
    }

    function tokenURI(uint256 _tokenId) external view returns (string) {revert("Not implemented.");}

}