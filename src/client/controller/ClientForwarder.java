package client.controller;

import client.contracts.Broker;
import client.contracts.FishBase;
import client.contracts.Marketplace;
import client.model.FishInfo;
import client.model.FishModel;
import org.web3j.tuples.generated.Tuple2;

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

    public void deregister(String id) {
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

    public void offer(BigInteger tokenId, BigInteger price) {
        marketplace.offerFish(tokenId, price).sendAsync();
    }

    public void buyNewFish(String name) {
        marketplace.buyNewFish(name, getNewTokenPrice()).sendAsync();
    }

    public void buyFish(BigInteger tokenId, BigInteger price) {
        marketplace.buyFish(tokenId, price).sendAsync();
    }

    public FishInfo getFishInfo(BigInteger tokenId) {
        Tuple2<String, BigInteger> tokenTuple = null;
        String ownerAddress;
        BigInteger ownerTankId = null;
        BigInteger price = null;

        try {

            tokenTuple = fishBase.getFishToken(tokenId).send();
            ownerAddress = fishBase.ownerOf(tokenId).send();
            ownerTankId = broker.clients(ownerAddress).send().getValue1();
            price = getTokenPrice(tokenId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new FishInfo(tokenId, tokenTuple.getValue1(), tokenTuple.getValue2().intValue(), ownerTankId, price);
    }

    public List<BigInteger> getOwnedTokens() {
        try {
            return fishBase.tokensOfOwner(clientCommunicator.getAccountAddress()).send();
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

    public BigInteger getNewTokenPrice() {
        try {
            return marketplace.newFishPrice().send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
