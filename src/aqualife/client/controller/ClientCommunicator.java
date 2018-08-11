package aqualife.client.controller;

import aqualife.client.contracts.Broker;
import aqualife.client.contracts.FishBase;
import aqualife.client.contracts.Marketplace;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.Callback;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.QueuingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Async;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class ClientCommunicator {

    private static final int POLLING_INTERVAL = 1000; // millis
    private static final int POLLING_TIMEOUT = 5 * 60000; // millis
    private static final int POLLING_ATTEMPTS = POLLING_TIMEOUT / POLLING_INTERVAL;

    private Web3j web3;
    private TransactionManager transactionManager;
    private Credentials credentials;
    private String accountAddress;

    private Broker broker;
    private FishBase fishBase;
    private Marketplace marketplace;

    private static final Logger LOGGER = Logger.getLogger(ClientCommunicator.class.getName());

    public ClientCommunicator(String password, String pathToWalletFile) {
        LOGGER.setLevel(Level.INFO);

//        web3 = Web3j.build(new HttpService("http://localhost:8545/"));  // defaults to http://localhost:8545/ (Ethereum-Node supporting JSON-RPC) Default PollingInterval: 15 * 1000 millis
        web3 = Web3j.build(new HttpService("http://localhost:8545/"), POLLING_INTERVAL, Async.defaultExecutorService());  // defaults to http://localhost:8545/ (Ethereum-Node supporting JSON-RPC)

        // test connection to node
        String clientVersion = getClientVersion();
        LOGGER.info("Client version:\n" + clientVersion);

        try {
            credentials = WalletUtils.loadCredentials(password, pathToWalletFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        transactionManager = new FastRawTransactionManager(
                web3, credentials, new PollingTransactionReceiptProcessor(web3, POLLING_INTERVAL, POLLING_ATTEMPTS));

        accountAddress = credentials.getAddress();
    }

    public CompletableFuture<Broker> deployBroker() {
        return Broker.deploy(web3, transactionManager, GAS_PRICE, BigInteger.valueOf(3000000L)).sendAsync();
    }

    public CompletableFuture<FishBase> deployFishBase() {
        return FishBase.deploy(web3, transactionManager, GAS_PRICE, BigInteger.valueOf(3000000L)).sendAsync();
    }

    public CompletableFuture<Marketplace> deployMarketplace() {
        return Marketplace.deploy(web3, transactionManager, GAS_PRICE, BigInteger.valueOf(3000000L)).sendAsync();
    }

    public void loadBroker(String address) {
        broker = Broker.load(address, web3, transactionManager, GAS_PRICE, BigInteger.valueOf(2100000L));
    }

    public void loadFishBase(String address) {
        fishBase = FishBase.load(address, web3, transactionManager, GAS_PRICE, BigInteger.valueOf(2100000L));
    }

    public void loadMarketplace(String address) {
        marketplace = Marketplace.load(address, web3, transactionManager, GAS_PRICE, BigInteger.valueOf(2100000L));
    }

    public void shutdown() {
        web3.shutdown();
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
