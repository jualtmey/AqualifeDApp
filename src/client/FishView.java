package client;

import javax.swing.*;
import java.awt.*;

public class FishView {
	private static Image imgBlackLeft = new ImageIcon(
			FishView.class.getResource("/client/resources/piranha-black-left.png")).getImage()
					.getScaledInstance(FishModel.getXSize(), -1, Image.SCALE_SMOOTH);

	private static Image imgBlackRight = new ImageIcon(
			FishView.class.getResource("/client/resources/piranha-black-right.png")).getImage()
					.getScaledInstance(FishModel.getXSize(), -1, Image.SCALE_SMOOTH);

	private static Image imgRedLeft = new ImageIcon(
			FishView.class.getResource("/client/resources/piranha-red-left.png")).getImage()
					.getScaledInstance(FishModel.getXSize(), -1, Image.SCALE_SMOOTH);

	private static Image imgRedRight = new ImageIcon(
			FishView.class.getResource("/client/resources/piranha-red-right.png")).getImage()
					.getScaledInstance(FishModel.getXSize(), -1, Image.SCALE_SMOOTH);

	public Image getImage(FishModel fishModel) {
		return fishModel.isToggled() ? (fishModel.getDirection() == Direction.LEFT ? imgRedLeft : imgRedRight)
				: (fishModel.getDirection() == Direction.LEFT ? imgBlackLeft : imgBlackRight);
	}
}
