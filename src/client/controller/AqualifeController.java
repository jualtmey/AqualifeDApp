package client.controller;

import client.Util;
import client.model.Direction;
import client.model.FishInfo;
import client.model.FishModel;
import client.model.TankModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AqualifeController {

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

    public void run() {
        tankModel.run();
    }

    public void offerFish(FishInfo fishInfo, double priceInEther) {
        clientForwarder.offer(fishInfo.getTokenId(), Util.convertEtherToWei(BigDecimal.valueOf(priceInEther)));
    }

    public void buyNewFish(String name) {
        clientForwarder.buyNewFish(name);
    }

    public void buyFish(FishInfo fishInfo) {
        clientForwarder.buyFish(fishInfo.getTokenId(), fishInfo.getPrice());
    }

    public void summonFish(BigInteger tokenId) {
        clientForwarder.summon(tokenId);
    }

    public void processRegisterEvent(BigInteger tankId) {
        tankModel.onRegistration("Tank" + tankId);
    }

    public void receiveFish(BigInteger tokenId, int y, Direction direction) {
        FishInfo fishInfo = clientForwarder.getFishInfo(tokenId);
        tankModel.receiveFish(new FishModel(fishInfo, 0, y, direction));
    }

    public void receiveFishRandom(BigInteger tokenId) {
        Random random = new Random();
        int y = random.nextInt(TankModel.HEIGHT + 1);
        Direction direction =  random.nextInt(2) == 0 ? Direction.RIGHT : Direction.LEFT;

        receiveFish(tokenId, y, direction);
    }

    public List<FishInfo> getFishInfoOfOwnedFishToken() {
        List<FishInfo> fishInfo = new LinkedList<>();

        clientForwarder.getOwnedTokens().forEach(tokenId -> fishInfo.add(clientForwarder.getFishInfo(tokenId)));

        return fishInfo;
    }

    public List<FishInfo> getForSaleFishInfo() {
        List<FishInfo> fishInfo = new LinkedList<>();

        tankModel.forEach(fish -> {
            if (fish.getFishInfo().isForSale())
                fishInfo.add(fish.getFishInfo());
        });
        fishInfo.sort(Comparator.comparing(FishInfo::getTokenId));

        return fishInfo;
    }

    public TankModel getTankModel() {
        return tankModel;
    }

}
