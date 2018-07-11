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
import org.web3j.abi.datatypes.generated.Uint256;
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
public class Marketplace extends Contract {
    private static final String BINARY = "608060405267016345785d8a000060035534801561001c57600080fd5b5060008054600160a060020a03191633179055610bed8061003e6000396000f3006080604052600436106100da5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632c28fdf881146100df5780633a84a0e2146100ec5780634fa0490114610113578063550a778b146101445780635f7952e91461017f5780636cc7660c146101a05780638546d002146101bb5780638610f3d6146101e75780638da5cb5b146101fa57806390de06bd1461020f578063a5ea9e6a14610224578063abff01101461023c578063b9b433dd14610251578063bf0d0213146102b6578063f2fde38b146102d7575b600080fd5b6100ea6004356102f8565b005b3480156100f857600080fd5b50610101610511565b60408051918252519081900360200190f35b34801561011f57600080fd5b50610128610517565b60408051600160a060020a039092168252519081900360200190f35b34801561015057600080fd5b5061015c600435610526565b60408051600160a060020a03909316835260208301919091528051918290030190f35b34801561018b57600080fd5b506100ea600160a060020a036004351661054b565b3480156101ac57600080fd5b506100ea6004356024356105ca565b3480156101c757600080fd5b506101d3600435610713565b604080519115158252519081900360200190f35b6100ea6004803560248101910135610730565b34801561020657600080fd5b50610128610843565b34801561021b57600080fd5b506100ea610852565b34801561023057600080fd5b506100ea6004356108e0565b34801561024857600080fd5b50610128610967565b34801561025d57600080fd5b50610266610976565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156102a257818101518382015260200161028a565b505050509050019250505060405180910390f35b3480156102c257600080fd5b506100ea600160a060020a0360043516610a96565b3480156102e357600080fd5b506100ea600160a060020a0360043516610b15565b600081815260016020526040902061030f82610713565b1515610365576040805160e560020a62461bcd02815260206004820152601660248201527f546f6b656e206973206e6f7420666f722073616c652e00000000000000000000604482015290519081900360640190fd5b60018101543410156103c1576040805160e560020a62461bcd02815260206004820152601160248201527f4e6f7420656e6f756768206d6f6e65792e000000000000000000000000000000604482015290519081900360640190fd5b6005548154604080517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a03928316600482015233602482015260448101869052905191909216916323b872dd91606480830192600092919082900301818387803b15801561043757600080fd5b505af115801561044b573d6000803e3d6000fd5b505082546001840154604051600160a060020a03909216935080156108fc029250906000818181858888f1935050505015801561048c573d6000803e3d6000fd5b5080546001820154604080519182525184923392600160a060020a0391909116917f681ddc67ea8796d2489979f5fc2ea2eb0f2d44ff3f11f061a191928a1f3d9a069181900360200190a45060009081526001602081905260408220805473ffffffffffffffffffffffffffffffffffffffff19168155015560028054600019019055565b60035481565b600554600160a060020a031681565b60016020819052600091825260409091208054910154600160a060020a039091169082565b600054600160a060020a0316331461059b576040805160e560020a62461bcd0281526020600482015260166024820152600080516020610ba2833981519152604482015290519081900360640190fd5b6005805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600554604080517f6352211e0000000000000000000000000000000000000000000000000000000081526004810185905290513392600160a060020a031691636352211e9160248083019260209291908290030181600087803b15801561063057600080fd5b505af1158015610644573d6000803e3d6000fd5b505050506040513d602081101561065a57600080fd5b5051600160a060020a03161461066f57600080fd5b61067882610713565b1515610688576002805460010190555b60408051808201825233808252602080830185815260008781526001808452908690209451855473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909116178555905193019290925582518481529251859391927f4a68fde121386ffcfc9555407bfb38625033a0d0d3726b00f56b4ab304b55447928290030190a35050565b600090815260016020526040902054600160a060020a0316151590565b6003543481111561078b576040805160e560020a62461bcd02815260206004820152601160248201527f4e6f7420656e6f756768206d6f6e65792e000000000000000000000000000000604482015290519081900360640190fd5b600554604080517f3d02d0c900000000000000000000000000000000000000000000000000000000815233600482018181526024830193845260448301879052600160a060020a0390941693633d02d0c993919288928892916064018484808284378201915050945050505050602060405180830381600087803b15801561081257600080fd5b505af1158015610826573d6000803e3d6000fd5b505050506040513d602081101561083c57600080fd5b5050505050565b600054600160a060020a031681565b600054600160a060020a031633146108a2576040805160e560020a62461bcd0281526020600482015260166024820152600080516020610ba2833981519152604482015290519081900360640190fd5b60008054604051600160a060020a0390911691303180156108fc02929091818181858888f193505050501580156108dd573d6000803e3d6000fd5b50565b600081815260016020526040902054600160a060020a0316331461090357600080fd5b60405181907f79d50744e568d2dcbac1c613dc0f174be90b0121dbc73682de80b3c4ebc5d1e790600090a260009081526001602081905260408220805473ffffffffffffffffffffffffffffffffffffffff19168155015560028054600019019055565b600454600160a060020a031681565b60608060008060006002546040519080825280602002602001820160405280156109aa578160200160208202803883390190505b509350600560009054906101000a9004600160a060020a0316600160a060020a03166318160ddd6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610a1957600080fd5b505af1158015610a2d573d6000803e3d6000fd5b505050506040513d6020811015610a4357600080fd5b50519250600091508190505b82811015610a8d57610a6081610713565b15610a8557808483815181101515610a7457fe5b602090810290910101526001909101905b600101610a4f565b50919392505050565b600054600160a060020a03163314610ae6576040805160e560020a62461bcd0281526020600482015260166024820152600080516020610ba2833981519152604482015290519081900360640190fd5b6004805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600054600160a060020a03163314610b65576040805160e560020a62461bcd0281526020600482015260166024820152600080516020610ba2833981519152604482015290519081900360640190fd5b600160a060020a038116156108dd5760008054600160a060020a03831673ffffffffffffffffffffffffffffffffffffffff19909116179055505600596f7520617265206e6f7420746865206f776e65722e00000000000000000000a165627a7a7230582089350912334f0076364bca561abcb88d97120df8a8a326d005b379b02584b64f0029";

    public static final String FUNC_BUYFISH = "buyFish";

    public static final String FUNC_NEWFISHPRICE = "newFishPrice";

    public static final String FUNC_FISHBASE = "fishBase";

    public static final String FUNC_SALEOF = "saleOf";

    public static final String FUNC_SETFISHBASE = "setFishBase";

    public static final String FUNC_OFFERFISH = "offerFish";

    public static final String FUNC_ISFORSALE = "isForSale";

    public static final String FUNC_BUYNEWFISH = "buyNewFish";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TRANSFERCONTRACTBALANCETOOWNER = "transferContractBalanceToOwner";

    public static final String FUNC_CANCELFISHOFFER = "cancelFishOffer";

    public static final String FUNC_BROKER = "broker";

    public static final String FUNC_LISTALLSALES = "listAllSales";

    public static final String FUNC_SETBROKER = "setBroker";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event OFFER_EVENT = new Event("Offer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event SALE_EVENT = new Event("Sale", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event CANCELLATION_EVENT = new Event("Cancellation", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList());
    ;

    protected Marketplace(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Marketplace(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> buyFish(BigInteger _tokenId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYFISH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> newFishPrice() {
        final Function function = new Function(FUNC_NEWFISHPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> fishBase() {
        final Function function = new Function(FUNC_FISHBASE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple2<String, BigInteger>> saleOf(BigInteger param0) {
        final Function function = new Function(FUNC_SALEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
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

    public RemoteCall<TransactionReceipt> setFishBase(String _newAddress) {
        final Function function = new Function(
                FUNC_SETFISHBASE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> offerFish(BigInteger _tokenId, BigInteger _price) {
        final Function function = new Function(
                FUNC_OFFERFISH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId), 
                new org.web3j.abi.datatypes.generated.Uint256(_price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isForSale(BigInteger _tokenId) {
        final Function function = new Function(FUNC_ISFORSALE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> buyNewFish(String _name, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYNEWFISH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferContractBalanceToOwner() {
        final Function function = new Function(
                FUNC_TRANSFERCONTRACTBALANCETOOWNER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> cancelFishOffer(BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_CANCELFISHOFFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> broker() {
        final Function function = new Function(FUNC_BROKER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<List> listAllSales() {
        final Function function = new Function(FUNC_LISTALLSALES, 
                Arrays.<Type>asList(), 
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

    public RemoteCall<TransactionReceipt> setBroker(String _newAddress) {
        final Function function = new Function(
                FUNC_SETBROKER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newAddress)), 
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

    public static RemoteCall<Marketplace> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Marketplace.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Marketplace> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Marketplace.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<OfferEventResponse> getOfferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OFFER_EVENT, transactionReceipt);
        ArrayList<OfferEventResponse> responses = new ArrayList<OfferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OfferEventResponse typedResponse = new OfferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OfferEventResponse> offerEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, OfferEventResponse>() {
            @Override
            public OfferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OFFER_EVENT, log);
                OfferEventResponse typedResponse = new OfferEventResponse();
                typedResponse.log = log;
                typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<OfferEventResponse> offerEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OFFER_EVENT));
        return offerEventObservable(filter);
    }

    public List<SaleEventResponse> getSaleEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SALE_EVENT, transactionReceipt);
        ArrayList<SaleEventResponse> responses = new ArrayList<SaleEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SaleEventResponse typedResponse = new SaleEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SaleEventResponse> saleEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SaleEventResponse>() {
            @Override
            public SaleEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SALE_EVENT, log);
                SaleEventResponse typedResponse = new SaleEventResponse();
                typedResponse.log = log;
                typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.price = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SaleEventResponse> saleEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SALE_EVENT));
        return saleEventObservable(filter);
    }

    public List<CancellationEventResponse> getCancellationEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CANCELLATION_EVENT, transactionReceipt);
        ArrayList<CancellationEventResponse> responses = new ArrayList<CancellationEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CancellationEventResponse typedResponse = new CancellationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CancellationEventResponse> cancellationEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, CancellationEventResponse>() {
            @Override
            public CancellationEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CANCELLATION_EVENT, log);
                CancellationEventResponse typedResponse = new CancellationEventResponse();
                typedResponse.log = log;
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<CancellationEventResponse> cancellationEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANCELLATION_EVENT));
        return cancellationEventObservable(filter);
    }

    public static Marketplace load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Marketplace(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Marketplace load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Marketplace(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class OfferEventResponse {
        public Log log;

        public String seller;

        public BigInteger tokenId;

        public BigInteger price;
    }

    public static class SaleEventResponse {
        public Log log;

        public String seller;

        public String buyer;

        public BigInteger tokenId;

        public BigInteger price;
    }

    public static class CancellationEventResponse {
        public Log log;

        public BigInteger tokenId;
    }
}
