package client;

import client.contracts.Broker;
import client.contracts.FishBase;
import org.web3j.abi.EventEncoder;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Async;

import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class ClientCommunicator {

    private static final String BROKER_ADDRESS = "0xd477537d8088e7b8a1a06d9d51e8f066f15bb028";
    private static final String FISH_BASE_ADDRESS = "0x9469ea20c4f6fef59f558deb03805c667775e7d6";

    // in millis
    private static final int POLLING_INTERVAL = 1000;
    private static final int POLLING_TIMEOUT = 60000;
    private static final int POLLING_ATTEMPTS = POLLING_TIMEOUT / POLLING_INTERVAL;

    private Web3j web3;
    private Credentials credentials;

    private Broker broker;
    private FishBase fishBase;

    private String address;

    private static final Logger LOGGER = Logger.getLogger(ClientCommunicator.class.getName());

    private TankModel tankModel;
    private ClientForwarder forwarder;
    private ClientReceiver receiver;

    public ClientCommunicator() {
        LOGGER.setLevel(Level.INFO);

//        web3 = Web3j.build(new HttpService("http://localhost:8545/"));  // defaults to http://localhost:8545/ (Ethereum-Node supporting JSON-RPC) PollingInterval: 15 * 1000
        web3 = Web3j.build(new HttpService("http://localhost:8545/"), POLLING_INTERVAL, Async.defaultExecutorService());  // defaults to http://localhost:8545/ (Ethereum-Node supporting JSON-RPC)

        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        LOGGER.info("Client version:\n" + clientVersion);

        String[] accounts = accounts();
        if (accounts == null) {
            LOGGER.info("No accounts");
            return;
        }

        String selectedAccount = (String) JOptionPane.showInputDialog(
                null,
                "Select your account:\n",
                "Accounts",
                JOptionPane.PLAIN_MESSAGE,
                null,
                accounts,
                accounts[0]);

        if (selectedAccount == null) {
            // TODO: exit or error
            return;
        }

        setAddress(selectedAccount);

        loadBroker();
        loadFishBase();
    }

    public void loadBroker() {
        TransactionManager transactionManager = new ClientTransactionManager(web3, address, POLLING_ATTEMPTS, POLLING_INTERVAL);
        broker = Broker.load(BROKER_ADDRESS, web3, transactionManager, GAS_PRICE, BigInteger.valueOf(2100000L));

        try {
            if (!broker.isValid()) {
                LOGGER.info("Broker invalid - Exit");
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFishBase() {
        TransactionManager transactionManager = new ClientTransactionManager(web3, address, POLLING_ATTEMPTS, POLLING_INTERVAL);
        fishBase = FishBase.load(FISH_BASE_ADDRESS, web3, transactionManager, GAS_PRICE, BigInteger.valueOf(2100000L));

        try {
            if (!fishBase.isValid()) {
                LOGGER.info("FishBase invalid - Exit");
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String addressToTopic(String address) {
        // address expected with leading '0x'
        // length of topic needs to be 64 characters (32 byte / 256 bit)
        return "0x000000000000000000000000" + address.substring(2);
    }

    public void setAddress(String address) {
        if (broker == null) {
            this.address = address;
        } else {
            LOGGER.info("Address cannot be changed, if broker is initialized.");
        }
    }

    public String getAddress() {
        return address;
    }

    public String[] accounts() {
        try {
            return web3.ethAccounts().send().getAccounts().toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class ClientForwarder {

        private ClientForwarder() {

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

        public void summon(long tokenId) {
            LOGGER.info("Summon fish" + tokenId);

            broker.summonFish(BigInteger.valueOf(tokenId)).sendAsync();
        }

        public FishInfo getFishToken(BigInteger tokenId) {
            LOGGER.info("Fish info " + tokenId);

            Tuple2<String, BigInteger> tokenTuple = null;
            String ownerAddress;
            BigInteger ownerTankId = null;

            try {

                tokenTuple = fishBase.getFishToken(tokenId).send();
                ownerAddress = fishBase.ownerOf(tokenId).send();
                ownerTankId = broker.clients(ownerAddress).send().getValue1();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return new FishInfo(tokenId, tokenTuple.getValue1(), tokenTuple.getValue2().intValue(), ownerTankId);
        }

    }

    public class ClientReceiver {
        private final TankModel tankModel;

        private ClientReceiver(TankModel tankModel) {
            this.tankModel = tankModel;
        }

        public void start() {
            EthFilter registerFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                    DefaultBlockParameterName.LATEST, broker.getContractAddress())
                    .addSingleTopic(EventEncoder.encode(Broker.REGISTER_EVENT))
                    .addSingleTopic(addressToTopic(address));

            EthFilter handoffFishFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                    DefaultBlockParameterName.LATEST, broker.getContractAddress())
                    .addSingleTopic(EventEncoder.encode(Broker.HANDOFFFISH_EVENT))
                    .addSingleTopic(addressToTopic(address));

            EthFilter summonFishFilter = new EthFilter(DefaultBlockParameterName.LATEST,
                    DefaultBlockParameterName.LATEST, broker.getContractAddress())
                    .addSingleTopic(EventEncoder.encode(Broker.SUMMONFISH_EVENT))
                    .addSingleTopic(addressToTopic(address));



            broker.registerEventObservable(registerFilter).subscribe(event -> {
                LOGGER.info("--- New Register Event ---");
                LOGGER.info(String.format("Recipient: %s%nID: %s%n",
                        event.recipient, event.tankId));

                tankModel.onRegistration("Tank" + event.tankId);
            });

            broker.handoffFishEventObservable(handoffFishFilter).subscribe(event -> {
                LOGGER.info("--- New Handoff Event ---");
                LOGGER.info(String.format("Recipient: %s%nTokenID: %s%nY: %s%nDirection: %s%n",
                        event.recipient, event.tokenId, event.y.intValue(), event.direction));

                int y = event.y.intValue();
                Direction direction =  Direction.toDirection(event.direction.intValue());

                FishInfo fishInfo = forwarder.getFishToken(event.tokenId);

                FishModel fish = new FishModel(fishInfo, 0, y, direction);
                tankModel.receiveFish(fish);
            });

            broker.summonFishEventObservable(summonFishFilter).subscribe(event -> {
                LOGGER.info("--- New Summon Event ---");
                LOGGER.info(String.format("Recipient: %s%nTokenID: %s%n",
                        event.recipient, event.tokenId));

                // set random values for y and direction
                Random random = new Random();
                int y = random.nextInt(TankModel.HEIGHT + 1);
                Direction direction =  random.nextInt(2) == 0 ? Direction.RIGHT : Direction.LEFT;

                FishInfo fishInfo = forwarder.getFishToken(event.tokenId);

                FishModel fish = new FishModel(fishInfo, 0, y, direction);
                tankModel.receiveFish(fish);
            });

            LOGGER.info("Subscribed to events");
        }

    }

    public ClientForwarder newClientForwarder() {
        if (broker == null) {
            throw new RuntimeException("Broker not initialized");
        }
        if (forwarder == null) {
            forwarder = new ClientForwarder();
        }
        return forwarder;
    }

    public ClientReceiver newClientReceiver(TankModel tankModel) {
        if (broker == null) {
            throw new RuntimeException("Broker not initialized");
        }
        if (receiver == null) {
            receiver = new ClientReceiver(tankModel);
        }
        return receiver;
    }

}
