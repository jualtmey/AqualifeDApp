package client;

import com.sun.deploy.util.SessionState;
import org.w3c.dom.ls.LSOutput;
import org.web3j.abi.EventEncoder;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Async;

import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class ClientCommunicator {

    private static final String BROKER_ADDRESS = "0xffbda8237fd0b6c875910015330433fe6ddd8107"; // geth
//    private static final String BROKER_ADDRESS = "0xb3ac42c3fff85d60653a02de561b9d4220968423"; // ganache

    public static final int POLLING_INTERVAL = 100; // millis

    private Web3j web3;
    private Credentials credentials;
    private Broker broker;

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

        initBroker();
    }

    public void initBroker() {
//        try {
//            credentials = WalletUtils.loadCredentials("test", "./test/ethereum/keystore/UTC--2018-05-23T13-34-07.274467000Z--6b39fa36eac4acc31f4f89c0b7c71eb3f75beddd");
////            credentials = WalletUtils.loadCredentials("test", "./test/ethereum/keystore/UTC--2018-05-23T13-36-51.878844000Z--2eee044a1c214e04347fe9ebe4bf591a5c2e6624");
////            credentials = WalletUtils.loadCredentials("test", "./test/ethereum/keystore/UTC--2018-05-23T13-36-56.110655700Z--969d5551790cad57e9dae845e5b4a00255a2a04c");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (CipherException e) {
//            e.printStackTrace();
//        }

//        broker = Broker.load(BROKER_ADDRESS, web3, credentials, GAS_PRICE, BigInteger.valueOf(2100000L));
//        TransactionManager fastRawTransactionManager = new FastRawTransactionManager(web3, credentials, (byte) 55);
        TransactionManager transactionManager = new ClientTransactionManager(web3, address, 600, POLLING_INTERVAL);
        broker = Broker.load(BROKER_ADDRESS, web3, transactionManager, GAS_PRICE, BigInteger.valueOf(2100000L));

        try {
            if (!broker.isValid()) {
                LOGGER.info("Broker invalid - Exit");
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("READY");

//        try {
//            broker.handoffFish("0", BigInteger.ONE, BigInteger.ONE, BigInteger.ONE).sendAsync();
////            Thread.sleep(100);
//            System.out.println(
//                    web3.ethGetTransactionCount("0x6b39fa36eac4acc31f4f89c0b7c71eb3f75beddd", DefaultBlockParameterName.PENDING).send().getTransactionCount());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.exit(1);
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
            // TODO: Implement
        }

        public void register() {
            LOGGER.info("Register...");
            try {
                TransactionReceipt transactionReceipt = broker.register().send();
//                Broker.RegisterEventResponse event = broker.getRegisterEvents(transactionReceipt).get(0);
//                tankModel.onRegistration("Tank" + event.id);
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
            LOGGER.info("Handoff " + fish.getId());
            try {
                broker.handoffFish(
                        fish.getTokenId(), BigInteger.valueOf(fish.getY()),
                        BigInteger.valueOf(fish.getDirection().getVector())).sendAsync()
                        .thenAccept(transactionReceipt -> {
                            System.out.println("Fish sent: " + fish.getId());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
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



            broker.registerEventObservable(registerFilter).subscribe(event -> {
                LOGGER.info("--- New Register Event ---");
                LOGGER.info(String.format("Recipient: %s%nID: %s%n",
                        event.recipient, event.tankId));

                tankModel.onRegistration("Tank" + event.tankId);

                // only needed without an address-topic filter
//                if (event.recipient.equals(ADDRESS)) {
//                    tankModel.onRegistration("Tank" + event.id);
//                }
            });

            // TODO: handoff (auch) nur empfangen, wenn TankID übereinstimmt? Nach TankID filtern, Adresse weglassen?
            broker.handoffFishEventObservable(handoffFishFilter).subscribe(event -> {
                LOGGER.info("--- New Handoff Event ---");
                LOGGER.info(String.format("Recipient: %s%nTokenID: %s%nFishName: %s%nOwnerTankID: %s%nY: %s%nDirection: %s%n",
                        event.recipient, event.tokenId, event.fishName, event.ownerTankId, event.direction));

                Direction direction = Direction.toDirection(event.direction.intValue());
                FishModel fish = new FishModel(event.tokenId, event.fishName, event.ownerTankId.intValue(), event.y.intValue(), direction);
                tankModel.receiveFish(fish);

                // only needed without an address-topic filter
//                if (event.recipient.equals(ADDRESS)) {
//                    Direction direction = Direction.toDirection(event.direction.intValue());
//                    FishModel fish = new FishModel(event.id, event.x.intValue(), event.y.intValue(), direction);
//                    tankModel.receiveFish(fish);
//                }
            });

//            web3.ethLogObservable(filter).subscribe(log -> {
//                System.out.println(log);
//                System.out.println(log.getData());



//                LOGGER.info("--- New Register Event ---");
//                LOGGER.info(String.format("Recipient: %s%nID: %s%n",
//                        event.recipient, event.id));
//
//                if (event.recipient.equals(ADDRESS)) {
//                    tankModel.onRegistration("Tank" + event.id);
//                }
//            });

//            web3.ethLogObservable(filter).subscribe(log -> {
//                LOGGER.info("--- New Handoff Event ---");
//                LOGGER.info(String.format("Recipient: %s%nFishID: %s%nX: %s%nY: %s%nDirection: %s%n",
//                        event.recipient, event.id, event.x, event.y, event.direction));
//
//                if (event.recipient.equals(ADDRESS)) {
//                    Direction direction = Direction.toDirection(event.direction.intValue());
//                    FishModel fish = new FishModel(event.id, event.x.intValue(), event.y.intValue(), direction);
//                    tankModel.receiveFish(fish);
//                }
//            });

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
