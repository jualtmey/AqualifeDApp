package client.controller;

import client.contracts.Broker;
import client.contracts.FishBase;
import client.contracts.Marketplace;
import client.model.Direction;
import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;

import java.util.logging.Logger;

public class ClientReceiver {

    private String address;

    private Web3j web3;
    private Broker broker;
    private FishBase fishBase;
    private Marketplace marketplace;

    private AqualifeController aqualifeController;

    private static final Logger LOGGER = Logger.getLogger(ClientReceiver.class.getName());

    public ClientReceiver(ClientCommunicator clientCommunicator, AqualifeController aqualifeController) {
        address = clientCommunicator.getAccountAddress();

        web3 = clientCommunicator.getWeb3();
        broker = clientCommunicator.getBroker();
        fishBase = clientCommunicator.getFishBase();
        marketplace = clientCommunicator.getMarketplace();

        this.aqualifeController = aqualifeController;
    }

    public void subscribeEvents() {
        EthFilter registerFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST, broker.getContractAddress())
                .addSingleTopic(EventEncoder.encode(Broker.REGISTER_EVENT))
                .addSingleTopic(ClientCommunicator.addressToTopic(address));

        EthFilter handoffFishFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST, broker.getContractAddress())
                .addSingleTopic(EventEncoder.encode(Broker.HANDOFFFISH_EVENT))
                .addSingleTopic(ClientCommunicator.addressToTopic(address));

        EthFilter summonFishFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST, broker.getContractAddress())
                .addSingleTopic(EventEncoder.encode(Broker.SUMMONFISH_EVENT))
                .addSingleTopic(ClientCommunicator.addressToTopic(address));

        EthFilter transferFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST, fishBase.getContractAddress())
                .addSingleTopic(EventEncoder.encode(FishBase.TRANSFER_EVENT));

        EthFilter renameTokenFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST, fishBase.getContractAddress())
                .addSingleTopic(EventEncoder.encode(FishBase.RENAMETOKEN_EVENT));

        EthFilter offerFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST, marketplace.getContractAddress())
                .addSingleTopic(EventEncoder.encode(Marketplace.OFFER_EVENT));

        EthFilter saleFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST, marketplace.getContractAddress())
                .addSingleTopic(EventEncoder.encode(Marketplace.SALE_EVENT));

        EthFilter cancellationFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST, marketplace.getContractAddress())
                .addSingleTopic(EventEncoder.encode(Marketplace.CANCELLATION_EVENT));


        broker.registerEventObservable(registerFilter).subscribe(event -> {
            LOGGER.info("--- New Register Event ---");
            LOGGER.info(String.format("Recipient: %s%nID: %s%n",
                    event.recipient, event.tankId));

            aqualifeController.onRegistrationEvent(event.tankId);
        });

        broker.handoffFishEventObservable(handoffFishFilter).subscribe(event -> {
            LOGGER.info("--- New Handoff Event ---");
            LOGGER.info(String.format("Recipient: %s%nTokenID: %s%nY: %s%nDirection: %s%n",
                    event.recipient, event.tokenId, event.y.intValue(), event.direction));

            int y = event.y.intValue();
            Direction direction = Direction.toDirection(event.direction.intValue());

            aqualifeController.receiveFish(event.tokenId, y, direction);
        });

        broker.summonFishEventObservable(summonFishFilter).subscribe(event -> {
            LOGGER.info("--- New Summon Event ---");
            LOGGER.info(String.format("Recipient: %s%nTokenID: %s%n",
                    event.recipient, event.tokenId));

            aqualifeController.receiveFishRandom(event.tokenId);
        });


        fishBase.transferEventObservable(transferFilter).subscribe(event -> {
            LOGGER.info("--- New Transfer Event ---");
            LOGGER.info(String.format("From: %s%nTo: %s%nTokenID: %s%n",
                    event.from, event.to, event.tokenId));

            aqualifeController.onFishBaseEvent();
        });

        fishBase.renameTokenEventObservable(renameTokenFilter).subscribe(event -> {
            LOGGER.info("--- New Rename Token Event ---");
            LOGGER.info(String.format("Owner: %s%nTokenID: %s%nNewName: %s%n",
                    event.owner, event.tokenId, event.newName));

            aqualifeController.onFishBaseEvent();
        });


        marketplace.offerEventObservable(offerFilter).subscribe(event -> {
            LOGGER.info("--- New Offer Event ---");
            LOGGER.info(String.format("Seller: %s%nTokenID: %s%nPrice: %s%n",
                    event.seller, event.tokenId, event.price));

            aqualifeController.onMarketplaceEvent();
        });

        marketplace.saleEventObservable(saleFilter).subscribe(event -> {
            LOGGER.info("--- New Sale Event ---");
            LOGGER.info(String.format("Seller: %s%nBuyer: %s%nTokenID: %s%nPrice: %s%n",
                    event.seller, event.buyer, event.tokenId, event.price));

            aqualifeController.onMarketplaceEvent();
        });

        marketplace.cancellationEventObservable(cancellationFilter).subscribe(event -> {
            LOGGER.info("--- New Cancellation Event ---");
            LOGGER.info(String.format("TokenID: %s%n",
                    event.tokenId));

            aqualifeController.onMarketplaceEvent();
        });


        web3.blockObservable(false).subscribe(block -> aqualifeController.onNewBlockEvent());


        LOGGER.info("Subscribed to events");
    }

    public void unsubscribeEvents() {

    }

}
