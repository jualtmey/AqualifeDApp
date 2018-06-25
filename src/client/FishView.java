package client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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

	private static Image imgGreyLeft = new ImageIcon(
			FishView.class.getResource("/client/resources/piranha-grey-left.png")).getImage()
			.getScaledInstance(FishModel.getXSize(), -1, Image.SCALE_SMOOTH);

	private static Image imgGreyRight = new ImageIcon(
			FishView.class.getResource("/client/resources/piranha-grey-right.png")).getImage()
			.getScaledInstance(FishModel.getXSize(), -1, Image.SCALE_SMOOTH);

	public Image getImage(FishModel fishModel) {
//		return fishModel.isToggled() ? (fishModel.getDirection() == Direction.LEFT ? imgRedLeft : imgRedRight)
//				: (fishModel.getDirection() == Direction.LEFT ? imgBlackLeft : imgBlackRight);

		Image fishImage = fishModel.getDirection() == Direction.LEFT ? imgGreyLeft : imgGreyRight;

		int uniqueData = fishModel.getFishInfo().getUniqueData();
		int last8BitMask = 0x000000FF;

		int r = uniqueData & last8BitMask;
		int g = (uniqueData >> 8) & last8BitMask;
		int b = (uniqueData >> 16) & last8BitMask;
//		int a = (uniqueData >> 24) & last8BitMask;
		int a = 64;

		return dye(fishImage, new Color(r, g, b, a));
	}

	private static BufferedImage dye(Image image, Color color) {
		// SOURCE: https://stackoverflow.com/questions/21382966/colorize-a-picture-in-java
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dyed.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.setComposite(AlphaComposite.SrcAtop);
		g.setColor(color);
		g.fillRect(0, 0, w, h);
		g.dispose();
		return dyed;
	}

}
