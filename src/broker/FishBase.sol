pragma solidity ^0.4.24;

import "./Ownable.sol";
import "./ERC721.sol";
import "./ERC721Metadata.sol";

// see KryptoKitties and OpenZeppelin

contract FishBase is ERC721, ERC721Metadata /*, ERC165 */, Ownable {

    // === STRUCTS ===

    struct FishToken {
        string name;
        uint32 uniqueData; // color information of fish
    }

    // === CONSTANTS ===

    string constant private tokenName = "FishToken";
    string constant private tokenSymbol = "FT";

    // === STORAGE ===

    address public authorizedTokenCreator;

    FishToken[] private fishies;

    mapping (uint256 => address) private tokenOwner;
    mapping (uint256 => address) private tokenApprovals;
    mapping (address => uint256) private ownershipTokenCount;

    FishBase public fishBase;

    // === MODIFIER ===

    modifier onlyAuthorized() {
        require(msg.sender == authorizedTokenCreator, "You are not authorized.");
        _;
    }

    // === CONSTRUCTOR ===

    constructor() public {
        authorizedTokenCreator = msg.sender;
    }

    // === FUNCTIONS ===

    function mintToken(address _owner, string _name) external onlyAuthorized returns (uint256) {
        FishToken memory fish = FishToken(_name, generateUniqueData());

        uint256 newFishId = fishies.push(fish) - 1;

        transfer(0, _owner, newFishId);

        return newFishId;
    }

    function generateUniqueData() private returns (uint32) {
        // random is not simple to implement
        // TODO: what if more than one fish are generated in same block?
        // Do they have the same data?
        // TODO: use fish name for random
        return uint32(blockhash(block.number - 1)); // converts last 4 bytes of hash to uint32
    }

    function changeFishName(uint256 _tokenId, string _name) external {
        require(msg.sender == tokenOwner[_tokenId]);

        fishies[_tokenId].name = _name;
    }

    function transfer(address _from, address _to, uint256 _tokenId) private {
        ownershipTokenCount[_from]--;
        ownershipTokenCount[_to]++;
        tokenOwner[_tokenId] = _to;

        emit Transfer(_from, _to, _tokenId);
    }

    function getFishToken(uint256 _tokenId) external view returns (string, uint32) {
        FishToken storage fish = fishies[_tokenId];
        return (fish.name, fish.uniqueData);
    }

    function setCreateFishAuthorized(address _newAuthorized) external onlyOwner {
        authorizedTokenCreator = _newAuthorized;
    }

    function tokensOfOwner(address _owner) external view returns (uint256[] ownerTokens) {
        uint256 tokenCount = ownershipTokenCount[_owner];

        if (tokenCount == 0) {
            return new uint256[](0);
        } else {
            uint256[] memory result = new uint256[](tokenCount);
            uint256 totalFishToken = fishies.length;
            uint256 resultIndex = 0;

            uint256 tokenId;

            for (tokenId = 0; tokenId < totalFishToken; tokenId++) {
                if (tokenOwner[tokenId] == _owner) {
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
        return tokenOwner[_tokenId];
    }

    function safeTransferFrom(address _from, address _to, uint256 _tokenId, bytes data) external payable {
        revert("Not implemented.");
    }

    function safeTransferFrom(address _from, address _to, uint256 _tokenId) external payable {
        revert("Not implemented.");
    }

    function transferFrom(address _from, address _to, uint256 _tokenId) external payable {
        require(tokenOwner[_tokenId] != address(0)); // tokenId exists, is valid
        require(msg.sender == _from || msg.sender == tokenApprovals[_tokenId]); // sender is owner or approved
        require(_from == tokenOwner[_tokenId]); // _from is token owner
        require(_to != address(0)); // save transfer, token can't be lost
        require(_to != address(this)); // don't transfer to this contract

        transfer(_from, _to, _tokenId);

        if (tokenApprovals[_tokenId] != address(0)) {
            tokenApprovals[_tokenId] = address(0);
            emit Approval(_from, address(0), _tokenId);
        }
    }

    function approve(address _approved, uint256 _tokenId) external payable {
        address owner = tokenOwner[_tokenId];
        require(_approved != owner);
        require(msg.sender == owner);

        tokenApprovals[_tokenId] = _approved;

        emit Approval(owner, _approved, _tokenId);
    }

    function setApprovalForAll(address _operator, bool _approved) external {
        revert("Not implemented.");
    }

    function getApproved(uint256 _tokenId) external view returns (address) {
        return tokenApprovals[_tokenId];
    }

    function isApprovedForAll(address _owner, address _operator) external view returns (bool) {
        revert("Not implemented.");

    }

    // === ERC721Metadata FUNCTIONS ===

    function name() external view returns (string _name) {
        return tokenName;
    }

    function symbol() external view returns (string _symbol) {
        return tokenSymbol;
    }

    function tokenURI(uint256 _tokenId) external view returns (string) {revert("Not implemented.");}

}