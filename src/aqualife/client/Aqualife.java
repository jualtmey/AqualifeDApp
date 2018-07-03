package aqualife.client;

import aqualife.client.aview.AquaGui;
import aqualife.client.aview.FishView;
import aqualife.client.controller.AqualifeController;
import aqualife.client.controller.ClientCommunicator;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;

public class Aqualife {

    public static void main(String[] args) {
//        AquaGui.setLookAndFeelToSystemDefault();
        AquaGui.setUIFont(new FontUIResource("Arial", Font.PLAIN, 14));

        String pathToWalletFile = AquaGui.showWalletFileChooser();
        if (pathToWalletFile == null) return;

        String accountPassword = AquaGui.showPasswordField();

        ClientCommunicator communicator = new ClientCommunicator(accountPassword, pathToWalletFile);

        AqualifeController aqualifeController = new AqualifeController(communicator);

        new AquaGui(aqualifeController, communicator);

        aqualifeController.start();
    }

}
