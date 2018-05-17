package client;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class DeploySmartContract {

    public static void main(String[] args) {
        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Credentials credentials;
        try {
            credentials = WalletUtils.loadCredentials("test", "./test/ethereum/keystore/UTC--2018-05-14T19-30-25.355812700Z--b9ac4cc0fd61e113e3340758d855038592d0a778");
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

}
