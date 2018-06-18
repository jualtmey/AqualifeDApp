package client;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class DeploySmartContract {

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
        TransactionManager transactionManager = new ClientTransactionManager(web3, address);

        Broker contract;
        try {
            contract = Broker.deploy(web3, transactionManager, GAS_PRICE, BigInteger.valueOf(2100000L)).send();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Address of contract: " + contract.getContractAddress());

        try {
            System.out.println(contract.register().send().getLogs());

            System.out.println(contract.fishBase().send());


            contract.createFish("hallo", BigInteger.valueOf(1000000000000000000L)).send();
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
