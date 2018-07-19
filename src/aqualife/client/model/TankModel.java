package aqualife.client.model;

import aqualife.client.controller.ClientForwarder;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class TankModel extends Observable implements Iterable<FishModel> {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 350;
	protected static final int MAX_FISHIES = 5;
	protected static final Random rand = new Random();
	protected volatile BigInteger id;
	protected final Set<FishModel> fishies;
	protected int fishCounter = 0;
	protected final ClientForwarder forwarder;

	private boolean running;

	public TankModel(ClientForwarder forwarder) {
		this.fishies = Collections.newSetFromMap(new ConcurrentHashMap<FishModel, Boolean>());
		this.forwarder = forwarder;
	}

	public synchronized void onRegistration(BigInteger id) {
		this.id = id;
	}

	public synchronized void receiveFish(FishModel fish) {
		fish.setToStart();
		fishies.add(fish);
	}

	public BigInteger getId() {
		return id;
	}

	public String getIdString() {
		return "Tank" + id;
	}

	public synchronized int getFishCounter() {
		return fishCounter;
	}

	public synchronized Iterator<FishModel> iterator() {
		return fishies.iterator();
	}

	private synchronized void updateFishies() {
		for (Iterator<FishModel> it = iterator(); it.hasNext();) {
			FishModel fish = it.next();

			fish.update();

			if (fish.hitsEdge())
				forwarder.handOff(fish);

			if (fish.disappears())
				it.remove();
		}
	}

	private synchronized void update() {
		updateFishies();
		setChanged();
		notifyObservers();
	}

	public void start() {
		running = true;
		run();
	}

	public void stop() {
		running = false;
	}

	private void run() {
		try {
			while (running && !Thread.currentThread().isInterrupted()) {
				update();
				TimeUnit.MILLISECONDS.sleep(10);
			}
		} catch (InterruptedException consumed) {
			// allow method to terminate
		}
	}

}