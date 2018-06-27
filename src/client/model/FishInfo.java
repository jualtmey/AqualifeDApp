package client.model;

import client.Util;

import java.math.BigInteger;

public class FishInfo {

    private BigInteger tokenId;
    private String name;
    private int uniqueData;
    private BigInteger ownerTankId;
    private BigInteger price;

    public FishInfo(BigInteger tokenId, String name, int uniqueData, BigInteger ownerTankId, BigInteger price) {
        this.tokenId = tokenId;
        this.name = name;
        this.uniqueData = uniqueData;
        this.ownerTankId = ownerTankId;
        this.price = price;
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

    public BigInteger getPrice() {
        return price;
    }

    public boolean isForSale() {
        return price != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TokenId: " + String.format("%1$-" + 4 + "s", tokenId) + " | ");
        sb.append("Name: " + String.format("%1$-" + 10 + "s", name) + " | ");
        sb.append("OwnerTankId: " + String.format("%1$-" + 4 + "s", ownerTankId));
        if (isForSale()) sb.append(" | Price: " + Util.convertWeiToEther(price));
        return sb.toString();
    }

}
