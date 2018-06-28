package client.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

@SuppressWarnings("serial")
public final class FishModel implements Serializable {
	private final static int xSize = 100;
	private final static int ySize = 50;
	private final static Random rand = new Random();

	private final FishInfo fishInfo;
	private double x;
	private double y;
	private Direction direction;

	private boolean toggled;

	public FishModel(FishInfo fishInfo, int x, int y, Direction direction) {
		this.fishInfo = fishInfo;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public BigInteger getTokenId() {
		return fishInfo.getTokenId();
	}

	public FishInfo getFishInfo() {
		return fishInfo;
	}

	public String getId() {
		return "[" + fishInfo.getTokenId() + "]" + fishInfo.getName() + "@Tank" + fishInfo.getOwnerTankId();
	}

	public String getTankId() {
		return "Tank" + fishInfo.getOwnerTankId().intValue();
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public Direction getDirection() {
		return direction;
	}

	public void reverse() {
		direction = direction.reverse();
	}

	public static int getXSize() {
		return xSize;
	}

	public static int getYSize() {
		return ySize;
	}

	public void toggle() {
		toggled = !toggled;
	}

	public boolean isToggled() {
		return toggled;
	}

	public boolean hitsEdge() {
		return (direction == Direction.LEFT && x == 0)
				|| (direction == Direction.RIGHT && x == TankModel.WIDTH - xSize);
	}

	public boolean disappears() {
		return (direction == Direction.LEFT && x == -xSize)
				|| (direction == Direction.RIGHT && x == TankModel.WIDTH);
	}

	public void update() {
		x += direction.getVector() * 0.5;

		double discreteSin = Math.round(Math.sin(x / 30.0));
		discreteSin = rand.nextInt(10) < 8 ? 0 : discreteSin;
		y += discreteSin;
		y = y < 0 ? 0 : y > TankModel.HEIGHT - FishModel.getYSize() ? TankModel.HEIGHT
				- FishModel.getYSize() : y;
	}

	public void setToStart() {
		x = direction == Direction.LEFT ? TankModel.WIDTH : -xSize;
	}

	public boolean isDeparting() {
		return (direction == Direction.LEFT && x < 0)
				|| (direction == Direction.RIGHT && x > TankModel.WIDTH - xSize);
	}

}
