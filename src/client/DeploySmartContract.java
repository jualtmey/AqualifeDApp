package client;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import java.io.IOException;
import java.math.BigInteger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class DeploySmartContract {

    public static void main(String[] args) {
//        deployOnGeth();
        deployOnGanache();
    }

    public static void deployOnGeth() {
        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Credentials credentials;
        try {
            credentials = WalletUtils.loadCredentials("test", "./test/ethereum/keystore/UTC--2018-05-23T13-34-07.274467000Z--6b39fa36eac4acc31f4f89c0b7c71eb3f75beddd");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (CipherException e) {
            e.printStackTrace();
            return;
        }

        Broker contract;
        try {
            contract = Broker.deploy(web3, credentials, GAS_PRICE, BigInteger.valueOf(2100000L)).send();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Address of contract: " + contract.getContractAddress());
    }

    public static void deployOnGanache() {
        Web3j web3 = Web3j.build(new HttpService("http://localhost:7545/"));  // defaults to http://localhost:8545/

        TransactionManager transactionManager = new ClientTransactionManager(web3, "0xd4C2f4999E1eBa3DB8C678196bBfe62659835B5f");

        Broker contract;
        try {
            contract = Broker.deploy(web3, transactionManager, GAS_PRICE, BigInteger.valueOf(2100000L)).send();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Address of contract: " + contract.getContractAddress());
    }

}
