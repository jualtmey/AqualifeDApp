package aqualife.client.contracts;

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
import org.web3j.abi.datatypes.DynamicArray;
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
import org.web3j.tuples.generated.Tuple3;
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
    private static final String BINARY = "608060405234801561001057600080fd5b5060008054600160a060020a0319163317815560408051606081018252828152602081018381529181018381526001805480820182559452905160039093027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf681019390935590517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf7830155517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf8909101805491151560ff199092169190911790556110db806100e16000396000f3006080604052600436106101115763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630498cb6a81146101165780630a8ffe451461013957806311a800bc146101605780631a5dbfb4146101945780631aa3a008146101ac5780632685bc27146101c1578063281ce44d146101ed5780633d4e884c146102025780634bf9944e146102235780634fa049011461023b5780635f7952e91461025057806370f4eee41461027157806380e85803146102895780638da5cb5b146102f1578063949d225d14610306578063aff5edb11461031b578063c1eab69714610330578063d15d9f8114610348578063f2fde38b14610360578063f88af21d14610381575b600080fd5b34801561012257600080fd5b5061013760043560243560443560000b6103b9565b005b34801561014557600080fd5b5061014e6105e9565b60408051918252519081900360200190f35b34801561016c57600080fd5b506101786004356105ef565b60408051600160a060020a039092168252519081900360200190f35b3480156101a057600080fd5b5061013760043561060a565b3480156101b857600080fd5b506101376106f0565b3480156101cd57600080fd5b506101d9600435610995565b604080519115158252519081900360200190f35b3480156101f957600080fd5b5061014e6109d7565b34801561020e57600080fd5b5061014e600160a060020a03600435166109dd565b34801561022f57600080fd5b5061014e6004356109ef565b34801561024757600080fd5b50610178610a01565b34801561025c57600080fd5b50610137600160a060020a0360043516610a10565b34801561027d57600080fd5b5061014e600435610aa1565b34801561029557600080fd5b506102a1600435610ab3565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156102dd5781810151838201526020016102c5565b505050509050019250505060405180910390f35b3480156102fd57600080fd5b50610178610c09565b34801561031257600080fd5b5061014e610c18565b34801561032757600080fd5b50610137610c1e565b34801561033c57600080fd5b5061014e600435610cc5565b34801561035457600080fd5b50610137600435610cd7565b34801561036c57600080fd5b50610137600160a060020a0360043516610f2e565b34801561038d57600080fd5b50610399600435610fcc565b604080519384526020840192909252151582820152519081900360600190f35b336000908152600260205260408120546001805483929081106103d857fe5b600091825260209091206002600390920201015460ff161515610445576040805160e560020a62461bcd02815260206004820152601760248201527f596f7520617265206e6f7420726567697374657265642e000000000000000000604482015290519081900360640190fd5b336000908152600260209081526040808320548884526004909252909120548691146104bb576040805160e560020a62461bcd02815260206004820152601860248201527f466973682069736e277420696e20796f75722074616e6b2e0000000000000000604482015290519081900360640190fd5b33600090815260026020526040812054935084900b60001914156105005760018054849081106104e757fe5b9060005260206000209060030201600001549150610523565b600180548490811061050e57fe5b90600052602060002090600302016001015491505b60008381526006602090815260408083204390558483526005909152902054151561055a5760008281526006602052604090204390555b600086815260046020908152604080832085905585835260058252808320805460001901905584835280832080546001019055600382528083205481518a815292830189905287840b90930b8282015251600160a060020a03909216917f9f94becdd502606a8fca72fc067ad54b86dbf5131deb059c9c6de699473b90219181900360600190a2505050505050565b60075481565b600360205260009081526040902054600160a060020a031681565b61061381610995565b1515610669576040805160e560020a62461bcd02815260206004820152601b60248201527f4f6e6c7920696620636c69656e7420697320696e6163746976652e0000000000604482015290519081900360640190fd5b600180548290811061067757fe5b600091825260209091206002600390920201015460ff1615156106e4576040805160e560020a62461bcd02815260206004820152601d60248201527f4f6e6c7920696620636c69656e7420697320726567697374657265642e000000604482015290519081900360640190fd5b6106ed81611000565b50565b3360009081526002602052604081205460018054909190811061070f57fe5b600091825260209091206002600390920201015460ff161561077b576040805160e560020a62461bcd02815260206004820152601b60248201527f596f752061726520616c726561647920726567697374657265642e0000000000604482015290519081900360640190fd5b503360009081526002602052604090205480151561087e57506040805160608101825260008082526020808301828152838501838152600180548082018255908552945160038087027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf681019290925592517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf782015590517fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf8909101805491151560ff1990921691909117905533808452600283528584208590558484529152929020805473ffffffffffffffffffffffffffffffffffffffff19169092179091555b600954151561089257600781905560088190555b604080516060810182526008548152600754602082015260019181018290528154909190839081106108c057fe5b9060005260206000209060030201600082015181600001556020820151816001015560408201518160020160006101000a81548160ff02191690831515021790555090505080600160075481548110151561091757fe5b600091825260209091206003909102015560085460018054839290811061093a57fe5b600091825260209182902060016003909202010191909155600882905560408051838152905133927e7dc6ab80cc84c043b7b8d4fcafc802187470087f7ea7fccd2e17aecd0256a1928290030190a250600980546001019055565b6000818152600560205260408120548110156109ce5760008281526006602052604090205443600a90910110156109ce575060016109d2565b5060005b919050565b60085481565b60026020526000908152604090205481565b60046020526000908152604090205481565b600a54600160a060020a031681565b600054600160a060020a03163314610a72576040805160e560020a62461bcd02815260206004820152601660248201527f596f7520617265206e6f7420746865206f776e65722e00000000000000000000604482015290519081900360640190fd5b600a805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60066020526000908152604090205481565b60008181526005602052604081205460609182908080841515610ae6576040805160008152602081019091529550610bff565b84604051908082528060200260200182016040528015610b10578160200160208202803883390190505b509350600a60009054906101000a9004600160a060020a0316600160a060020a03166318160ddd6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610b7f57600080fd5b505af1158015610b93573d6000803e3d6000fd5b505050506040513d6020811015610ba957600080fd5b50519250600091508190505b82811015610bfb57600081815260046020526040902054871415610bf357808483815181101515610be257fe5b602090810290910101526001909101905b600101610bb5565b8395505b5050505050919050565b600054600160a060020a031681565b60095481565b33600090815260026020526040902054600180549091908110610c3d57fe5b600091825260209091206002600390920201015460ff161515610caa576040805160e560020a62461bcd02815260206004820152601760248201527f596f7520617265206e6f7420726567697374657265642e000000000000000000604482015290519081900360640190fd5b33600090815260026020526040902054610cc390611000565b565b60056020526000908152604090205481565b33600090815260026020526040812054600180548392908110610cf657fe5b600091825260209091206002600390920201015460ff161515610d63576040805160e560020a62461bcd02815260206004820152601760248201527f596f7520617265206e6f7420726567697374657265642e000000000000000000604482015290519081900360640190fd5b50503360009081526002602090815260408083205484845260049092529091205480821415610ddc576040805160e560020a62461bcd02815260206004820152601860248201527f54616e6b20686f6c6473206669736820616c72656164792e0000000000000000604482015290519081900360640190fd5b600a54604080517f6352211e000000000000000000000000000000000000000000000000000000008152600481018690529051600292600092600160a060020a0390911691636352211e9160248082019260209290919082900301818787803b158015610e4857600080fd5b505af1158015610e5c573d6000803e3d6000fd5b505050506040513d6020811015610e7257600080fd5b5051600160a060020a03168152602081019190915260400160002054821480610e9f5750610e9f81610995565b1515610eaa57600080fd5b8015610ec757600081815260056020526040902080546000190190555b6000838152600460209081526040808320859055848352600582528083208054600101905560068252918290204390558151858152915133927f9eb50f372c72fd3877333cd8dc6526e5cc84feec5d4ccd2efd98cf0dcdcb064092908290030190a2505050565b600054600160a060020a03163314610f90576040805160e560020a62461bcd02815260206004820152601660248201527f596f7520617265206e6f7420746865206f776e65722e00000000000000000000604482015290519081900360640190fd5b600160a060020a038116156106ed5760008054600160a060020a03831673ffffffffffffffffffffffffffffffffffffffff1990911617905550565b6001805482908110610fda57fe5b600091825260209091206003909102018054600182015460029092015490925060ff1683565b600060018281548110151561101157fe5b906000526020600020906003020190506007548214156110345760018101546007555b6008548214156110445780546008555b60018082015482548254919291811061105957fe5b90600052602060002090600302016001018190555080600001546001826001015481548110151561108657fe5b6000918252602090912060039091020155600201805460ff1916905550600980546000190190555600a165627a7a7230582054b22195b81fbbe8aa57d872f657ea5e1db4eb5f73834989558fac598d4edddb0029";

    public static final String FUNC_HANDOFFFISH = "handoffFish";

    public static final String FUNC_FIRSTCLIENT = "firstClient";

    public static final String FUNC_ADDRESSOF = "addressOf";

    public static final String FUNC_DEREGISTER = "deregister";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_ISINACTIVE = "isInactive";

    public static final String FUNC_LASTCLIENT = "lastClient";

    public static final String FUNC_TANKIDOF = "tankIdOf";

    public static final String FUNC_TOKENIDTOCURRENTTANK = "tokenIdToCurrentTank";

    public static final String FUNC_FISHBASE = "fishBase";

    public static final String FUNC_SETFISHBASE = "setFishBase";

    public static final String FUNC_LASTHANDOFF = "lastHandoff";

    public static final String FUNC_GETALLTOKENSINTANK = "getAllTokensInTank";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SIZE = "size";

    public static final String FUNC_TOKENSINTANK = "tokensInTank";

    public static final String FUNC_SUMMONFISH = "summonFish";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_CLIENTS = "clients";

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

    public RemoteCall<BigInteger> firstClient() {
        final Function function = new Function(FUNC_FIRSTCLIENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> addressOf(BigInteger param0) {
        final Function function = new Function(FUNC_ADDRESSOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> deregister(BigInteger _tankId) {
        final Function function = new Function(
                FUNC_DEREGISTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tankId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> register() {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isInactive(BigInteger _tankId) {
        final Function function = new Function(FUNC_ISINACTIVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tankId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> lastClient() {
        final Function function = new Function(FUNC_LASTCLIENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> tankIdOf(String param0) {
        final Function function = new Function(FUNC_TANKIDOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> tokenIdToCurrentTank(BigInteger param0) {
        final Function function = new Function(FUNC_TOKENIDTOCURRENTTANK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> fishBase() {
        final Function function = new Function(FUNC_FISHBASE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setFishBase(String _newAddress) {
        final Function function = new Function(
                FUNC_SETFISHBASE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> lastHandoff(BigInteger param0) {
        final Function function = new Function(FUNC_LASTHANDOFF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<List> getAllTokensInTank(BigInteger _tankId) {
        final Function function = new Function(FUNC_GETALLTOKENSINTANK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tankId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
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

    public RemoteCall<BigInteger> tokensInTank(BigInteger param0) {
        final Function function = new Function(FUNC_TOKENSINTANK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteCall<Tuple3<BigInteger, BigInteger, Boolean>> clients(BigInteger param0) {
        final Function function = new Function(FUNC_CLIENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple3<BigInteger, BigInteger, Boolean>>(
                new Callable<Tuple3<BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
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
