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
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
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
public class FishBase extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506000805433600160a060020a03199182168117835560018054909216179055610c8590819061004090396000f3006080604052600436106101065763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde03811461010b578063081812fc14610195578063095ea7b3146101c957806323b872dd146101e257806334c2a77a146101ff57806342842e0e14610220578063550e44351461023d5780636352211e1461025257806370a082311461026a5780638462151c1461029d5780638da5cb5b1461030e57806395d89b4114610323578063a22cb46514610338578063b88d4fde1461035e578063c87b56dd1461038a578063dd2f30c2146103a2578063e985e9c5146103cf578063ebd34bdd1461040a578063f2fde38b146104ad575b600080fd5b34801561011757600080fd5b506101206104ce565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561015a578181015183820152602001610142565b50505050905090810190601f1680156101875780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101a157600080fd5b506101ad600435610506565b60408051600160a060020a039092168252519081900360200190f35b6101e0600160a060020a0360043516602435610558565b005b6101e0600160a060020a03600435811690602435166044356105a8565b34801561020b57600080fd5b506101e0600160a060020a036004351661061b565b6101e0600160a060020a0360043581169060243516604435610558565b34801561024957600080fd5b506101ad6106ac565b34801561025e57600080fd5b506101ad6004356106bb565b34801561027657600080fd5b5061028b600160a060020a03600435166106d6565b60408051918252519081900360200190f35b3480156102a957600080fd5b506102be600160a060020a03600435166106f1565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156102fa5781810151838201526020016102e2565b505050509050019250505060405180910390f35b34801561031a57600080fd5b506101ad6107c9565b34801561032f57600080fd5b506101206107d8565b34801561034457600080fd5b506101e0600160a060020a03600435166024351515610558565b6101e0600160a060020a0360048035821691602480359091169160443591606435908101910135610558565b34801561039657600080fd5b5061012060043561080f565b3480156103ae57600080fd5b5061028b60048035600160a060020a03169060248035908101910135610861565b3480156103db57600080fd5b506103f6600160a060020a0360043581169060243516610506565b604080519115158252519081900360200190f35b34801561041657600080fd5b506104226004356109aa565b60405180806020018363ffffffff1663ffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610471578181015183820152602001610459565b50505050905090810190601f16801561049e5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b3480156104b957600080fd5b506101e0600160a060020a0360043516610a79565b60408051808201909152600981527f46697368546f6b656e000000000000000000000000000000000000000000000060208201525b90565b6040805160e560020a62461bcd02815260206004820152601060248201527f4e6f7420696d706c656d656e7465642e000000000000000000000000000000006044820152905160009181900360640190fd5b6040805160e560020a62461bcd02815260206004820152601060248201527f4e6f7420696d706c656d656e7465642e00000000000000000000000000000000604482015290519081900360640190fd5b33600160a060020a038416146105bd57600080fd5b600081815260036020526040902054600160a060020a031633146105e057600080fd5b600160a060020a03821615156105f557600080fd5b600160a060020a03821630141561060b57600080fd5b610616838383610b16565b505050565b600054600160a060020a0316331461067d576040805160e560020a62461bcd02815260206004820152601660248201527f596f7520617265206e6f7420746865206f776e65722e00000000000000000000604482015290519081900360640190fd5b6001805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600154600160a060020a031681565b600090815260036020526040902054600160a060020a031690565b600160a060020a031660009081526004602052604090205490565b600160a060020a0381166000908152600460205260408120546060918290808084151561072e5760408051600081526020810190915295506107bf565b84604051908082528060200260200182016040528015610758578160200160208202803883390190505b506002549094509250600091508190505b828110156107bb57600081815260036020526040902054600160a060020a03888116911614156107b3578084838151811015156107a257fe5b602090810290910101526001909101905b600101610769565b8395505b5050505050919050565b600054600160a060020a031681565b60408051808201909152600281527f4654000000000000000000000000000000000000000000000000000000000000602082015290565b6040805160e560020a62461bcd02815260206004820152601060248201527f4e6f7420696d706c656d656e7465642e000000000000000000000000000000006044820152905160609181900360640190fd5b600061086b610ba9565b600154600090600160a060020a031633146108d0576040805160e560020a62461bcd02815260206004820152601760248201527f596f7520617265206e6f7420617574686f72697a65642e000000000000000000604482015290519081900360640190fd5b6040805160606020601f880181900402820181018352918101868152909182919088908890819085018382808284378201915050505050508152602001610915610ba0565b63ffffffff169052600280546001818101808455600084905284518051959750919490938793027f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace019161096e91839160200190610bc1565b50602091909101516001909101805463ffffffff191663ffffffff9092169190911790550390506109a160008783610b16565b95945050505050565b60606000806002848154811015156109be57fe5b600091825260209182902060029182020160018181015482546040805161010094831615949094026000190190911694909404601f8101869004860283018601909452838252919450849363ffffffff909216929091849190830182828015610a685780601f10610a3d57610100808354040283529160200191610a68565b820191906000526020600020905b815481529060010190602001808311610a4b57829003601f168201915b505050505091509250925050915091565b600054600160a060020a03163314610adb576040805160e560020a62461bcd02815260206004820152601660248201527f596f7520617265206e6f7420746865206f776e65722e00000000000000000000604482015290519081900360640190fd5b600160a060020a03811615610b13576000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b50565b600160a060020a0380841660008181526004602090815260408083208054600019019055938616808352848320805460010190558583526003909152838220805473ffffffffffffffffffffffffffffffffffffffff1916821790559251849392917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4505050565b60001943014090565b60408051808201909152606081526000602082015290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610c0257805160ff1916838001178555610c2f565b82800160010185558215610c2f579182015b82811115610c2f578251825591602001919060010190610c14565b50610c3b929150610c3f565b5090565b61050391905b80821115610c3b5760008155600101610c455600a165627a7a723058201606dc1ca1f24abc3512269b7ad648f596cbd38cd58ac1dbd61ef7d862ed668c0029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_SETCREATEFISHAUTHORIZED = "setCreateFishAuthorized";

    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";

    public static final String FUNC_AUTHORIZEDTOKENCREATOR = "authorizedTokenCreator";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_TOKENSOFOWNER = "tokensOfOwner";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_CREATEFISHTOKEN = "createFishToken";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_GETFISHTOKEN = "getFishToken";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    ;

    protected FishBase(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected FishBase(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getApproved(BigInteger _tokenId) {
        final Function function = new Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String _approved, BigInteger _tokenId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_approved), 
                new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _tokenId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> setCreateFishAuthorized(String _newAuthorized) {
        final Function function = new Function(
                FUNC_SETCREATEFISHAUTHORIZED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newAuthorized)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String _from, String _to, BigInteger _tokenId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> authorizedTokenCreator() {
        final Function function = new Function(FUNC_AUTHORIZEDTOKENCREATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> ownerOf(BigInteger _tokenId) {
        final Function function = new Function(FUNC_OWNEROF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<List> tokensOfOwner(String _owner) {
        final Function function = new Function(FUNC_TOKENSOFOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner)), 
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

    public RemoteCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setApprovalForAll(String _operator, Boolean _approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_operator), 
                new org.web3j.abi.datatypes.Bool(_approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String _from, String _to, BigInteger _tokenId, byte[] data, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_tokenId), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> tokenURI(BigInteger _tokenId) {
        final Function function = new Function(FUNC_TOKENURI, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> createFishToken(String _owner, String _name) {
        final Function function = new Function(
                FUNC_CREATEFISHTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Utf8String(_name)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isApprovedForAll(String _owner, String _operator) {
        final Function function = new Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Tuple2<String, BigInteger>> getFishToken(BigInteger _tokenId) {
        final Function function = new Function(FUNC_GETFISHTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint32>() {}));
        return new RemoteCall<Tuple2<String, BigInteger>>(
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<FishBase> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FishBase.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<FishBase> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FishBase.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventObservable(filter);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._approved = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventObservable(filter);
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalForAllEventResponse> approvalForAllEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.log = log;
                typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ApprovalForAllEventResponse> approvalForAllEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventObservable(filter);
    }

    public static FishBase load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FishBase(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static FishBase load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FishBase(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TransferEventResponse {
        public Log log;

        public String _from;

        public String _to;

        public BigInteger _tokenId;
    }

    public static class ApprovalEventResponse {
        public Log log;

        public String _owner;

        public String _approved;

        public BigInteger _tokenId;
    }

    public static class ApprovalForAllEventResponse {
        public Log log;

        public String _owner;

        public String _operator;

        public Boolean _approved;
    }
}
