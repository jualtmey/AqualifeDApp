package client;

public class ClientCommunicator {

	public class ClientForwarder {

		private ClientForwarder() {
			// TODO: Implement
		}

		public void register() {
			// TODO: Implement
		}

		public void deregister(String id) {
			// TODO: Implement
		}

		public void handOff(FishModel fish) {
			// TODO: Implement
		}
	}

	public class ClientReceiver extends Thread {
		private final TankModel tankModel;

		private ClientReceiver(TankModel tankModel) {
			this.tankModel = tankModel;
		}

	}

	public ClientForwarder newClientForwarder() {
		return new ClientForwarder();
	}

	public ClientReceiver newClientReceiver(TankModel tankModel) {
		return new ClientReceiver(tankModel);
	}

}
