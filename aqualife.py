import os
import sys
import shutil
import json


NAME_SMART_CONTRACT = "Broker"

PATH_SMART_CONTRACT_SRC = "./src/broker/"
PATH_SMART_CONTRACT_OUT = "./contract/"
PATH_GETH_DATADIR = "./test/ethereum/"
PATH_ACCOUNT_PASSWD = "./test/passwd.txt"

ACCOUNT_NUM = 5


def create_accounts():
    print("Create " + str(ACCOUNT_NUM) + " accounts...") 
    print("Password for each account specified in '" + PATH_ACCOUNT_PASSWD + "'")
    for i in range(ACCOUNT_NUM):
        os.system("geth --datadir " + PATH_GETH_DATADIR + " account new --password " + PATH_ACCOUNT_PASSWD)


def init():
    print("Initialize test blockchain...")
    os.system("geth --networkid 55 init ./test/Genesis.json --datadir " + PATH_GETH_DATADIR)


def compile_contract():
    print("Compile smart contract...")

    if not os.path.exists(PATH_SMART_CONTRACT_OUT):
        print("Create contract output folder '" + PATH_SMART_CONTRACT_OUT + "'...")
        os.makedirs(PATH_SMART_CONTRACT_OUT)

    path_contract_src = PATH_SMART_CONTRACT_SRC + NAME_SMART_CONTRACT + ".sol"
    os.system("solc " + path_contract_src + " --bin --abi --optimize --overwrite -o " + PATH_SMART_CONTRACT_OUT)


def generate_web3j_wrapper():
    print("Generate web3j wrapper class...")

    path_contract_bin = PATH_SMART_CONTRACT_OUT + NAME_SMART_CONTRACT + ".bin"
    path_contract_abi = PATH_SMART_CONTRACT_OUT + NAME_SMART_CONTRACT + ".abi"
    
    os.system("web3j solidity generate " + path_contract_bin + " " + path_contract_abi + " -o ./src/ -p client")


def build():
    compile_contract()
    generate_web3j_wrapper()


def deploy_contract():
    pass


def clean_geth_db():
    os.system("geth removedb --datadir " + PATH_GETH_DATADIR)


def clean():
    clean_geth_db()
    shutil.rmtree(PATH_GETH_DATADIR)
    shutil.rmtree(PATH_SMART_CONTRACT_OUT)


def run():
    os.system('geth --networkid 55 --nodiscover --maxpeers 0 --rpc --rpccorsdomain "https://remix.ethereum.org" --datadir ' + PATH_GETH_DATADIR + ' --preload script.js console')


def print_help():
    print("Possible arguments:")
    print("\tinit - initialize the test blockchain using genesis file")
    print("\tcreateAccounts - create some test accounts")
    print("\tbuild - compile smart contract and create wrapper class")
    print("\trun - start the geth test node")
    print("\tcleandb - remove the geth database")
    print("\tclean - cleanup")


def main():

    if len(sys.argv) != 2:
        print("Please call with one argument:")
        print_help()
        return 
    
    argument = sys.argv[1]

    if argument == "run":
        run()
    elif argument == "build":
        build()
    elif argument == "init":
        init()
    elif argument == "createAccounts":
        create_accounts()
    elif argument == "cleandb":
        clean_geth_db()
    elif argument == "clean":
        clean()
    else:
        print("Unknown argument.")
        print_help()
        return
    
    print("Program finished.")


if __name__ == "__main__":
    main()