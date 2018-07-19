import os
import sys
import shutil
import subprocess
import json


PATH_SMART_CONTRACT_SRC = "./src/ethereum/"
PATH_SMART_CONTRACT_OUT = "./contract/"
PATH_GETH_DATADIR = "./test/ethereum/"
PATH_ACCOUNT_PASSWD = "./test/passwd.txt"
PATH_GENESIS_FILE = "./test/genesis.json"
PATH_AQUALIFE_JAR = "/jar"
PATH_TO_JAVA_JDK = ''  # set here the path to the java jdk used by gradle, if you don't want to use the default path

ACCOUNT_NUM = 5
DEFAULT_ETHER = "100000000000000000000"  # in Wei


def create_account():
    result_str = subprocess.run("geth account new --password " + PATH_ACCOUNT_PASSWD + " --datadir " + PATH_GETH_DATADIR, shell=True, stdout=subprocess.PIPE).stdout.decode('ascii')

    # extract the address from the geth output. Output looks like: "Address: {...}"
    start = result_str.find('{') + 1
    end = result_str.find('}', start)
    print("Account created: " + result_str[start:end])

    return result_str[start:end]


def add_accounts_to_genesis(addresses):
    print("Adding accounts to genesis file...")

    with open(PATH_GENESIS_FILE, "r+") as json_file:
        data = json.load(json_file)
        data["alloc"].clear()

        for address in addresses:
            data["alloc"][address] = {"balance": DEFAULT_ETHER}

        json_file.seek(0)
        json.dump(data, json_file, indent=4)
        json_file.truncate()


def init():
    print("Create " + str(ACCOUNT_NUM) + " accounts...")
    print("Password for each account specified in '" + PATH_ACCOUNT_PASSWD + "'")

    addresses = []
    for i in range(ACCOUNT_NUM):
        address = create_account()
        addresses.append(address)

    add_accounts_to_genesis(addresses)

    print("Initialize test blockchain...")
    os.system("geth --networkid 55 init " + PATH_GENESIS_FILE + " --datadir " + PATH_GETH_DATADIR)


def compile_contracts():
    print("Compile smart contracts...")

    if not os.path.exists(PATH_SMART_CONTRACT_OUT):
        print("Create contract output folder '" + PATH_SMART_CONTRACT_OUT + "'...")
        os.makedirs(PATH_SMART_CONTRACT_OUT)

    files = os.listdir(PATH_SMART_CONTRACT_SRC)
    for file in files:
        path_contract_src = PATH_SMART_CONTRACT_SRC + file
        os.system("solc " + path_contract_src + " --bin --abi --optimize --overwrite -o " + PATH_SMART_CONTRACT_OUT)


def generate_web3j_wrapper():
    print("Generate web3j wrapper classes...")

    files = os.listdir(PATH_SMART_CONTRACT_OUT)
    for i in range(0, len(files), 2):
        path_contract_abi = PATH_SMART_CONTRACT_OUT + files[i]
        path_contract_bin = PATH_SMART_CONTRACT_OUT + files[i + 1]
        os.system("web3j solidity generate " + path_contract_bin + " " + path_contract_abi + " -o ./src/ -p aqualife.client.contracts")


def compile_project():
    print("Compiling java project...")

    use_java_jdk = ""
    if len(PATH_TO_JAVA_JDK) != 0:
        use_java_jdk = "-Dorg.gradle.java.home=" + PATH_TO_JAVA_JDK

    os.system("gradle build " + use_java_jdk)


def build():
    compile_contracts()
    generate_web3j_wrapper()
    compile_project()


def deploy_contract():
    os.system('java -Duser.dir="' + os.getcwd() + PATH_AQUALIFE_JAR + '" -cp AqualifeDApp.jar aqualife.deploy.DeploySmartContract')


def clean_geth_db():
    os.system("geth removedb --datadir " + PATH_GETH_DATADIR)


def clean():
    clean_geth_db()
    if os.path.exists(PATH_GETH_DATADIR):
        shutil.rmtree(PATH_GETH_DATADIR)
    if os.path.exists(PATH_SMART_CONTRACT_OUT):
        shutil.rmtree(PATH_SMART_CONTRACT_OUT)
    os.system("gradle clean")


def run():
    os.system('geth --networkid 55 --nodiscover --maxpeers 0 --rpc --rpccorsdomain "https://remix.ethereum.org" --datadir ' + PATH_GETH_DATADIR + ' --preload geth_config.js console')


def print_help():
    print("Possible arguments:")
    print("\tinit - create several test accounts and initialize the test blockchain using the genesis file")
    print("\tbuild - compile smart contracts, create wrapper classes and build project")
    print("\tdeploy - deploy smart contracts")
    print("\trun - start a geth test node in a private network")
    print("\tcleandb - remove the geth database")
    print("\tclean - cleanup")


def main():

    if len(sys.argv) != 2:
        print("Please call with one argument:")
        print_help()
        return

    argument = sys.argv[1]

    if argument == "init":
        init()
    elif argument == "build":
        build()
    elif argument == "deploy":
        deploy_contract()
    elif argument == "run":
        run()
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