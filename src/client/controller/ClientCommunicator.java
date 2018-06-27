package client.controller;

import client.contracts.Broker;
import client.contracts.FishBase;
import client.contracts.Marketplace;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Async;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class ClientCommunicator {

    private static final String BROKER_ADDRESS = "0xf86eefa1b51d6e808a985eb17a8f914706d64562";
    private static final String FISH_BASE_ADDRESS = "0x362919f4588299686f31be5855cdd2b7f4fb1427";
    private static final String MARKETPLACE_ADDRESS = "0x73ccb78b04499111e6e6fc67641e1c93f747c2ad";

    private static final int POLLING_INTERVAL = 1000; // millis
    private static final int POLLING_TIMEOUT = 60000; // millis
    private static final int POLLING_ATTEMPTS = POLLING_TIMEOUT / POLLING_INTERVAL;

    private Web3j web3;
    private Credentials credentials;
    private String accountAddress;

    private Broker broker;
    private FishBase fishBase;
    private Marketplace marketplace;

//    private ClientForwarder forwarder;
//    private ClientReceiver receiver;

    private static final Logger LOGGER = Logger.getLogger(ClientCommunicator.class.getName());

    public ClientCommunicator() {
        LOGGER.setLevel(Level.INFO);

//        web3 = Web3j.build(new HttpService("http://localhost:8545/"));  // defaults to http://localhost:8545/ (Ethereum-Node supporting JSON-RPC) PollingInterval: 15 * 1000
        web3 = Web3j.build(new HttpService("http://localhost:8545/"), POLLING_INTERVAL, Async.defaultExecutorService());  // defaults to http://localhost:8545/ (Ethereum-Node supporting JSON-RPC)

        // test connection to node
        String clientVersion = getClientVersion();
        LOGGER.info("Client version:\n" + clientVersion);
    }

    public void loadContracts() {
        loadBroker();
        loadFishBase();
        loadMarketplace();
    }

    private void loadBroker() {
        TransactionManager transactionManager = new ClientTransactionManager(web3, accountAddress, POLLING_ATTEMPTS, POLLING_INTERVAL);
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

    private void loadFishBase() {
        TransactionManager transactionManager = new ClientTransactionManager(web3, accountAddress, POLLING_ATTEMPTS, POLLING_INTERVAL);
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

    private void loadMarketplace() {
        TransactionManager transactionManager = new ClientTransactionManager(web3, accountAddress, POLLING_ATTEMPTS, POLLING_INTERVAL);
        marketplace = Marketplace.load(MARKETPLACE_ADDRESS, web3, transactionManager, GAS_PRICE, BigInteger.valueOf(2100000L));

        try {
            if (!marketplace.isValid()) {
                LOGGER.info("Marketplace invalid - Exit");
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAccountAddress(String address) {
        accountAddress = address;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public String[] getAccounts() {
        try {
            return web3.ethAccounts().send().getAccounts().toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getClientVersion() {
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return web3ClientVersion.getWeb3ClientVersion();
    }

    public BigDecimal getBalanceInEther() {
        return Convert.fromWei(getBalance().toString(), Convert.Unit.ETHER);
    }

    public BigInteger getBalance() {
        return getBalance(accountAddress);
    }

    public BigInteger getBalance(String address) {
        try {
            return web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addressToTopic(String address) {
        // address expected with leading '0x'
        // length of topic needs to be 64 characters (32 byte / 256 bit)
        return "0x000000000000000000000000" + address.substring(2);
    }

//    public ClientForwarder getClientForwarder() {
//        if (broker == null) {
//            throw new RuntimeException("Broker not initialized");
//        }
//        if (forwarder == null) {
//            forwarder = new ClientForwarder();
//        }
//        return forwarder;
//    }
//
//    public ClientReceiver getClientReceiver(TankModel tankModel) {
//        if (broker == null) {
//            throw new RuntimeException("Broker not initialized");
//        }
//        if (receiver == null) {
//            receiver = new ClientReceiver(tankModel);
//        }
//        return receiver;
//    }

    public Web3j getWeb3() {
        return web3;
    }

    public Broker getBroker() {
        return broker;
    }

    public FishBase getFishBase() {
        return fishBase;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

}
