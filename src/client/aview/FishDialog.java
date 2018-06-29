package client.aview;

import client.Util;
import client.controller.AqualifeController;
import client.model.FishInfo;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.List;

public class FishDialog extends JDialog {

    private DefaultListModel<FishInfo> ownedFishTokenList;
    private DefaultListModel<FishInfo> forSaleFishTokenList;

    private AqualifeController aqualifeController;

    public FishDialog(JFrame parent, AqualifeController aqualifeController) {
        super(parent, true);

        this.aqualifeController = aqualifeController;

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centerPanel.add(new TopPanel());
        centerPanel.add(new BottomPanel());

        add(centerPanel);

        fillOwnedFishTokenList();
        fillForSaleFishTokenList();

        setResizable(false);
        setTitle("Fish Menu");
        pack();
        setLocationRelativeTo(parent);
    }

    private void fillListModel(DefaultListModel<FishInfo> listModel, List<FishInfo> list) {
        listModel.clear();
        list.forEach(info -> listModel.addElement(info));
    }

    public void fillOwnedFishTokenList() {
        fillListModel(ownedFishTokenList, aqualifeController.getFishInfoOfOwnedFishToken());
    }

    public void fillForSaleFishTokenList() {
        fillListModel(forSaleFishTokenList, aqualifeController.getForSaleFishInfo());
    }

    private class TopPanel extends JPanel {

        public TopPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Owned Fish Token"));

            ownedFishTokenList = new DefaultListModel<>();

            JList topList = new JList(ownedFishTokenList);
            topList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            topList.setLayoutOrientation(JList.VERTICAL);
            topList.setVisibleRowCount(-1);
            topList.setFont(new Font("Courier New", Font.PLAIN, 14));

            JScrollPane topListScroller = new JScrollPane(topList);
            topListScroller.setPreferredSize(new Dimension(600, 120));

            JPanel topButtonsPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

            JButton summonButton = new JButton("Summon");
            summonButton.addActionListener(e -> {
                FishInfo fishInfo = (FishInfo) topList.getSelectedValue();
                if (fishInfo == null) {
                    return;
                }
                aqualifeController.summonFish(fishInfo.getTokenId());
            });

            JButton renameButton = new JButton("Rename");
            renameButton.addActionListener(e -> {
                FishInfo fishInfo = (FishInfo) topList.getSelectedValue();
                if (fishInfo == null) {
                    return;
                }

                String name = JOptionPane.showInputDialog("Set new name:");
                if (name.isEmpty()) {
                    return;
                }

                aqualifeController.renameFish(fishInfo, name);
            });

            JButton sellButton = new JButton("Sell");
            sellButton.addActionListener(e -> {
                FishInfo fishInfo = (FishInfo) topList.getSelectedValue();
                if (fishInfo == null) {
                    return;
                }

                String priceInEther = JOptionPane.showInputDialog("Specify price (in Ether):");
                if (priceInEther.isEmpty()) {
                    return;
                }

                aqualifeController.offerFish(fishInfo, Double.parseDouble(priceInEther));
            });

            JButton cancelSellButton = new JButton("Cancel Sell");
            cancelSellButton.addActionListener(e -> {
                FishInfo fishInfo = (FishInfo) topList.getSelectedValue();
                if (fishInfo == null) {
                    return;
                }

                aqualifeController.cancelSell(fishInfo);
            });

            topButtonsPanel.add(summonButton);
            topButtonsPanel.add(renameButton);
            topButtonsPanel.add(sellButton);
            topButtonsPanel.add(cancelSellButton);

            add(topListScroller);
            add(topButtonsPanel);
        }
    }

    private class BottomPanel extends JPanel {

        public BottomPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Marketplace - For Sale"));

            forSaleFishTokenList = new DefaultListModel<>();

            JList bottomList = new JList(forSaleFishTokenList);
            bottomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            bottomList.setLayoutOrientation(JList.VERTICAL);
            bottomList.setVisibleRowCount(-1);
            bottomList.setFont(new Font("Courier New", Font.PLAIN, 14));

            JScrollPane bottomListScroller = new JScrollPane(bottomList);
            bottomListScroller.setPreferredSize(new Dimension(600, 120));

            JPanel bottomButtonsPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

            JButton buyButton = new JButton("Buy Selected");
            buyButton.addActionListener(e -> {
                FishInfo selectedFish = (FishInfo) bottomList.getSelectedValue();
                int selectedOption = JOptionPane.showConfirmDialog(
                        this, "Do you really want to buy this token?\n" + selectedFish, "Confirm", JOptionPane.YES_NO_OPTION);
                if (selectedOption == JOptionPane.YES_OPTION) {
                    aqualifeController.buyFish(selectedFish);
                }
            });

            JButton buyNewButton = new JButton("Buy New");
            buyNewButton.addActionListener(e -> {
                BigInteger price = aqualifeController.getNewFishPrice();
                String name = JOptionPane.showInputDialog(
                        "The creation of a new FishToken costs " + Util.convertWeiToEther(price) + " Ether.\n" +
                                "Set name of fish:");
                if (name != null) {
                    aqualifeController.buyNewFish(name);
                }
            });

            bottomButtonsPanel.add(buyButton);
            bottomButtonsPanel.add(buyNewButton);

            add(bottomListScroller);
            add(bottomButtonsPanel);
        }
    }

}
