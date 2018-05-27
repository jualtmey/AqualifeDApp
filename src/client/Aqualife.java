package client;

import javax.swing.*;

public class Aqualife {

    public static void main(String[] args) {
        ClientCommunicator communicator = new ClientCommunicator();


//        AqualifeController aqualifeController = new AqualifeController(communicator);

        TankModel tankModel = new TankModel(communicator.newClientForwarder());

        SwingUtilities.invokeLater(new AquaGui(tankModel, communicator));

        communicator.newClientReceiver(tankModel).start();

        tankModel.run();
    }
}
