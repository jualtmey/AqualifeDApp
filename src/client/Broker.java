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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Int8;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
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
    private static final String BINARY = "608060405260018055670de0b6b3a764000060065534801561002057600080fd5b503061002a61007d565b600160a060020a03909116815260405190819003602001906000f080158015610057573d6000803e3d6000fd5b5060058054600160a060020a031916600160a060020a039290921691909117905561008d565b604051610d1c8061127a83390190565b6111de8061009c6000396000f3006080604052600436106100b95763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630498cb6a81146100be5780630a8ffe45146100e15780631aa3a00814610112578063281ce44d146101275780634e727dec1461013c5780634fa04901146101635780635bfb4baf1461017857806361eed2a91461018d578063949d225d146101e0578063aff5edb1146101f5578063be3791bb1461020a578063d15d9f8114610256575b600080fd5b3480156100ca57600080fd5b506100df60043560243560443560000b61026e565b005b3480156100ed57600080fd5b506100f6610653565b60408051600160a060020a039092168252519081900360200190f35b34801561011e57600080fd5b506100df610662565b34801561013357600080fd5b506100f661084e565b34801561014857600080fd5b5061015161085d565b60408051918252519081900360200190f35b34801561016f57600080fd5b506100f6610863565b34801561018457600080fd5b50610151610872565b34801561019957600080fd5b506101ae600160a060020a0360043516610878565b60408051948552600160a060020a03938416602086015291909216838201529015156060830152519081900360800190f35b3480156101ec57600080fd5b506101516108b2565b34801561020157600080fd5b506100df6108b8565b6040805160206004803580820135601f81018490048402850184019095528484526100df943694929360249392840191908190840183828082843750949750610a429650505050505050565b34801561026257600080fd5b506100df600435610ddd565b336000908152602081905260408120600201546060908290819060a060020a900460ff1615156102d6576040805160e560020a62461bcd0281526020600482015260176024820152600080516020611193833981519152604482015290519081900360640190fd5b6000198560000b14156103065733600090815260208190526040902060010154600160a060020a03169350610325565b33600090815260208190526040902060020154600160a060020a031693505b600554604080517f332d86d7000000000000000000000000000000000000000000000000000000008152336004820152600160a060020a038781166024830152604482018b90529151919092169163332d86d791606480830192600092919082900301818387803b15801561039957600080fd5b505af11580156103ad573d6000803e3d6000fd5b5050600554604080517febd34bdd000000000000000000000000000000000000000000000000000000008152600481018c90529051600160a060020a03909216935063ebd34bdd925060248082019260009290919082900301818387803b15801561041757600080fd5b505af115801561042b573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052606081101561045457600080fd5b81019080805164010000000081111561046c57600080fd5b8201602081018481111561047f57600080fd5b815164010000000081118282018710171561049957600080fd5b5050602091820151600554604080517f6352211e000000000000000000000000000000000000000000000000000000008152600481018f9052905193995091975060009550859450600160a060020a031692636352211e9260248084019382900301818787803b15801561050c57600080fd5b505af1158015610520573d6000803e3d6000fd5b505050506040513d602081101561053657600080fd5b8101908080519060200190929190505050600160a060020a0316600160a060020a0316815260200190815260200160002060000154905083600160a060020a03167fe507cda9ebb588f1d6a0b89358a2b311c15379462c140989b68f5f80a12029d7888585858b8b60405180878152602001806020018663ffffffff1663ffffffff1681526020018581526020018481526020018360000b60000b8152602001828103825287818151815260200191508051906020019080838360005b8381101561060b5781810151838201526020016105f3565b50505050905090810190601f1680156106385780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390a250505050505050565b600354600160a060020a031681565b3360009081526020819052604081206002015460a060020a900460ff16156106d4576040805160e560020a62461bcd02815260206004820152601b60248201527f596f752061726520616c726561647920726567697374657265642e0000000000604482015290519081900360640190fd5b600254151561070d57600380543373ffffffffffffffffffffffffffffffffffffffff1991821681179092556004805490911690911790555b50336000908152602081905260409020548015156107315750600180548082019091555b6040805160808101825282815260048054600160a060020a0390811660208085019182526003805484168688019081526001606088018181523360008181528087528b81209a518b5596518a84018054918a1673ffffffffffffffffffffffffffffffffffffffff19928316179055935160029a8b0180549351151560a060020a0274ff000000000000000000000000000000000000000019928b1694871694909417919091169290921790915592548616855288852001805482168317905585549094168352918690209094018054831682179055825490911681179091558251848152925190927e7dc6ab80cc84c043b7b8d4fcafc802187470087f7ea7fccd2e17aecd0256a192908290030190a250600280546001019055565b600454600160a060020a031681565b60065481565b600554600160a060020a031681565b60015481565b6000602081905290815260409020805460018201546002909201549091600160a060020a03908116919081169060a060020a900460ff1684565b60025481565b3360009081526020819052604081206002015460a060020a900460ff161515610919576040805160e560020a62461bcd0281526020600482015260176024820152600080516020611193833981519152604482015290519081900360640190fd5b503360008181526020819052604090206003549091600160a060020a0390911614156109715760028101546003805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039092169190911790555b600454600160a060020a03163314156109b65760018101546004805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039092169190911790555b6002818101805460019384018054600160a060020a039081166000908152602081905260408082208701805495841673ffffffffffffffffffffffffffffffffffffffff199687161790559254855483168252929020909501805491909516911617909255815474ff000000000000000000000000000000000000000019169091558054600019019055565b33600090815260208190526040812060020154606090829060a060020a900460ff161515610aa8576040805160e560020a62461bcd0281526020600482015260176024820152600080516020611193833981519152604482015290519081900360640190fd5b60065434811115610b03576040805160e560020a62461bcd02815260206004820152601160248201527f4e6f7420656e6f756768206d6f6e65792e000000000000000000000000000000604482015290519081900360640190fd5b600554604080517fbccda5b9000000000000000000000000000000000000000000000000000000008152336004820181815260248301938452895160448401528951600160a060020a039095169463bccda5b99492938b9391606490910190602085019080838360005b83811015610b85578181015183820152602001610b6d565b50505050905090810190601f168015610bb25780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610bd257600080fd5b505af1158015610be6573d6000803e3d6000fd5b505050506040513d6020811015610bfc57600080fd5b5051600554604080517febd34bdd000000000000000000000000000000000000000000000000000000008152600481018490529051929650600160a060020a039091169163ebd34bdd9160248082019260009290919082900301818387803b158015610c6757600080fd5b505af1158015610c7b573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526060811015610ca457600080fd5b810190808051640100000000811115610cbc57600080fd5b82016020810184811115610ccf57600080fd5b8151640100000000811182820187101715610ce957600080fd5b505060209182015133600081815280855260408082205481518d815263ffffffff861692810192909252606082018190526080820183905260a0820183905260c08288018181528751918401919091528651969c50949a509297507fe507cda9ebb588f1d6a0b89358a2b311c15379462c140989b68f5f80a12029d796508b958b958b95849392909160e0840191890190808383885b83811015610d97578181015183820152602001610d7f565b50505050905090810190601f168015610dc45780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390a25050505050565b336000908152602081905260408120600201546060919060a060020a900460ff161515610e42576040805160e560020a62461bcd0281526020600482015260176024820152600080516020611193833981519152604482015290519081900360640190fd5b600554604080517f6352211e000000000000000000000000000000000000000000000000000000008152600481018690529051600160a060020a0390921691636352211e916024808201926020929091908290030181600087803b158015610ea957600080fd5b505af1158015610ebd573d6000803e3d6000fd5b505050506040513d6020811015610ed357600080fd5b5051600160a060020a03163314610f34576040805160e560020a62461bcd02815260206004820152601d60248201527f4f6e6c7920666f72206f776e657273206f66207468697320666973682e000000604482015290519081900360640190fd5b600554604080517f7e0b92d5000000000000000000000000000000000000000000000000000000008152336004820152602481018690529051600160a060020a0390921691637e0b92d59160448082019260009290919082900301818387803b158015610fa057600080fd5b505af1158015610fb4573d6000803e3d6000fd5b5050600554604080517febd34bdd000000000000000000000000000000000000000000000000000000008152600481018890529051600160a060020a03909216935063ebd34bdd925060248082019260009290919082900301818387803b15801561101e57600080fd5b505af1158015611032573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052606081101561105b57600080fd5b81019080805164010000000081111561107357600080fd5b8201602081018481111561108657600080fd5b81516401000000008111828201871017156110a057600080fd5b505060209182015133600081815280855260408082205481518c815263ffffffff861692810192909252606082018190526080820183905260a0820183905260c08288018181528751918401919091528651969b509499509297507fe507cda9ebb588f1d6a0b89358a2b311c15379462c140989b68f5f80a12029d796508a958a958a95849392909160e0840191890190808383885b8381101561114e578181015183820152602001611136565b50505050905090810190601f16801561117b5780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390a25050505600596f7520617265206e6f7420726567697374657265642e000000000000000000a165627a7a7230582016f681edce846b08bb7df46c0e46f753c12f8e2464f4837d285c0fc08555b6360029608060405234801561001057600080fd5b50604051602080610d1c833981016040525160008054600160a060020a03909216600160a060020a0319909216919091179055610cca806100526000396000f3006080604052600436106100fb5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde038114610100578063081812fc1461018a578063095ea7b3146101be57806323b872dd146101d7578063332d86d7146101f457806342842e0e1461021e5780636352211e1461023b57806370a08231146102535780637e0b92d5146102865780638462151c146102aa57806395d89b411461031b578063a22cb46514610330578063abff011014610356578063b88d4fde1461036b578063bccda5b914610397578063c87b56dd146103fe578063e985e9c514610416578063ebd34bdd14610451575b600080fd5b34801561010c57600080fd5b5061011561050d565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561014f578181015183820152602001610137565b50505050905090810190601f16801561017c5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561019657600080fd5b506101a2600435610545565b60408051600160a060020a039092168252519081900360200190f35b6101d5600160a060020a0360043516602435610597565b005b6101d5600160a060020a03600435811690602435166044356105e7565b34801561020057600080fd5b506101d5600160a060020a036004358116906024351660443561065a565b6101d5600160a060020a0360043581169060243516604435610597565b34801561024757600080fd5b506101a260043561074e565b34801561025f57600080fd5b50610274600160a060020a0360043516610769565b60408051918252519081900360200190f35b34801561029257600080fd5b506101d5600160a060020a0360043516602435610784565b3480156102b657600080fd5b506102cb600160a060020a03600435166107e4565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156103075781810151838201526020016102ef565b505050509050019250505060405180910390f35b34801561032757600080fd5b506101156108bc565b34801561033c57600080fd5b506101d5600160a060020a03600435166024351515610597565b34801561036257600080fd5b506101a26108f3565b6101d5600160a060020a0360048035821691602480359091169160443591606435908101910135610597565b3480156103a357600080fd5b5060408051602060046024803582810135601f8101859004850286018501909652858552610274958335600160a060020a03169536956044949193909101919081908401838280828437509497506109029650505050505050565b34801561040a57600080fd5b50610115600435610a1d565b34801561042257600080fd5b5061043d600160a060020a0360043581169060243516610545565b604080519115158252519081900360200190f35b34801561045d57600080fd5b50610469600435610a6f565b60405180806020018463ffffffff1663ffffffff16815260200183600160a060020a0316600160a060020a03168152602001828103825285818151815260200191508051906020019080838360005b838110156104d05781810151838201526020016104b8565b50505050905090810190601f1680156104fd5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b60408051808201909152600981527f46697368546f6b656e000000000000000000000000000000000000000000000060208201525b90565b6040805160e560020a62461bcd02815260206004820152601060248201527f4e6f7420696d706c656d656e7465642e000000000000000000000000000000006044820152905160009181900360640190fd5b6040805160e560020a62461bcd02815260206004820152601060248201527f4e6f7420696d706c656d656e7465642e00000000000000000000000000000000604482015290519081900360640190fd5b33600160a060020a038416146105fc57600080fd5b600081815260026020526040902054600160a060020a0316331461061f57600080fd5b600160a060020a038216151561063457600080fd5b600160a060020a03821630141561064a57600080fd5b610655838383610b53565b505050565b600054600160a060020a0316331461067157600080fd5b82600160a060020a031660018281548110151561068a57fe5b60009182526020909120600290910201600101546401000000009004600160a060020a031614610704576040805160e560020a62461bcd02815260206004820152601960248201527f46697368206973206e6f7420696e20796f75722074616e6b2e00000000000000604482015290519081900360640190fd5b8160018281548110151561071457fe5b906000526020600020906002020160010160046101000a815481600160a060020a030219169083600160a060020a03160217905550505050565b600090815260026020526040902054600160a060020a031690565b600160a060020a031660009081526003602052604090205490565b600054600160a060020a0316331461079b57600080fd5b816001828154811015156107ab57fe5b906000526020600020906002020160010160046101000a815481600160a060020a030219169083600160a060020a031602179055505050565b600160a060020a038116600090815260036020526040812054606091829080808415156108215760408051600081526020810190915295506108b2565b8460405190808252806020026020018201604052801561084b578160200160208202803883390190505b506001549094509250600091508190505b828110156108ae57600081815260026020526040902054600160a060020a03888116911614156108a65780848381518110151561089557fe5b602090810290910101526001909101905b60010161085c565b8395505b5050505050919050565b60408051808201909152600281527f4654000000000000000000000000000000000000000000000000000000000000602082015290565b600054600160a060020a031681565b600061090c610be6565b60008054600160a060020a0316331461092457600080fd5b60606040519081016040528085815260200161093e610bdd565b63ffffffff168152600160a060020a0387166020918201526001805480820180835560008390528451805195975092949093879360029093027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf601926109aa9284929190910190610c06565b50602082015160019091018054604090930151600160a060020a03166401000000000277ffffffffffffffffffffffffffffffffffffffff000000001963ffffffff90931663ffffffff199094169390931791909116919091179055039050610a1560008683610b53565b949350505050565b6040805160e560020a62461bcd02815260206004820152601060248201527f4e6f7420696d706c656d656e7465642e000000000000000000000000000000006044820152905160609181900360640190fd5b60606000806000600185815481101515610a8557fe5b600091825260209182902060029182020160018181015482546040805161010094831615949094026000190190911694909404601f8101869004860283018601909452838252919450849363ffffffff831693640100000000909304600160a060020a0316928591830182828015610b3e5780601f10610b1357610100808354040283529160200191610b3e565b820191906000526020600020905b815481529060010190602001808311610b2157829003601f168201915b50505050509250935093509350509193909250565b600160a060020a0380841660008181526003602090815260408083208054600019019055938616808352848320805460010190558583526002909152838220805473ffffffffffffffffffffffffffffffffffffffff1916821790559251849392917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef91a4505050565b60001943014090565b604080516060818101835281526000602082018190529181019190915290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610c4757805160ff1916838001178555610c74565b82800160010185558215610c74579182015b82811115610c74578251825591602001919060010190610c59565b50610c80929150610c84565b5090565b61054291905b80821115610c805760008155600101610c8a5600a165627a7a723058204a5dd1bc50440abc2684c2403a98b173087854ad0b78c61e39fb282b4b68c6040029";

    public static final String FUNC_HANDOFFFISH = "handoffFish";

    public static final String FUNC_FIRSTCLIENT = "firstClient";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_LASTCLIENT = "lastClient";

    public static final String FUNC_CREATEFISHPRICE = "createFishPrice";

    public static final String FUNC_FISHBASE = "fishBase";

    public static final String FUNC_CLIENTIDCOUNTER = "clientIdCounter";

    public static final String FUNC_CLIENTS = "clients";

    public static final String FUNC_SIZE = "size";

    public static final String FUNC_DEREGISTER = "deregister";

    public static final String FUNC_CREATEFISH = "createFish";

    public static final String FUNC_SUMMONFISH = "summonFish";

    public static final Event REGISTER_EVENT = new Event("Register", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event HANDOFFFISH_EVENT = new Event("HandoffFish", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int8>() {}));
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

    public RemoteCall<BigInteger> createFishPrice() {
        final Function function = new Function(FUNC_CREATEFISHPRICE, 
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

    public RemoteCall<BigInteger> clientIdCounter() {
        final Function function = new Function(FUNC_CLIENTIDCOUNTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteCall<TransactionReceipt> createFish(String _name, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CREATEFISH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> summonFish(BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_SUMMONFISH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tokenId)), 
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
            typedResponse.fishName = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.fishUniqueData = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.ownerTankId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.y = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.direction = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
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
                typedResponse.fishName = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.fishUniqueData = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.ownerTankId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.y = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.direction = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<HandoffFishEventResponse> handoffFishEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HANDOFFFISH_EVENT));
        return handoffFishEventObservable(filter);
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

        public String fishName;

        public BigInteger fishUniqueData;

        public BigInteger ownerTankId;

        public BigInteger y;

        public BigInteger direction;
    }
}
