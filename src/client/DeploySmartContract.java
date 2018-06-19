package client;

import client.contracts.Broker;
import client.contracts.FishBase;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

import java.io.IOException;
import java.math.BigInteger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class DeploySmartContract {

    public static final int POLLING_INTERVAL = 2000; // millis

    public static void main(String[] args) {
        deployOnGeth();
//        deployOnGanache();
    }

    public static void deployOnGeth() {
        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/

        String address = null;
        try {
            address = web3.ethAccounts().send().getAccounts().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TransactionManager transactionManager = new ClientTransactionManager(web3, address, 600, POLLING_INTERVAL);

        Broker broker;
        FishBase fishBase;

        try {

            broker = Broker.deploy(web3, transactionManager, GAS_PRICE, BigInteger.valueOf(3000000L)).send();
            fishBase = FishBase.deploy(web3, transactionManager, GAS_PRICE, BigInteger.valueOf(3000000L)).send();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Address of Broker: " + broker.getContractAddress());
        System.out.println("Address of FishBase: " + fishBase.getContractAddress());

        try {
            broker.setFishBase(fishBase.getContractAddress()).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deployOnGanache() {
        Web3j web3 = Web3j.build(new HttpService("http://localhost:7545/"));

        String address = null;
        try {
            address = web3.ethAccounts().send().getAccounts().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TransactionManager transactionManager = new ClientTransactionManager(web3, address);

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
