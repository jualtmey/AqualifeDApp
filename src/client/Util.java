package client;

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Util {

    private Util() {}

    public static BigDecimal convertWeiToEther(BigInteger wei) {
        return Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
    }

    public static BigInteger convertEtherToWei(BigDecimal ether) {
        return Convert.toWei(ether, Convert.Unit.ETHER).toBigInteger();
    }

}
