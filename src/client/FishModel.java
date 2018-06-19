package client;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

@SuppressWarnings("serial")
public final class FishModel implements Serializable {
	private final static int xSize = 100;
	private final static int ySize = 50;
	private final static Random rand = new Random();

	private final FishToken fishToken;
	private int x;
	private int y;
	private Direction direction;

	private boolean toggled;

	public FishModel(FishToken fishToken, int x, int y, Direction direction) {
		this.fishToken = fishToken;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public BigInteger getTokenId() {
		return fishToken.getTokenId();
	}

	public FishToken getFishToken() {
		return fishToken;
	}

	public String getId() {
		return "[" + fishToken.getTokenId() + "]" + fishToken.getName() + "@Tank" + fishToken.getOwnerTankId();
	}

	public String getTankId() {
		return "Tank" + fishToken.getOwnerTankId().intValue();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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
		x += direction.getVector();

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
