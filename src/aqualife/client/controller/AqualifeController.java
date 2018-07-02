package aqualife.client.controller;

import aqualife.client.Util;
import aqualife.client.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class AqualifeController extends Observable {

    private static final String PATH_CONTRACT_ADDRESSES = "./contract_address.txt";

    private TankModel tankModel;

    private ClientCommunicator clientCommunicator;
    private ClientForwarder clientForwarder;
    private ClientReceiver clientReceiver;

    public AqualifeController(ClientCommunicator clientCommunicator) {
        this.clientCommunicator = clientCommunicator;

        List<String> addresses = readContractAddressesFromFile(PATH_CONTRACT_ADDRESSES);
        clientCommunicator.loadBroker(addresses.get(0));
        clientCommunicator.loadFishBase(addresses.get(1));
        clientCommunicator.loadMarketplace(addresses.get(2));

        clientForwarder = new ClientForwarder(clientCommunicator);
        clientReceiver = new ClientReceiver(clientCommunicator, this);
        clientReceiver.subscribeEvents();

        tankModel = new TankModel(clientForwarder);
    }

    public void start() {
        clientForwarder.getAllTokensInTank().forEach(tokenId -> receiveFishRandom(tokenId));

        clientForwarder.register(); // TODO: handle if already registered (Gui will not update to TankID)
        if (!clientForwarder.isMarketplaceApprovedForAll()) {
            clientForwarder.approveMarketplaceForAll();
        }

        tankModel.run();
    }

    public void stop() {
        clientForwarder.deregister(tankModel.getId());
        System.exit(0);
    }

    public void offerFish(FishInfo fishInfo, double priceInEther) {
        clientForwarder.offer(fishInfo.getTokenId(), Util.convertEtherToWei(BigDecimal.valueOf(priceInEther)));
    }

    public void cancelSell(FishInfo fishInfo) {
        clientForwarder.cancelSell(fishInfo.getTokenId());
    }

    public void buyNewFish(String name) {
        clientForwarder.buyNewFish(name);
    }

    public void buyFish(FishInfo fishInfo) {
        clientForwarder.buyFish(fishInfo.getTokenId(), fishInfo.getPrice());
    }

    public void renameFish(FishInfo fishInfo, String name) {
        clientForwarder.renameFish(fishInfo.getTokenId(), name);
    }

    public void summonFish(BigInteger tokenId) {
        clientForwarder.summon(tokenId);
    }

    public void receiveFish(BigInteger tokenId, int y, Direction direction) {
        FishInfo fishInfo = clientForwarder.getFishInfo(tokenId);
        tankModel.receiveFish(new FishModel(fishInfo, 0, y, direction));
    }

    public void receiveFishRandom(BigInteger tokenId) {
        Random random = new Random();
        int y = random.nextInt(TankModel.HEIGHT + 1);
        Direction direction = random.nextInt(2) == 0 ? Direction.RIGHT : Direction.LEFT;

        receiveFish(tokenId, y, direction);
    }

    public void onRegistrationEvent(BigInteger tankId) {
        tankModel.onRegistration("Tank" + tankId);
        setChanged();
        notifyObservers(Event.REGISTRATION);
    }

    public void onFishBaseEvent() {
        setChanged();
        notifyObservers(Event.FISHBASE);
    }

    public void onMarketplaceEvent() {
        setChanged();
        notifyObservers(Event.MARKETPLACE);
    }

    public void onNewBlockEvent() {
        setChanged();
        notifyObservers(Event.NEW_BLOCK);
    }

    public List<FishInfo> getFishInfoOfOwnedFishToken() {
        List<FishInfo> fishInfo = new ArrayList<>();

        clientForwarder.getOwnedTokens().forEach(tokenId -> fishInfo.add(clientForwarder.getFishInfo(tokenId)));

        return fishInfo;
    }

    public List<FishInfo> getForSaleFishInfo() {
        List<FishInfo> fishInfo = new ArrayList<>();

        clientForwarder.getAllTokensForSale().forEach(tokenId -> fishInfo.add(clientForwarder.getFishInfo(tokenId)));

        return fishInfo;
    }

    public BigInteger getNewFishPrice() {
        return clientForwarder.getNewFishPrice();
    }

    public TankModel getTankModel() {
        return tankModel;
    }

    public List<String> readContractAddressesFromFile(String path) {
        List<String> contractAddresses = new ArrayList<>();
        try {
            Files.readAllLines(Paths.get(path)).forEach(line -> contractAddresses.add(line.split(":")[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contractAddresses;
    }

}
