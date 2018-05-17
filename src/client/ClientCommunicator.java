package client;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

public class ClientCommunicator {

    public final String BROKER_ADDRESS = "0x46e8bbaca751f1fee0b31fce76279854611899cd";

    private Web3j web3;
    private Credentials credentials;
    private Broker broker;

    public ClientCommunicator() {
        web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/ (Ethereum-Node supporting JSON-RPC)

        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("Client version:\n" + clientVersion);

        try {
            credentials = WalletUtils.loadCredentials("test", "./test/ethereum/keystore/UTC--2018-05-14T19-30-25.355812700Z--b9ac4cc0fd61e113e3340758d855038592d0a778");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }

        broker = Broker.load(BROKER_ADDRESS, web3, credentials, GAS_PRICE, BigInteger.valueOf(2100000L));

        System.out.println("READY");
    }

    public class ClientForwarder {

        private ClientForwarder() {
            // TODO: Implement
        }

        public void register() {

        }

        public void deregister(String id) {
            // TODO: Implement
        }

        public void handOff(FishModel fish) {
            // TODO: Implement
        }
    }

    public class ClientReceiver {
        private final TankModel tankModel;

        private ClientReceiver(TankModel tankModel) {
            this.tankModel = tankModel;
        }

        public void start() {
//            EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
//                    DefaultBlockParameterName.LATEST, BROKER_ADDRESS).addSingleTopic("recipient");

            broker.handoffFishEventObservable(null).subscribe(event -> {
                System.out.println("--- New Handoff Event ---");
                System.out.printf("Recipient: %s%nFishID: %s%nX: %s%nY: %s%nDirection: %s%n",
                        event.recipient, event.id, event.x, event.y, event.direction);

//                if (event.recipient == credentials.getAddress()) {
                    Direction direction = Direction.toDirection(event.direction.intValue());
                    FishModel fish = new FishModel(event.id, event.x.intValue(), event.y.intValue(), direction);
                    tankModel.receiveFish(fish);
//                }

            });
        }

    }

    public ClientForwarder newClientForwarder() {
        return new ClientForwarder();
    }

    public ClientReceiver newClientReceiver(TankModel tankModel) {
        return new ClientReceiver(tankModel);
    }

}
