package client;

import java.math.BigInteger;

public class FishToken {

    private BigInteger tokenId;
    private String name;
    private int uniqueData;
    private BigInteger ownerTankId;

    public FishToken(BigInteger tokenId, String name, int uniqueData, BigInteger ownerTankId) {
        this.tokenId = tokenId;
        this.name = name;
        this.uniqueData = uniqueData;
        this.ownerTankId = ownerTankId;
    }

    public BigInteger getTokenId() {
        return tokenId;
    }

    public String getName() {
        return name;
    }

    public int getUniqueData() {
        return uniqueData;
    }

    public BigInteger getOwnerTankId() {
        return ownerTankId;
    }

}
