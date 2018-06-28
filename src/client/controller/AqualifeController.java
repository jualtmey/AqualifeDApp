package client.controller;

import client.Util;
import client.model.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class AqualifeController extends Observable {

    private TankModel tankModel;

    private ClientCommunicator clientCommunicator;
    private ClientForwarder clientForwarder;
    private ClientReceiver clientReceiver;

    public AqualifeController(ClientCommunicator clientCommunicator) {
        this.clientCommunicator = clientCommunicator;

        clientForwarder = new ClientForwarder(clientCommunicator);
        clientReceiver = new ClientReceiver(clientCommunicator, this);
        clientReceiver.subscribeEvents();

        tankModel = new TankModel(clientForwarder);
    }

    public void start() {
        clientForwarder.getAllTokensInTank().forEach(tokenId -> receiveFishRandom(tokenId));

        clientForwarder.register();
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

    public void onRegistration(BigInteger tankId) {
        tankModel.onRegistration("Tank" + tankId);
        setChanged();
        notifyObservers(Event.REGISTRATION);
    }

    public void onTransfer() {
        setChanged();
        notifyObservers(Event.TRANSFER);
    }

    public void onMarketplace() {
        setChanged();
        notifyObservers(Event.MARKETPLACE);
    }

    public void onNewBlock() {
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
//        fishInfo.sort(Comparator.comparing(FishInfo::getTokenId));

        return fishInfo;
    }

    public BigInteger getNewFishPrice() {
        return clientForwarder.getNewFishPrice();
    }

    public TankModel getTankModel() {
        return tankModel;
    }

}
