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
    private static final String BINARY = "608060405234801561001057600080fd5b506000805433600160a060020a031991821681178355600180549092161790556110b290819061004090396000f30060806040526004361061011c5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde038114610121578063081812fc146101ab578063095ea7b3146101df57806323b872dd146101f857806334c2a77a146102155780633d02d0c91461023657806342842e0e146102755780634fa0490114610292578063550e4435146102a75780636352211e146102bc57806370a08231146102d45780638462151c146102f55780638da5cb5b1461036657806395d89b411461037b578063a22cb46514610390578063b88d4fde146103b6578063c87b56dd146103e2578063e985e9c5146103fa578063ebd34bdd14610435578063f2fde38b146104d8578063ff781453146104f9575b600080fd5b34801561012d57600080fd5b5061013661051d565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610170578181015183820152602001610158565b50505050905090810190601f16801561019d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101b757600080fd5b506101c3600435610555565b60408051600160a060020a039092168252519081900360200190f35b6101f6600160a060020a0360043516602435610570565b005b6101f6600160a060020a0360043581169060243516604435610629565b34801561022157600080fd5b506101f6600160a060020a0360043516610771565b34801561024257600080fd5b5061026360048035600160a060020a03169060248035908101910135610802565b60408051918252519081900360200190f35b6101f6600160a060020a036004358116906024351660443561097c565b34801561029e57600080fd5b506101c36109cc565b3480156102b357600080fd5b506101c36109db565b3480156102c857600080fd5b506101c36004356109ea565b3480156102e057600080fd5b50610263600160a060020a0360043516610a05565b34801561030157600080fd5b50610316600160a060020a0360043516610a20565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561035257818101518382015260200161033a565b505050509050019250505060405180910390f35b34801561037257600080fd5b506101c3610af8565b34801561038757600080fd5b50610136610b07565b34801561039c57600080fd5b506101f6600160a060020a03600435166024351515610b3e565b6101f6600160a060020a036004803582169160248035909116916044359160643590810191013561097c565b3480156103ee57600080fd5b50610136600435610bc2565b34801561040657600080fd5b50610421600160a060020a0360043581169060243516610c14565b604080519115158252519081900360200190f35b34801561044157600080fd5b5061044d600435610c42565b60405180806020018363ffffffff1663ffffffff168152602001828103825284818151815260200191508051906020019080838360005b8381101561049c578181015183820152602001610484565b50505050905090810190601f1680156104c95780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b3480156104e457600080fd5b506101f6600160a060020a0360043516610d11565b34801561050557600080fd5b506101f6600480359060248035908101910135610dae565b60408051808201909152600981527f46697368546f6b656e000000000000000000000000000000000000000000000060208201525b90565b600090815260046020526040902054600160a060020a031690565b600081815260036020526040902054600160a060020a0390811690831681141561059957600080fd5b33600160a060020a03821614806105b557506105b58133610c14565b15156105c057600080fd5b600082815260046020526040808220805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0387811691821790925591518593918516917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591a4505050565b600081815260036020526040902054600160a060020a0316151561064c57600080fd5b33600160a060020a03841614806106795750600081815260046020526040902054600160a060020a031633145b8061068957506106898333610c14565b151561069457600080fd5b600081815260036020526040902054600160a060020a038481169116146106ba57600080fd5b600160a060020a03821615156106cf57600080fd5b600160a060020a0382163014156106e557600080fd5b6106f0838383610e02565b600081815260046020526040902054600160a060020a03161561076c57600081815260046020526040808220805473ffffffffffffffffffffffffffffffffffffffff1916905551829190600160a060020a038616907f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925908390a45b505050565b600054600160a060020a031633146107d3576040805160e560020a62461bcd02815260206004820152601660248201527f596f7520617265206e6f7420746865206f776e65722e00000000000000000000604482015290519081900360640190fd5b6001805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600061080c610f68565b600154600090600160a060020a03163314610871576040805160e560020a62461bcd02815260206004820152601760248201527f596f7520617265206e6f7420617574686f72697a65642e000000000000000000604482015290519081900360640190fd5b6040805160606020601f8801819004028201810183529181018681529091829190889088908190850183828082843782019150505050505081526020016108e787878080601f01602080910402602001604051908101604052809392919081815260200183838082843750610e8c945050505050565b63ffffffff169052600280546001818101808455600084905284518051959750919490938793027f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace019161094091839160200190610f80565b50602091909101516001909101805463ffffffff191663ffffffff90921691909117905503905061097360008783610e02565b95945050505050565b6040805160e560020a62461bcd02815260206004820152601060248201527f4e6f7420696d706c656d656e7465642e00000000000000000000000000000000604482015290519081900360640190fd5b600754600160a060020a031681565b600154600160a060020a031681565b600090815260036020526040902054600160a060020a031690565b600160a060020a031660009081526005602052604090205490565b600160a060020a03811660009081526005602052604081205460609182908080841515610a5d576040805160008152602081019091529550610aee565b84604051908082528060200260200182016040528015610a87578160200160208202803883390190505b506002549094509250600091508190505b82811015610aea57600081815260036020526040902054600160a060020a0388811691161415610ae257808483815181101515610ad157fe5b602090810290910101526001909101905b600101610a98565b8395505b5050505050919050565b600054600160a060020a031681565b60408051808201909152600281527f4654000000000000000000000000000000000000000000000000000000000000602082015290565b600160a060020a038216331415610b5457600080fd5b336000818152600660209081526040808320600160a060020a03871680855290835292819020805460ff1916861515908117909155815190815290519293927f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31929181900390910190a35050565b6040805160e560020a62461bcd02815260206004820152601060248201527f4e6f7420696d706c656d656e7465642e000000000000000000000000000000006044820152905160609181900360640190fd5b600160a060020a03918216600090815260066020908152604080832093909416825291909152205460ff1690565b6060600080600284815481101515610c5657fe5b600091825260209182902060029182020160018181015482546040805161010094831615949094026000190190911694909404601f8101869004860283018601909452838252919450849363ffffffff909216929091849190830182828015610d005780601f10610cd557610100808354040283529160200191610d00565b820191906000526020600020905b815481529060010190602001808311610ce357829003601f168201915b505050505091509250925050915091565b600054600160a060020a03163314610d73576040805160e560020a62461bcd02815260206004820152601660248201527f596f7520617265206e6f7420746865206f776e65722e00000000000000000000604482015290519081900360640190fd5b600160a060020a03811615610dab576000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b50565b600083815260036020526040902054600160a060020a03163314610dd157600080fd5b8181600285815481101515610de257fe5b60009182526020909120610dfc9360029092020191610ffe565b50505050565b600160a060020a0380841660008181526005602090815260408083208054600019019055938616808352848320805460010190558583526003909152838220805473ffffffffffffffffffffffffffffffffffffffff1916821790559251849392917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4505050565b60025460408051600019430140602080830182815286516000969395889592939201918501908083835b60208310610ed55780518252601f199092019160209182019101610eb6565b51815160209384036101000a60001901801990921691161790529201938452506040805180850381529382019081905283519395509350839290850191508083835b60208310610f365780518252601f199092019160209182019101610f17565b5181516020939093036101000a6000190180199091169216919091179052604051920182900390912095945050505050565b60408051808201909152606081526000602082015290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610fc157805160ff1916838001178555610fee565b82800160010185558215610fee579182015b82811115610fee578251825591602001919060010190610fd3565b50610ffa92915061106c565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061103f5782800160ff19823516178555610fee565b82800160010185558215610fee579182015b82811115610fee578235825591602001919060010190611051565b61055291905b80821115610ffa57600081556001016110725600a165627a7a72305820390ff7e091a1edd7cd328972e3e1506e97d7d682b89791d69f29b0fba2025cc30029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_SETCREATEFISHAUTHORIZED = "setCreateFishAuthorized";

    public static final String FUNC_MINTTOKEN = "mintToken";

    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";

    public static final String FUNC_FISHBASE = "fishBase";

    public static final String FUNC_AUTHORIZEDTOKENCREATOR = "authorizedTokenCreator";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_TOKENSOFOWNER = "tokensOfOwner";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_GETFISHTOKEN = "getFishToken";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_CHANGEFISHNAME = "changeFishName";

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

    public RemoteCall<TransactionReceipt> mintToken(String _owner, String _name) {
        final Function function = new Function(
                FUNC_MINTTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Utf8String(_name)), 
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

    public RemoteCall<String> fishBase() {
        final Function function = new Function(FUNC_FISHBASE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<TransactionReceipt> changeFishName(BigInteger _tokenId, String _name) {
        final Function function = new Function(
                FUNC_CHANGEFISHNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId), 
                new org.web3j.abi.datatypes.Utf8String(_name)), 
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
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
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
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
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
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
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
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
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
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
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

        public String from;

        public String to;

        public BigInteger tokenId;
    }

    public static class ApprovalEventResponse {
        public Log log;

        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse {
        public Log log;

        public String owner;

        public String operator;

        public Boolean approved;
    }
}
