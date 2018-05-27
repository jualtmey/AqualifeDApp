package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class AquaGui extends JFrame implements Runnable, Observer {
	private final List<JMenuItem> fishMenuItems = Collections
			.synchronizedList(new ArrayList<JMenuItem>());

	private final JMenu searchMenu;
	private final Runnable updateRunnable;

	public AquaGui(final TankModel tankModel, ClientCommunicator communicator) {
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

		JMenuItem gsMenuItem = new JMenuItem("Global Snapshot");
		toolsMenu.add(gsMenuItem);

		gsMenuItem.addActionListener(new NotImplementedYetController(this));

		searchMenu = new JMenu("Toggle Fish Color...");
		toolsMenu.add(searchMenu);
		tankModel.addObserver(this);

		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(new JLabel("Address: " + communicator.getAddress() + " "));

		updateRunnable = new Runnable() {
			@Override
			public void run() {
				setTitle(tankModel.getId());

				int size = fishMenuItems.size();
				while (tankModel.getFishCounter() > size) {
					String fishId = "fish" + (++size) + "@" + tankModel.getId();
					JMenuItem fishMenuItem = new JMenuItem(fishId);
					fishMenuItem.addActionListener(new NotImplementedYetController(AquaGui.this));
					fishMenuItems.add(fishMenuItem);
					searchMenu.add(fishMenuItem);
				}
			}
		};
	}

	public void showAccountSelectionDialog(String[] accounts) {

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
