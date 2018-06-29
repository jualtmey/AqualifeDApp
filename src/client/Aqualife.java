package client;

import client.aview.AquaGui;
import client.controller.AqualifeController;
import client.controller.ClientCommunicator;

import javax.swing.*;
import java.awt.*;

public class Aqualife {

    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 14));

        ClientCommunicator communicator = new ClientCommunicator();

        communicator.setAccountAddress(showAccountAddressSelection(communicator.getAccounts()));

        AqualifeController aqualifeController = new AqualifeController(communicator);

        SwingUtilities.invokeLater(new AquaGui(aqualifeController, communicator));

        aqualifeController.start();
    }

    public static String showAccountAddressSelection(String[] accounts) {
        String selectedAccount = (String) JOptionPane.showInputDialog(
                null,
                "Select your account (account must be unlocked):\n",
                "Account Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                accounts,
                accounts[0]);

        if (selectedAccount == null) {
            // TODO: exit or error
            return null;
        }

        return selectedAccount;
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }

}
