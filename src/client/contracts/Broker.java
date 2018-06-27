package client.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Int8;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Broker extends Contract {
    private static final String BINARY = "6080604052600160035534801561001557600080fd5b5060008054600160a060020a03191633179055610b92806100376000396000f3006080604052600436106100cf5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630498cb6a81146100d45780630a8ffe45146100f75780631aa3a00814610128578063281ce44d1461013d5780634bf9944e146101525780634fa049011461016a5780635bfb4baf1461017f5780635f7952e9146101a657806361eed2a9146101c75780638da5cb5b1461021a578063949d225d1461022f578063aff5edb114610244578063d15d9f8114610259578063f2fde38b14610271575b600080fd5b3480156100e057600080fd5b506100f560043560243560443560000b610292565b005b34801561010357600080fd5b5061010c61043a565b60408051600160a060020a039092168252519081900360200190f35b34801561013457600080fd5b506100f5610449565b34801561014957600080fd5b5061010c61061c565b34801561015e57600080fd5b5061010c60043561062b565b34801561017657600080fd5b5061010c610646565b34801561018b57600080fd5b50610194610655565b60408051918252519081900360200190f35b3480156101b257600080fd5b506100f5600160a060020a036004351661065b565b3480156101d357600080fd5b506101e8600160a060020a03600435166106df565b60408051948552600160a060020a03938416602086015291909216838201529015156060830152519081900360800190f35b34801561022657600080fd5b5061010c610718565b34801561023b57600080fd5b50610194610727565b34801561025057600080fd5b506100f561072d565b34801561026557600080fd5b506100f56004356108a8565b34801561027d57600080fd5b506100f5600160a060020a0360043516610ad6565b3360009081526001602052604081206002015460a060020a900460ff161515610305576040805160e560020a62461bcd02815260206004820152601760248201527f596f7520617265206e6f7420726567697374657265642e000000000000000000604482015290519081900360640190fd5b6000848152600260205260409020548490600160a060020a03163314610375576040805160e560020a62461bcd02815260206004820152601860248201527f466973682069736e277420696e20796f75722074616e6b2e0000000000000000604482015290519081900360640190fd5b6000198360000b14156103a6573360009081526001602081905260409091200154600160a060020a031691506103c5565b33600090815260016020526040902060020154600160a060020a031691505b60008581526002602090815260408083208054600160a060020a038716600160a060020a03199091168117909155815189815292830188905286840b90930b82820152517f9f94becdd502606a8fca72fc067ad54b86dbf5131deb059c9c6de699473b90219181900360600190a25050505050565b600554600160a060020a031681565b3360009081526001602052604081206002015460a060020a900460ff16156104bb576040805160e560020a62461bcd02815260206004820152601b60248201527f596f752061726520616c726561647920726567697374657265642e0000000000604482015290519081900360640190fd5b60045415156104e7576005805433600160a060020a031991821681179092556006805490911690911790555b503360009081526001602052604090205480151561050c575060038054600181019091555b6040805160808101825282815260068054600160a060020a0390811660208085019182526005805484168688019081526001606088018181523360008181528387528b81209a518b5596518a84018054918a16600160a060020a0319928316179055935160029a8b0180549351151560a060020a0274ff000000000000000000000000000000000000000019928b1694871694909417919091169290921790915592548616855288852001805482168317905585549094168352918690209094018054831682179055825490911681179091558251848152925190927e7dc6ab80cc84c043b7b8d4fcafc802187470087f7ea7fccd2e17aecd0256a192908290030190a250600480546001019055565b600654600160a060020a031681565b600260205260009081526040902054600160a060020a031681565b600754600160a060020a031681565b60035481565b600054600160a060020a031633146106bd576040805160e560020a62461bcd02815260206004820152601660248201527f596f7520617265206e6f7420746865206f776e65722e00000000000000000000604482015290519081900360640190fd5b60078054600160a060020a031916600160a060020a0392909216919091179055565b6001602081905260009182526040909120805491810154600290910154600160a060020a039182169181169060a060020a900460ff1684565b600054600160a060020a031681565b60045481565b3360009081526001602052604081206002015460a060020a900460ff1615156107a0576040805160e560020a62461bcd02815260206004820152601760248201527f596f7520617265206e6f7420726567697374657265642e000000000000000000604482015290519081900360640190fd5b503360008181526001602052604090206005549091600160a060020a0390911614156107eb57600281015460058054600160a060020a031916600160a060020a039092169190911790555b600654600160a060020a031633141561082357600181015460068054600160a060020a031916600160a060020a039092169190911790555b6002818101805460019384018054600160a060020a039081166000908152602087905260408082209096018054948316600160a060020a0319958616179055915484548216835294909120909401805493909416921691909117909155805474ff00000000000000000000000000000000000000001916905560048054600019019055565b3360009081526001602052604090206002015460a060020a900460ff16151561091b576040805160e560020a62461bcd02815260206004820152601760248201527f596f7520617265206e6f7420726567697374657265642e000000000000000000604482015290519081900360640190fd5b600081815260026020526040902054600160a060020a031633141561098a576040805160e560020a62461bcd02815260206004820152601860248201527f54616e6b20686f6c6473206669736820616c72656164792e0000000000000000604482015290519081900360640190fd5b600754604080517f6352211e000000000000000000000000000000000000000000000000000000008152600481018490529051600160a060020a0390921691636352211e916024808201926020929091908290030181600087803b1580156109f157600080fd5b505af1158015610a05573d6000803e3d6000fd5b505050506040513d6020811015610a1b57600080fd5b5051600160a060020a03163314610a7c576040805160e560020a62461bcd02815260206004820152601c60248201527f466f72206f776e6572206f6620746869732066697368206f6e6c792e00000000604482015290519081900360640190fd5b6000818152600260209081526040918290208054600160a060020a031916339081179091558251848152925190927f9eb50f372c72fd3877333cd8dc6526e5cc84feec5d4ccd2efd98cf0dcdcb064092908290030190a250565b600054600160a060020a03163314610b38576040805160e560020a62461bcd02815260206004820152601660248201527f596f7520617265206e6f7420746865206f776e65722e00000000000000000000604482015290519081900360640190fd5b600160a060020a03811615610b635760008054600160a060020a031916600160a060020a0383161790555b505600a165627a7a72305820fcf1dee88462e1fe3f7fb169864f58335b738edd57d693626fd212e188ad2a960029";

    public static final String FUNC_HANDOFFFISH = "handoffFish";

    public static final String FUNC_FIRSTCLIENT = "firstClient";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_LASTCLIENT = "lastClient";

    public static final String FUNC_TOKENIDTOCURRENTTANK = "tokenIdToCurrentTank";

    public static final String FUNC_FISHBASE = "fishBase";

    public static final String FUNC_CLIENTIDCOUNTER = "clientIdCounter";

    public static final String FUNC_SETFISHBASE = "setFishBase";

    public static final String FUNC_CLIENTS = "clients";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SIZE = "size";

    public static final String FUNC_DEREGISTER = "deregister";

    public static final String FUNC_SUMMONFISH = "summonFish";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event REGISTER_EVENT = new Event("Register", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event HANDOFFFISH_EVENT = new Event("HandoffFish", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int8>() {}));
    ;

    public static final Event SUMMONFISH_EVENT = new Event("SummonFish", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    protected Broker(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Broker(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> handoffFish(BigInteger _tokenId, BigInteger _y, BigInteger _direction) {
        final Function function = new Function(
                FUNC_HANDOFFFISH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId), 
                new org.web3j.abi.datatypes.generated.Int256(_y), 
                new org.web3j.abi.datatypes.generated.Int8(_direction)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> firstClient() {
        final Function function = new Function(FUNC_FIRSTCLIENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> register() {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> lastClient() {
        final Function function = new Function(FUNC_LASTCLIENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> tokenIdToCurrentTank(BigInteger param0) {
        final Function function = new Function(FUNC_TOKENIDTOCURRENTTANK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> fishBase() {
        final Function function = new Function(FUNC_FISHBASE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> clientIdCounter() {
        final Function function = new Function(FUNC_CLIENTIDCOUNTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setFishBase(String _newAddress) {
        final Function function = new Function(
                FUNC_SETFISHBASE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple4<BigInteger, String, String, Boolean>> clients(String param0) {
        final Function function = new Function(FUNC_CLIENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple4<BigInteger, String, String, Boolean>>(
                new Callable<Tuple4<BigInteger, String, String, Boolean>>() {
                    @Override
                    public Tuple4<BigInteger, String, String, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, String, String, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> size() {
        final Function function = new Function(FUNC_SIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> deregister() {
        final Function function = new Function(
                FUNC_DEREGISTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> summonFish(BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_SUMMONFISH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Broker> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Broker.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Broker> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Broker.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<RegisterEventResponse> getRegisterEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTER_EVENT, transactionReceipt);
        ArrayList<RegisterEventResponse> responses = new ArrayList<RegisterEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisterEventResponse typedResponse = new RegisterEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.tankId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RegisterEventResponse> registerEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, RegisterEventResponse>() {
            @Override
            public RegisterEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REGISTER_EVENT, log);
                RegisterEventResponse typedResponse = new RegisterEventResponse();
                typedResponse.log = log;
                typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.tankId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<RegisterEventResponse> registerEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTER_EVENT));
        return registerEventObservable(filter);
    }

    public List<HandoffFishEventResponse> getHandoffFishEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(HANDOFFFISH_EVENT, transactionReceipt);
        ArrayList<HandoffFishEventResponse> responses = new ArrayList<HandoffFishEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HandoffFishEventResponse typedResponse = new HandoffFishEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.y = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.direction = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<HandoffFishEventResponse> handoffFishEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, HandoffFishEventResponse>() {
            @Override
            public HandoffFishEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(HANDOFFFISH_EVENT, log);
                HandoffFishEventResponse typedResponse = new HandoffFishEventResponse();
                typedResponse.log = log;
                typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.y = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.direction = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<HandoffFishEventResponse> handoffFishEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HANDOFFFISH_EVENT));
        return handoffFishEventObservable(filter);
    }

    public List<SummonFishEventResponse> getSummonFishEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SUMMONFISH_EVENT, transactionReceipt);
        ArrayList<SummonFishEventResponse> responses = new ArrayList<SummonFishEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SummonFishEventResponse typedResponse = new SummonFishEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SummonFishEventResponse> summonFishEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SummonFishEventResponse>() {
            @Override
            public SummonFishEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SUMMONFISH_EVENT, log);
                SummonFishEventResponse typedResponse = new SummonFishEventResponse();
                typedResponse.log = log;
                typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SummonFishEventResponse> summonFishEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUMMONFISH_EVENT));
        return summonFishEventObservable(filter);
    }

    public static Broker load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Broker(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Broker load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Broker(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class RegisterEventResponse {
        public Log log;

        public String recipient;

        public BigInteger tankId;
    }

    public static class HandoffFishEventResponse {
        public Log log;

        public String recipient;

        public BigInteger tokenId;

        public BigInteger y;

        public BigInteger direction;
    }

    public static class SummonFishEventResponse {
        public Log log;

        public String recipient;

        public BigInteger tokenId;
    }
}
