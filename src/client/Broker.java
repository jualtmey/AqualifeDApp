package client;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
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
    private static final String BINARY = "60806040526001805534801561001457600080fd5b506106a2806100246000396000f30060806040526004361061008d5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630a8ffe4581146100925780631aa3a008146100c3578063281ce44d146100da5780635bfb4baf146100ef57806361eed2a914610116578063949d225d1461015f578063aff5edb114610174578063d1fff10b14610189575b600080fd5b34801561009e57600080fd5b506100a76101ef565b60408051600160a060020a039092168252519081900360200190f35b3480156100cf57600080fd5b506100d86101fe565b005b3480156100e657600080fd5b506100a7610301565b3480156100fb57600080fd5b50610104610310565b60408051918252519081900360200190f35b34801561012257600080fd5b50610137600160a060020a0360043516610316565b60408051938452600160a060020a039283166020850152911682820152519081900360600190f35b34801561016b57600080fd5b50610104610343565b34801561018057600080fd5b506100d8610349565b34801561019557600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100d8943694929360249392840191908190840183828082843750949750508435955050506020830135926040013591506104769050565b600354600160a060020a031681565b60025415156102405760038054600160a060020a03331673ffffffffffffffffffffffffffffffffffffffff1991821681179092556004805490911690911790555b6040805160608101825260018054825260048054600160a060020a039081166020808601918252600380548416878901908152338516600081815293849052898420985189559351888801805491871673ffffffffffffffffffffffffffffffffffffffff199283161790559051600298890180549187169183169190911790559054841682528782208601805482168417905584549093168152959095208401805482168617905581541690931790925581548201825580549091019055565b600454600160a060020a031681565b60015481565b6000602081905290815260409020805460018201546002909201549091600160a060020a03908116911683565b60025481565b600160a060020a03338116600081815260208190526040902060035490921614156103a05760028101546003805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039092169190911790555b60045433600160a060020a03908116911614156103e95760018101546004805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039092169190911790555b6002818101805460019384018054600160a060020a039081166000908152602081905260408082208701805495841673ffffffffffffffffffffffffffffffffffffffff1996871617905592549454821681528281208701805495831695851695909517909455331683528220918255928101805484169055810180549092169091558054600019019055565b60001981141561057a5760008033600160a060020a0316600160a060020a0316815260200190815260200160002060010160009054906101000a9004600160a060020a0316600160a060020a03167fa945eec980c3a47c3e570ef82b23f21e985bf873045bae59679cd1d1f295f892858585856040518080602001858152602001848152602001838152602001828103825286818151815260200191508051906020019080838360005b83811015610538578181015183820152602001610520565b50505050905090810190601f1680156105655780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a2610670565b60008033600160a060020a0316600160a060020a0316815260200190815260200160002060020160009054906101000a9004600160a060020a0316600160a060020a03167fa945eec980c3a47c3e570ef82b23f21e985bf873045bae59679cd1d1f295f892858585856040518080602001858152602001848152602001838152602001828103825286818151815260200191508051906020019080838360005b8381101561063257818101518382015260200161061a565b50505050905090810190601f16801561065f5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a25b505050505600a165627a7a723058200966e771359e0f4d4e3f2f052e4017acfdff39c7a6231bc26faa972e069441270029";

    public static final String FUNC_FIRSTCLIENT = "firstClient";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_LASTCLIENT = "lastClient";

    public static final String FUNC_CLIENTIDCOUNTER = "clientIdCounter";

    public static final String FUNC_CLIENTS = "clients";

    public static final String FUNC_SIZE = "size";

    public static final String FUNC_DEREGISTER = "deregister";

    public static final String FUNC_HANDOFFFISH = "handoffFish";

    public static final Event HANDOFFFISH_EVENT = new Event("HandoffFish", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}));
    ;

    protected Broker(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Broker(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
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

    public RemoteCall<BigInteger> clientIdCounter() {
        final Function function = new Function(FUNC_CLIENTIDCOUNTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple3<BigInteger, String, String>> clients(String param0) {
        final Function function = new Function(FUNC_CLIENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        return new RemoteCall<Tuple3<BigInteger, String, String>>(
                new Callable<Tuple3<BigInteger, String, String>>() {
                    @Override
                    public Tuple3<BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
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

    public RemoteCall<TransactionReceipt> handoffFish(String id, BigInteger x, BigInteger y, BigInteger direction) {
        final Function function = new Function(
                FUNC_HANDOFFFISH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(id), 
                new org.web3j.abi.datatypes.generated.Int256(x), 
                new org.web3j.abi.datatypes.generated.Int256(y), 
                new org.web3j.abi.datatypes.generated.Int256(direction)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<HandoffFishEventResponse> getHandoffFishEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(HANDOFFFISH_EVENT, transactionReceipt);
        ArrayList<HandoffFishEventResponse> responses = new ArrayList<HandoffFishEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HandoffFishEventResponse typedResponse = new HandoffFishEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.id = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.x = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.y = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.direction = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
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
                typedResponse.id = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.x = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.y = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.direction = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<HandoffFishEventResponse> handoffFishEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HANDOFFFISH_EVENT));
        return handoffFishEventObservable(filter);
    }

    public static RemoteCall<Broker> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Broker.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Broker> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Broker.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Broker load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Broker(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Broker load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Broker(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class HandoffFishEventResponse {
        public Log log;

        public String recipient;

        public String id;

        public BigInteger x;

        public BigInteger y;

        public BigInteger direction;
    }
}
