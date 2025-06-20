package org.dongguk.dambo.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.CustomError;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class MusicNFT extends Contract {
    public static final String BINARY = "608060405234801561000f575f5ffd5b506040518060400160405280600881526020017f4d757369634e46540000000000000000000000000000000000000000000000008152506040518060400160405280600381526020017f4d55530000000000000000000000000000000000000000000000000000000000815250815f908161008a91906102df565b50806001908161009a91906102df565b5050506103ae565b5f81519050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f600282049050600182168061011d57607f821691505b6020821081036101305761012f6100d9565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f600883026101927fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610157565b61019c8683610157565b95508019841693508086168417925050509392505050565b5f819050919050565b5f819050919050565b5f6101e06101db6101d6846101b4565b6101bd565b6101b4565b9050919050565b5f819050919050565b6101f9836101c6565b61020d610205826101e7565b848454610163565b825550505050565b5f5f905090565b610224610215565b61022f8184846101f0565b505050565b5b81811015610252576102475f8261021c565b600181019050610235565b5050565b601f8211156102975761026881610136565b61027184610148565b81016020851015610280578190505b61029461028c85610148565b830182610234565b50505b505050565b5f82821c905092915050565b5f6102b75f198460080261029c565b1980831691505092915050565b5f6102cf83836102a8565b9150826002028217905092915050565b6102e8826100a2565b67ffffffffffffffff811115610301576103006100ac565b5b61030b8254610106565b610316828285610256565b5f60209050601f831160018114610347575f8415610335578287015190505b61033f85826102c4565b8655506103a6565b601f19841661035586610136565b5f5b8281101561037c57848901518255600182019150602085019450602081019050610357565b868310156103995784890151610395601f8916826102a8565b8355505b6001600288020188555050505b505050505050565b6122e0806103bb5f395ff3fe608060405234801561000f575f5ffd5b50600436106100e8575f3560e01c806370a082311161008a578063b88d4fde11610064578063b88d4fde14610258578063c87b56dd14610274578063e985e9c5146102a4578063eacabe14146102d4576100e8565b806370a08231146101ee57806395d89b411461021e578063a22cb4651461023c576100e8565b8063095ea7b3116100c6578063095ea7b31461016a57806323b872dd1461018657806342842e0e146101a25780636352211e146101be576100e8565b806301ffc9a7146100ec57806306fdde031461011c578063081812fc1461013a575b5f5ffd5b610106600480360381019061010191906117b0565b610304565b60405161011391906117f5565b60405180910390f35b610124610364565b604051610131919061187e565b60405180910390f35b610154600480360381019061014f91906118d1565b6103f3565b604051610161919061193b565b60405180910390f35b610184600480360381019061017f919061197e565b61040e565b005b6101a0600480360381019061019b91906119bc565b610424565b005b6101bc60048036038101906101b791906119bc565b610523565b005b6101d860048036038101906101d391906118d1565b610542565b6040516101e5919061193b565b60405180910390f35b61020860048036038101906102039190611a0c565b610553565b6040516102159190611a46565b60405180910390f35b610226610609565b604051610233919061187e565b60405180910390f35b61025660048036038101906102519190611a89565b610699565b005b610272600480360381019061026d9190611bf3565b6106af565b005b61028e600480360381019061028991906118d1565b6106d4565b60405161029b919061187e565b60405180910390f35b6102be60048036038101906102b99190611c73565b6107df565b6040516102cb91906117f5565b60405180910390f35b6102ee60048036038101906102e99190611d4f565b61086d565b6040516102fb9190611a46565b60405180910390f35b5f634906490660e01b7bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916148061035d575061035c826108a9565b5b9050919050565b60605f805461037290611dd6565b80601f016020809104026020016040519081016040528092919081815260200182805461039e90611dd6565b80156103e95780601f106103c0576101008083540402835291602001916103e9565b820191905f5260205f20905b8154815290600101906020018083116103cc57829003601f168201915b5050505050905090565b5f6103fd8261098a565b5061040782610a10565b9050919050565b610420828261041b610a49565b610a50565b5050565b5f73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1603610494575f6040517f64a0ae9200000000000000000000000000000000000000000000000000000000815260040161048b919061193b565b60405180910390fd5b5f6104a783836104a2610a49565b610a62565b90508373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161461051d578382826040517f64283d7b00000000000000000000000000000000000000000000000000000000815260040161051493929190611e06565b60405180910390fd5b50505050565b61053d83838360405180602001604052805f8152506106af565b505050565b5f61054c8261098a565b9050919050565b5f5f73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16036105c4575f6040517f89c62b640000000000000000000000000000000000000000000000000000000081526004016105bb919061193b565b60405180910390fd5b60035f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f20549050919050565b60606001805461061890611dd6565b80601f016020809104026020016040519081016040528092919081815260200182805461064490611dd6565b801561068f5780601f106106665761010080835404028352916020019161068f565b820191905f5260205f20905b81548152906001019060200180831161067257829003601f168201915b5050505050905090565b6106ab6106a4610a49565b8383610c6d565b5050565b6106ba848484610424565b6106ce6106c5610a49565b85858585610dd6565b50505050565b60606106df8261098a565b505f60065f8481526020019081526020015f2080546106fd90611dd6565b80601f016020809104026020016040519081016040528092919081815260200182805461072990611dd6565b80156107745780601f1061074b57610100808354040283529160200191610774565b820191905f5260205f20905b81548152906001019060200180831161075757829003601f168201915b505050505090505f610784610f82565b90505f8151036107985781925050506107da565b5f825111156107cc5780826040516020016107b4929190611e75565b604051602081830303815290604052925050506107da565b6107d584610f98565b925050505b919050565b5f60055f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16905092915050565b5f60075f81548092919061088090611ec5565b91905055505f60075490506108958482610ffe565b61089f81846110f1565b8091505092915050565b5f7f80ac58cd000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916148061097357507f5b5e139f000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916145b8061098357506109828261114b565b5b9050919050565b5f5f610995836111b4565b90505f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610a0757826040517f7e2732890000000000000000000000000000000000000000000000000000000081526004016109fe9190611a46565b60405180910390fd5b80915050919050565b5f60045f8381526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b5f33905090565b610a5d83838360016111ed565b505050565b5f5f610a6d846111b4565b90505f73ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1614610aae57610aad8184866113ac565b5b5f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610b3957610aed5f855f5f6111ed565b600160035f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f82825403925050819055505b5f73ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff1614610bb857600160035f8773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f82825401925050819055505b8460025f8681526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550838573ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef60405160405180910390a4809150509392505050565b5f73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1603610cdd57816040517f5b08ba18000000000000000000000000000000000000000000000000000000008152600401610cd4919061193b565b60405180910390fd5b8060055f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c3183604051610dc991906117f5565b60405180910390a3505050565b5f8373ffffffffffffffffffffffffffffffffffffffff163b1115610f7b578273ffffffffffffffffffffffffffffffffffffffff1663150b7a02868685856040518563ffffffff1660e01b8152600401610e349493929190611f5e565b6020604051808303815f875af1925050508015610e6f57506040513d601f19601f82011682018060405250810190610e6c9190611fbc565b60015b610ef0573d805f8114610e9d576040519150601f19603f3d011682016040523d82523d5f602084013e610ea2565b606091505b505f815103610ee857836040517f64a0ae92000000000000000000000000000000000000000000000000000000008152600401610edf919061193b565b60405180910390fd5b805181602001fd5b63150b7a0260e01b7bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916817bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614610f7957836040517f64a0ae92000000000000000000000000000000000000000000000000000000008152600401610f70919061193b565b60405180910390fd5b505b5050505050565b606060405180602001604052805f815250905090565b6060610fa38261098a565b505f610fad610f82565b90505f815111610fcb5760405180602001604052805f815250610ff6565b80610fd58461146f565b604051602001610fe6929190611e75565b6040516020818303038152906040525b915050919050565b5f73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff160361106e575f6040517f64a0ae92000000000000000000000000000000000000000000000000000000008152600401611065919061193b565b60405180910390fd5b5f61107a83835f610a62565b90505f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16146110ec575f6040517f73c6ac6e0000000000000000000000000000000000000000000000000000000081526004016110e3919061193b565b60405180910390fd5b505050565b8060065f8481526020019081526020015f20908161110f9190612187565b507ff8e1a15aba9398e019f0b49df1a4fde98ee17ae345cb5f6b5e2c27f5033e8ce78260405161113f9190611a46565b60405180910390a15050565b5f7f01ffc9a7000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916149050919050565b5f60025f8381526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b808061122557505f73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614155b15611357575f6112348461098a565b90505f73ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff161415801561129e57508273ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614155b80156112b157506112af81846107df565b155b156112f357826040517fa9fbf51f0000000000000000000000000000000000000000000000000000000081526004016112ea919061193b565b60405180910390fd5b811561135557838573ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92560405160405180910390a45b505b8360045f8581526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050505050565b6113b7838383611539565b61146a575f73ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff160361142b57806040517f7e2732890000000000000000000000000000000000000000000000000000000081526004016114229190611a46565b60405180910390fd5b81816040517f177e802f000000000000000000000000000000000000000000000000000000008152600401611461929190612256565b60405180910390fd5b505050565b60605f600161147d846115f9565b0190505f8167ffffffffffffffff81111561149b5761149a611acf565b5b6040519080825280601f01601f1916602001820160405280156114cd5781602001600182028036833780820191505090505b5090505f82602001820190505b60011561152e578080600190039150507f3031323334353637383961626364656600000000000000000000000000000000600a86061a8153600a85816115235761152261227d565b5b0494505f85036114da575b819350505050919050565b5f5f73ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16141580156115f057508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff1614806115b157506115b084846107df565b5b806115ef57508273ffffffffffffffffffffffffffffffffffffffff166115d783610a10565b73ffffffffffffffffffffffffffffffffffffffff16145b5b90509392505050565b5f5f5f90507a184f03e93ff9f4daa797ed6e38ed64bf6a1f0100000000000000008310611655577a184f03e93ff9f4daa797ed6e38ed64bf6a1f010000000000000000838161164b5761164a61227d565b5b0492506040810190505b6d04ee2d6d415b85acef81000000008310611692576d04ee2d6d415b85acef810000000083816116885761168761227d565b5b0492506020810190505b662386f26fc1000083106116c157662386f26fc1000083816116b7576116b661227d565b5b0492506010810190505b6305f5e10083106116ea576305f5e10083816116e0576116df61227d565b5b0492506008810190505b612710831061170f5761271083816117055761170461227d565b5b0492506004810190505b6064831061173257606483816117285761172761227d565b5b0492506002810190505b600a8310611741576001810190505b80915050919050565b5f604051905090565b5f5ffd5b5f5ffd5b5f7fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b61178f8161175b565b8114611799575f5ffd5b50565b5f813590506117aa81611786565b92915050565b5f602082840312156117c5576117c4611753565b5b5f6117d28482850161179c565b91505092915050565b5f8115159050919050565b6117ef816117db565b82525050565b5f6020820190506118085f8301846117e6565b92915050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f601f19601f8301169050919050565b5f6118508261180e565b61185a8185611818565b935061186a818560208601611828565b61187381611836565b840191505092915050565b5f6020820190508181035f8301526118968184611846565b905092915050565b5f819050919050565b6118b08161189e565b81146118ba575f5ffd5b50565b5f813590506118cb816118a7565b92915050565b5f602082840312156118e6576118e5611753565b5b5f6118f3848285016118bd565b91505092915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f611925826118fc565b9050919050565b6119358161191b565b82525050565b5f60208201905061194e5f83018461192c565b92915050565b61195d8161191b565b8114611967575f5ffd5b50565b5f8135905061197881611954565b92915050565b5f5f6040838503121561199457611993611753565b5b5f6119a18582860161196a565b92505060206119b2858286016118bd565b9150509250929050565b5f5f5f606084860312156119d3576119d2611753565b5b5f6119e08682870161196a565b93505060206119f18682870161196a565b9250506040611a02868287016118bd565b9150509250925092565b5f60208284031215611a2157611a20611753565b5b5f611a2e8482850161196a565b91505092915050565b611a408161189e565b82525050565b5f602082019050611a595f830184611a37565b92915050565b611a68816117db565b8114611a72575f5ffd5b50565b5f81359050611a8381611a5f565b92915050565b5f5f60408385031215611a9f57611a9e611753565b5b5f611aac8582860161196a565b9250506020611abd85828601611a75565b9150509250929050565b5f5ffd5b5f5ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b611b0582611836565b810181811067ffffffffffffffff82111715611b2457611b23611acf565b5b80604052505050565b5f611b3661174a565b9050611b428282611afc565b919050565b5f67ffffffffffffffff821115611b6157611b60611acf565b5b611b6a82611836565b9050602081019050919050565b828183375f83830152505050565b5f611b97611b9284611b47565b611b2d565b905082815260208101848484011115611bb357611bb2611acb565b5b611bbe848285611b77565b509392505050565b5f82601f830112611bda57611bd9611ac7565b5b8135611bea848260208601611b85565b91505092915050565b5f5f5f5f60808587031215611c0b57611c0a611753565b5b5f611c188782880161196a565b9450506020611c298782880161196a565b9350506040611c3a878288016118bd565b925050606085013567ffffffffffffffff811115611c5b57611c5a611757565b5b611c6787828801611bc6565b91505092959194509250565b5f5f60408385031215611c8957611c88611753565b5b5f611c968582860161196a565b9250506020611ca78582860161196a565b9150509250929050565b5f67ffffffffffffffff821115611ccb57611cca611acf565b5b611cd482611836565b9050602081019050919050565b5f611cf3611cee84611cb1565b611b2d565b905082815260208101848484011115611d0f57611d0e611acb565b5b611d1a848285611b77565b509392505050565b5f82601f830112611d3657611d35611ac7565b5b8135611d46848260208601611ce1565b91505092915050565b5f5f60408385031215611d6557611d64611753565b5b5f611d728582860161196a565b925050602083013567ffffffffffffffff811115611d9357611d92611757565b5b611d9f85828601611d22565b9150509250929050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f6002820490506001821680611ded57607f821691505b602082108103611e0057611dff611da9565b5b50919050565b5f606082019050611e195f83018661192c565b611e266020830185611a37565b611e33604083018461192c565b949350505050565b5f81905092915050565b5f611e4f8261180e565b611e598185611e3b565b9350611e69818560208601611828565b80840191505092915050565b5f611e808285611e45565b9150611e8c8284611e45565b91508190509392505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f611ecf8261189e565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203611f0157611f00611e98565b5b600182019050919050565b5f81519050919050565b5f82825260208201905092915050565b5f611f3082611f0c565b611f3a8185611f16565b9350611f4a818560208601611828565b611f5381611836565b840191505092915050565b5f608082019050611f715f83018761192c565b611f7e602083018661192c565b611f8b6040830185611a37565b8181036060830152611f9d8184611f26565b905095945050505050565b5f81519050611fb681611786565b92915050565b5f60208284031215611fd157611fd0611753565b5b5f611fde84828501611fa8565b91505092915050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f600883026120437fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82612008565b61204d8683612008565b95508019841693508086168417925050509392505050565b5f819050919050565b5f61208861208361207e8461189e565b612065565b61189e565b9050919050565b5f819050919050565b6120a18361206e565b6120b56120ad8261208f565b848454612014565b825550505050565b5f5f905090565b6120cc6120bd565b6120d7818484612098565b505050565b5b818110156120fa576120ef5f826120c4565b6001810190506120dd565b5050565b601f82111561213f5761211081611fe7565b61211984611ff9565b81016020851015612128578190505b61213c61213485611ff9565b8301826120dc565b50505b505050565b5f82821c905092915050565b5f61215f5f1984600802612144565b1980831691505092915050565b5f6121778383612150565b9150826002028217905092915050565b6121908261180e565b67ffffffffffffffff8111156121a9576121a8611acf565b5b6121b38254611dd6565b6121be8282856120fe565b5f60209050601f8311600181146121ef575f84156121dd578287015190505b6121e7858261216c565b86555061224e565b601f1984166121fd86611fe7565b5f5b82811015612224578489015182556001820191506020850194506020810190506121ff565b86831015612241578489015161223d601f891682612150565b8355505b6001600288020188555050505b505050505050565b5f6040820190506122695f83018561192c565b6122766020830184611a37565b9392505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601260045260245ffdfea2646970667358221220d287061fcd944e1464b5ba2e3f8d37aae417ace3a42bd31cf9c0d01a8dd51e7264736f6c634300081e0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_MINTNFT = "mintNFT";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_safeTransferFrom = "safeTransferFrom";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOKENURI = "tokenURI";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final CustomError ERC721INCORRECTOWNER_ERROR = new CustomError("ERC721IncorrectOwner",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC721INSUFFICIENTAPPROVAL_ERROR = new CustomError("ERC721InsufficientApproval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final CustomError ERC721INVALIDAPPROVER_ERROR = new CustomError("ERC721InvalidApprover",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC721INVALIDOPERATOR_ERROR = new CustomError("ERC721InvalidOperator",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC721INVALIDOWNER_ERROR = new CustomError("ERC721InvalidOwner",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC721INVALIDRECEIVER_ERROR = new CustomError("ERC721InvalidReceiver",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC721INVALIDSENDER_ERROR = new CustomError("ERC721InvalidSender",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC721NONEXISTENTTOKEN_ERROR = new CustomError("ERC721NonexistentToken",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event BATCHMETADATAUPDATE_EVENT = new Event("BatchMetadataUpdate",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event METADATAUPDATE_EVENT = new Event("MetadataUpdate",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    @Deprecated
    protected MusicNFT(String contractAddress, Web3j web3j, Credentials credentials,
                       BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MusicNFT(String contractAddress, Web3j web3j, Credentials credentials,
                       ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MusicNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                       BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MusicNFT(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                       ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
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

    public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
        ApprovalEventResponse typedResponse = new ApprovalEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock,
                                                                 DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<ApprovalForAllEventResponse> getApprovalForAllEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
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

    public static ApprovalForAllEventResponse getApprovalForAllEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, log);
        ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalForAllEventFromLog(log));
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public static List<BatchMetadataUpdateEventResponse> getBatchMetadataUpdateEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BATCHMETADATAUPDATE_EVENT, transactionReceipt);
        ArrayList<BatchMetadataUpdateEventResponse> responses = new ArrayList<BatchMetadataUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BatchMetadataUpdateEventResponse typedResponse = new BatchMetadataUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._fromTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._toTokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BatchMetadataUpdateEventResponse getBatchMetadataUpdateEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BATCHMETADATAUPDATE_EVENT, log);
        BatchMetadataUpdateEventResponse typedResponse = new BatchMetadataUpdateEventResponse();
        typedResponse.log = log;
        typedResponse._fromTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse._toTokenId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<BatchMetadataUpdateEventResponse> batchMetadataUpdateEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBatchMetadataUpdateEventFromLog(log));
    }

    public Flowable<BatchMetadataUpdateEventResponse> batchMetadataUpdateEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BATCHMETADATAUPDATE_EVENT));
        return batchMetadataUpdateEventFlowable(filter);
    }

    public static List<MetadataUpdateEventResponse> getMetadataUpdateEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(METADATAUPDATE_EVENT, transactionReceipt);
        ArrayList<MetadataUpdateEventResponse> responses = new ArrayList<MetadataUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MetadataUpdateEventResponse typedResponse = new MetadataUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MetadataUpdateEventResponse getMetadataUpdateEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(METADATAUPDATE_EVENT, log);
        MetadataUpdateEventResponse typedResponse = new MetadataUpdateEventResponse();
        typedResponse.log = log;
        typedResponse._tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<MetadataUpdateEventResponse> metadataUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMetadataUpdateEventFromLog(log));
    }

    public Flowable<MetadataUpdateEventResponse> metadataUpdateEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(METADATAUPDATE_EVENT));
        return metadataUpdateEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
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

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock,
                                                                 DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to),
                        new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String owner) {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getApproved(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETAPPROVED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedForAll(String owner, String operator) {
        final Function function = new Function(FUNC_ISAPPROVEDFORALL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner),
                        new org.web3j.abi.datatypes.Address(160, operator)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mintNFT(String recipient, String tokenURI) {
        final Function function = new Function(
                FUNC_MINTNFT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, recipient),
                        new org.web3j.abi.datatypes.Utf8String(tokenURI)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ownerOf(BigInteger tokenId) {
        final Function function = new Function(FUNC_OWNEROF,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to,
                                                                   BigInteger tokenId) {
        final Function function = new Function(
                FUNC_safeTransferFrom,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from),
                        new org.web3j.abi.datatypes.Address(160, to),
                        new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to,
                                                                   BigInteger tokenId, byte[] data) {
        final Function function = new Function(
                FUNC_safeTransferFrom,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from),
                        new org.web3j.abi.datatypes.Address(160, to),
                        new org.web3j.abi.datatypes.generated.Uint256(tokenId),
                        new org.web3j.abi.datatypes.DynamicBytes(data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator,
                                                                    Boolean approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator),
                        new org.web3j.abi.datatypes.Bool(approved)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> tokenURI(BigInteger tokenId) {
        final Function function = new Function(FUNC_TOKENURI,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to,
                                                               BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from),
                        new org.web3j.abi.datatypes.Address(160, to),
                        new org.web3j.abi.datatypes.generated.Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static MusicNFT load(String contractAddress, Web3j web3j, Credentials credentials,
                                BigInteger gasPrice, BigInteger gasLimit) {
        return new MusicNFT(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MusicNFT load(String contractAddress, Web3j web3j,
                                TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MusicNFT(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MusicNFT load(String contractAddress, Web3j web3j, Credentials credentials,
                                ContractGasProvider contractGasProvider) {
        return new MusicNFT(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MusicNFT load(String contractAddress, Web3j web3j,
                                TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MusicNFT(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MusicNFT> deploy(Web3j web3j, Credentials credentials,
                                              ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MusicNFT.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<MusicNFT> deploy(Web3j web3j, TransactionManager transactionManager,
                                              ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MusicNFT.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<MusicNFT> deploy(Web3j web3j, Credentials credentials,
                                              BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MusicNFT.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<MusicNFT> deploy(Web3j web3j, TransactionManager transactionManager,
                                              BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MusicNFT.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse extends BaseEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
    }

    public static class BatchMetadataUpdateEventResponse extends BaseEventResponse {
        public BigInteger _fromTokenId;

        public BigInteger _toTokenId;
    }

    public static class MetadataUpdateEventResponse extends BaseEventResponse {
        public BigInteger _tokenId;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }
}