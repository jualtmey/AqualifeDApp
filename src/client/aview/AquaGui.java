package client.aview;

import client.controller.AqualifeController;
import client.controller.ClientCommunicator;
import client.model.TankModel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class AquaGui extends JFrame implements Runnable, Observer {
	private final List<JMenuItem> fishMenuItems = Collections
			.synchronizedList(new ArrayList<JMenuItem>());

//	private final JMenu searchMenu;
	private final Runnable updateRunnable;

	private AqualifeController aqualifeController;
	private TankModel tankModel;

	public AquaGui(final AqualifeController aqualifeController, ClientCommunicator communicator) {
		this.aqualifeController = aqualifeController;
		this.tankModel = aqualifeController.getTankModel();

		TankView tankView = new TankView(tankModel);
		tankModel.addObserver(tankView);
		add(tankView);

		pack();

		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				tankModel.finish();
				System.exit(0);
			}
		});

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu toolsMenu = new JMenu("Tools");
		menuBar.add(toolsMenu);

		JMenuItem fishMenuItem = new JMenuItem("Fish Menu");
		toolsMenu.add(fishMenuItem);

		fishMenuItem.addActionListener(e -> new FishDialog(this, aqualifeController));

//		searchMenu = new JMenu("Toggle Fish Color...");
//		toolsMenu.add(searchMenu);
//		tankModel.addObserver(this);

		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(new JLabel("ADDR: " + communicator.getAccountAddress() + " | ETH: " + communicator.getBalanceInEther() + " "));

		tankModel.addObserver(this);

		updateRunnable = new Runnable() {
			@Override
			public void run() {
				setTitle(tankModel.getId());
			}
		};
	}

	@Override
	public void run() {
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		SwingUtilities.invokeLater(updateRunnable);
	}

}
