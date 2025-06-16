package org.dongguk.dambo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.dongguk.dambo.contract.MusicNFT;
import org.dongguk.dambo.dto.MetadataMintRequest;
import org.dongguk.dambo.dto.MintResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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
    public MintResponse mintNFT(String recipient, String tokenURI) {
        try {
            log.info("[NFT Minting] 시작 - recipient: {}, tokenURI: {}", recipient, tokenURI);

            Web3j web3j = Web3j.build(new HttpService(infuraUrl));
            log.info("[NFT Minting] Web3j 연결 완료 - URL: {}", infuraUrl);

            Credentials credentials = Credentials.create(privateKey);
            log.info("[NFT Minting] 지갑 Credentials 생성 완료");

            String cleanAddress = contractAddress.trim();
            if (!cleanAddress.startsWith("0x") || cleanAddress.length() != 42) {
                log.error("[NFT Minting] Invalid contract address: {}", cleanAddress);
                throw new IllegalArgumentException("Invalid contract address: " + cleanAddress);
            }

            MusicNFT contract = MusicNFT.load(
                    cleanAddress, web3j, credentials, new DefaultGasProvider()
            );
            log.info("[NFT Minting] 스마트 컨트랙트 로드 완료 - address: {}", cleanAddress);

            TransactionReceipt receipt = contract.mintNFT(recipient, tokenURI).send();
            log.info("[NFT Minting] 트랜잭션 성공 - hash: {}", receipt.getTransactionHash());

            List<MusicNFT.TransferEventResponse> events = MusicNFT.getTransferEvents(receipt);
            if (events.isEmpty()) {
                log.warn("[NFT Minting] Transfer 이벤트가 존재하지 않음");
                throw new RuntimeException("No Transfer event found.");
            }

            BigInteger tokenId = events.getFirst().tokenId;
            log.info("[NFT Minting] 민팅된 토큰 ID: {}", tokenId);

            return MintResponse.of(receipt.getTransactionHash(), tokenId.longValue());

        } catch (Exception e) {
            log.error("[NFT Minting] 실패 - error: {}", e.getMessage(), e);
            throw new RuntimeException("Minting failed: " + e.getMessage(), e);
        }
    }

    /**
     * JSON metadata를 Pinata에 업로드하고 IPFS Gateway URL을 반환
     */
    public String uploadMetadataToIPFS(MetadataMintRequest request) {
        try {
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("ethPrice", request.ethPrice());
            metadata.put("name", request.name());
            metadata.put("singer", request.singer());
            metadata.put("composer", request.composer());
            metadata.put("lyricist", request.lyricist());
            metadata.put("streamingUrl", request.streamingUrl());
            metadata.put("image", request.image());

            Map<String, Object> payload = new HashMap<>();
            payload.put("pinataContent", metadata);

            Map<String, Object> pinataMetadata = new HashMap<>();
            pinataMetadata.put("name", request.name());
            payload.put("pinataMetadata", pinataMetadata);

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