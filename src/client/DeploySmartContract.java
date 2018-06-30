package client;

import client.aview.AquaGui;
import client.contracts.Broker;
import client.contracts.FishBase;
import client.contracts.Marketplace;
import client.controller.ClientCommunicator;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DeploySmartContract {

    private static final String TITLE = "Deploy Smart Contract";
    private static final String PATH_CONTRACT_ADDRESSES = "./contract_address.txt";

    public static void main(String[] args) {
        deployOnGeth();
    }

    public static void deployOnGeth() {
        // load account and initialize connection to ethereum node
        String pathToWalletFile = AquaGui.showWalletFileChooser();
        if (pathToWalletFile == null) return;

        String accountPassword = AquaGui.showPasswordField();

        ClientCommunicator communicator = new ClientCommunicator(accountPassword, pathToWalletFile);


        JOptionPane.showMessageDialog(null,
                "Using address: " + communicator.getAccountAddress() + "\n" +
                        "Deploying and initializing smart contracts (Broker, FishBase, Marketplace)...",
                TITLE, JOptionPane.INFORMATION_MESSAGE);


        // deploy smart contracts
        Broker broker;
        FishBase fishBase;
        Marketplace marketplace;

        CompletableFuture<Broker> brokerFuture;
        CompletableFuture<FishBase> fishBaseFuture;
        CompletableFuture<Marketplace> marketplaceFuture;

        brokerFuture = communicator.deployBroker();
        fishBaseFuture = communicator.deployFishBase();
        marketplaceFuture = communicator.deployMarketplace();

        try {
            broker = brokerFuture.get();
            fishBase = fishBaseFuture.get();
            marketplace = marketplaceFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Address of Broker: " + broker.getContractAddress());
        System.out.println("Address of FishBase: " + fishBase.getContractAddress());
        System.out.println("Address of Marketplace: " + marketplace.getContractAddress());


        // configure smart contracts
        List<CompletableFuture> futures = new LinkedList<>();

        futures.add(broker.setFishBase(fishBase.getContractAddress()).sendAsync().
                thenRun(() -> System.out.println("Broker - configured FishBase")));
        futures.add(fishBase.setCreateFishAuthorized(marketplace.getContractAddress()).sendAsync().
                thenRun(() -> System.out.println("FishBase - configured CreateFishAuthorized")));
        futures.add(marketplace.setBroker(broker.getContractAddress()).sendAsync().
                thenRun(() -> System.out.println("Marketplace - configured Broker")));
        futures.add(marketplace.setFishBase(fishBase.getContractAddress()).sendAsync().
                thenRun(() -> System.out.println("Marketplace - configured FishBase")));

        for (CompletableFuture future : futures) {
            future.join();
        }

        // write contract addresses to file
        StringBuilder data = new StringBuilder();
        data.append("Broker Address:" + broker.getContractAddress() + "\n");
        data.append("FishBase Address:" + fishBase.getContractAddress() + "\n");
        data.append("Marketplace Address:" + marketplace.getContractAddress() + "\n");

        Path contractAddressesPath = null;
        try {
            contractAddressesPath = Files.write(Paths.get(PATH_CONTRACT_ADDRESSES), data.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


        JOptionPane.showMessageDialog(null,
                "Done.\nAddresses written to file:\n"
                        + contractAddressesPath.toAbsolutePath(), TITLE, JOptionPane.INFORMATION_MESSAGE);


        System.exit(0);
    }

}
