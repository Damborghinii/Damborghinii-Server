package org.dongguk.dambo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.dongguk.dambo.contract.MusicNFT;
import org.dongguk.dambo.dto.MetadataMintRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MusicNFTService {

    @Value("${web3.infura.url}")
    private String infuraUrl;

    @Value("${web3.wallet.private-key}")
    private String privateKey;

    @Value("${contract.musicnft.address}")
    private String contractAddress;

    @Value("${pinata.api.url}")
    private String pinataUrl;

    @Value("${pinata.api.key}")
    private String pinataApiKey;

    @Value("${pinata.secret.key}")
    private String pinataSecretKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * NFT 민팅 수행
     */
    public String mintNFT(String recipient, String tokenURI) {
        try {
            Web3j web3j = Web3j.build(new HttpService(infuraUrl));
            Credentials credentials = Credentials.create(privateKey);

            String cleanAddress = contractAddress.trim();
            if (!cleanAddress.startsWith("0x") || cleanAddress.length() != 42) {
                throw new IllegalArgumentException("Invalid contract address: " + cleanAddress);
            }

            MusicNFT contract = MusicNFT.load(
                    contractAddress.trim(), web3j, credentials, new DefaultGasProvider()
            );

            var transaction = contract.mintNFT(recipient, tokenURI).send();
            return transaction.getTransactionHash();

        } catch (Exception e) {
            throw new RuntimeException("Minting failed: " + e.getMessage(), e);
        }
    }

    /**
     * JSON metadata를 Pinata에 업로드하고 IPFS Gateway URL을 반환
     */
    public String uploadMetadataToIPFS(MetadataMintRequest request) {
        try {
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("name", request.getName());
            metadata.put("description", request.getDescription());
            metadata.put("image", request.getImage());

            Map<String, Object> payload = new HashMap<>();
            payload.put("pinataContent", metadata);

            String jsonPayload = objectMapper.writeValueAsString(payload);

            // 요청 전송
            RequestBody body = RequestBody.create(jsonPayload, MediaType.parse("application/json"));
            Request httpRequest = new Request.Builder()
                    .url(pinataUrl) // ex) https://api.pinata.cloud/pinning/pinJSONToIPFS
                    .addHeader("Content-Type", "application/json")
                    .addHeader("pinata_api_key", pinataApiKey)
                    .addHeader("pinata_secret_api_key", pinataSecretKey)
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(httpRequest).execute();

            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "empty";
                throw new RuntimeException("Pinata upload failed: " + errorBody);
            }

            String responseBody = response.body().string();
            Map<?, ?> responseJson = objectMapper.readValue(responseBody, Map.class);
            String ipfsHash = (String) responseJson.get("IpfsHash");

            return "https://gateway.pinata.cloud/ipfs/" + ipfsHash;

        } catch (IOException e) {
            throw new RuntimeException("IPFS upload failed: " + e.getMessage(), e);
        }
    }
}