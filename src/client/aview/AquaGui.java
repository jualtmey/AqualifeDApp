package client.aview;

import client.controller.AqualifeController;
import client.controller.ClientCommunicator;
import client.model.Event;
import client.model.TankModel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

@SuppressWarnings("serial")
public class AquaGui extends JFrame implements Runnable, Observer {
    private final List<JMenuItem> fishMenuItems = Collections
            .synchronizedList(new ArrayList<JMenuItem>());

    private AqualifeController aqualifeController;
    private ClientCommunicator communicator;
    private TankModel tankModel;

    private FishDialog fishDialog;
    private JLabel balanceLabel;

    public AquaGui(final AqualifeController aqualifeController, ClientCommunicator communicator) {
        this.aqualifeController = aqualifeController;
        this.communicator = communicator;
        this.tankModel = aqualifeController.getTankModel();

        TankView tankView = new TankView(tankModel);
        tankModel.addObserver(tankView);
        add(tankView);

        fishDialog = new FishDialog(this, aqualifeController);

        pack();

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setTitle("Deregister and exit...");
                setEnabled(false);
                aqualifeController.stop();
            }
        });

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu toolsMenu = new JMenu("Tools");
        menuBar.add(toolsMenu);

        JMenuItem fishMenuItem = new JMenuItem("Fish Menu");
        fishMenuItem.addActionListener(e -> fishDialog.setVisible(true));
        toolsMenu.add(fishMenuItem);

        balanceLabel = new JLabel("ETH: ");
        refreshBalance();

        menuBar.add(new JSeparator(SwingConstants.VERTICAL));
        menuBar.add(new JLabel("ADDR: " + communicator.getAccountAddress() + " | "));
        menuBar.add(balanceLabel);

        tankModel.addObserver(this);
        aqualifeController.addObserver(this);

        setTitle("Not registered");
    }

    private void refreshBalance() {
        balanceLabel.setText("ETH: " + communicator.getBalanceInEther() + " ");
    }

    @Override
    public void run() {
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
		if (o instanceof AqualifeController && arg != null && arg instanceof Event) {
            Event event = (Event) arg;
            switch (event) {
                case REGISTRATION:
                    setTitle(tankModel.getId());
                    break;
                case FISHBASE:
                    fishDialog.fillOwnedFishTokenList();
//                    JOptionPane.showMessageDialog(this, "You own a new FishToken: " + event.tokenId);
                    break;
                case MARKETPLACE:
                    fishDialog.fillForSaleFishTokenList();
                    break;
                case NEW_BLOCK:
                    refreshBalance();
                    break;
            }
        }
    }

}
