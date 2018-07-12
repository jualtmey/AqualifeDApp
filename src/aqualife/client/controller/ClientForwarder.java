package aqualife.client.controller;

import aqualife.client.contracts.Broker;
import aqualife.client.contracts.FishBase;
import aqualife.client.contracts.Marketplace;
import aqualife.client.model.FishInfo;
import aqualife.client.model.FishModel;
import org.web3j.tuples.generated.Tuple2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;

public class ClientForwarder {

    private static final Logger LOGGER = Logger.getLogger(ClientForwarder.class.getName());

    private ClientCommunicator clientCommunicator;
    private Broker broker;
    private FishBase fishBase;
    private Marketplace marketplace;

    public ClientForwarder(ClientCommunicator clientCommunicator) {
        this.clientCommunicator = clientCommunicator;
        broker = clientCommunicator.getBroker();
        fishBase = clientCommunicator.getFishBase();
        marketplace = clientCommunicator.getMarketplace();
    }

    public void register() {
        LOGGER.info("Register...");
        try {
            broker.register().send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deregister() {
        LOGGER.info("Deregister...");
        try {
            broker.deregister().send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handOff(FishModel fish) {
        LOGGER.info("Handoff " + fish.getTokenId());

        broker.handoffFish(
                fish.getTokenId(), BigInteger.valueOf(fish.getY()),
                BigInteger.valueOf(fish.getDirection().getVector())).sendAsync()
                .thenAccept(transactionReceipt -> {
                    System.out.println("Fish sent: " + fish.getTokenId());
                });
    }

    public void summon(BigInteger tokenId) {
        LOGGER.info("Summon fish" + tokenId);

        broker.summonFish(tokenId).sendAsync();
    }

    public void approveMarketplaceForAll() {
        try {
            fishBase.setApprovalForAll(marketplace.getContractAddress(), true).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offer(BigInteger tokenId, BigInteger price) {
        marketplace.offerFish(tokenId, price).sendAsync();
    }

    public void buyNewFish(String name) {
        marketplace.buyNewFish(name, getNewFishPrice()).sendAsync();
    }

    public void buyFish(BigInteger tokenId, BigInteger price) {
        marketplace.buyFish(tokenId, price).sendAsync();
    }

    public void cancelSell(BigInteger tokenId) {
        marketplace.cancelFishOffer(tokenId).sendAsync();
    }

    public void renameFish(BigInteger tokenId, String name) {
        fishBase.changeFishName(tokenId, name).sendAsync();
    }

    public FishInfo getFishInfo(BigInteger tokenId) {
        Tuple2<String, BigInteger> tokenTuple = null;
        String ownerAddress;
        BigInteger ownerTankId = null;
        BigInteger price = null;

        try {

            tokenTuple = fishBase.getFishToken(tokenId).send();
            ownerAddress = fishBase.ownerOf(tokenId).send();
            ownerTankId = broker.tankIdOf(ownerAddress).send();
            price = getTokenPrice(tokenId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new FishInfo(tokenId, tokenTuple.getValue1(), tokenTuple.getValue2().intValue(), ownerTankId, price);
    }

    public BigInteger tokenIdToCurrentTank(BigInteger tokenId) {
        try {
            return broker.tokenIdToCurrentTank(tokenId).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BigInteger> getOwnedTokens() {
        try {
            return fishBase.tokensOfOwner(clientCommunicator.getAccountAddress()).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BigInteger> getAllTokensForSale() {
        try {
            return marketplace.listAllSales().send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigInteger getTokenPrice(BigInteger tokenId) {
        try {
            if (!marketplace.isForSale(tokenId).send().booleanValue()) {
                return null;
            }

            return marketplace.saleOf(tokenId).send().getValue2();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isMarketplaceApprovedForAll() {
        try {
            return fishBase.isApprovedForAll(clientCommunicator.getAccountAddress(), marketplace.getContractAddress()).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public BigInteger getNewFishPrice() {
        try {
            return marketplace.newFishPrice().send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BigInteger> getAllTokensInTank() {
        try {
            return broker.getAllTokensInTank(broker.tankIdOf(clientCommunicator.getAccountAddress()).send()).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isRegistered() {
        try {
            BigInteger tankId = getTankId();
            return broker.clients(tankId).send().getValue3();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public BigInteger getTankId() {
        try {
            return broker.tankIdOf(clientCommunicator.getAccountAddress()).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
