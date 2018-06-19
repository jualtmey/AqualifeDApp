var mining_threads = 1;
var default_passwd = "test";

function checkWork() {
    if (eth.getBlock("pending").transactions.length > 0) {
        if (eth.mining) return;
        console.log("== Pending transactions! Mining...");
        miner.start(mining_threads);
    } else {
        miner.stop();
        console.log("== No transactions! Mining stopped.");
    }
}

function unlockAllAccounts() {
    console.log("== Try to unlock all accounts with password '" + default_passwd + "'...");
	eth.accounts.forEach( function(e) {
        console.log("== Account: " + e);
        var result = personal.unlockAccount(e, default_passwd, 0);
        console.log("== " + result)
    })
}

unlockAllAccounts();

// eth.filter("latest", function(err, block) { checkWork(); });
// eth.filter("pending", function(err, block) { checkWork(); });
//
// checkWork();

miner.start(mining_threads);