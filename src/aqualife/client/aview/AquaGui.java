package aqualife.client.aview;

import aqualife.client.controller.AqualifeController;
import aqualife.client.controller.ClientCommunicator;
import aqualife.client.model.Event;
import aqualife.client.model.TankModel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;

@SuppressWarnings("serial")
public class AquaGui extends JFrame implements Observer {
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

        fishDialog = new FishDialog(this, aqualifeController);

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

        add(tankView);

        pack();

        setTitle("Not registered");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private void refreshBalance() {
        balanceLabel.setText("ETH: " + communicator.getBalanceInEther() + " ");
    }

    public static String showWalletFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:/Users/Julian/Desktop/AqualifeDApp/test/ethereum/keystore"));
        fileChooser.setDialogTitle("Select Wallet File (Account)");

        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            return file.getAbsolutePath();
        }
        return null;
    }

    public static String showPasswordField() {
        String password = JOptionPane.showInputDialog("Enter password (visible): ");
        return password;
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

    public static void setLookAndFeelToSystemDefault() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                case MARKETPLACE:
                    fishDialog.fillOwnedFishTokenList();
                    fishDialog.fillForSaleFishTokenList();
                    break;
                case NEW_BLOCK:
                    refreshBalance();
                    break;
            }
        }
    }
}
