package org.dongguk.dambo.service.nft;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.entity.MusicCopyright;
import org.dongguk.dambo.domain.entity.Nft;
import org.dongguk.dambo.domain.entity.User;
import org.dongguk.dambo.domain.exception.user.UserErrorCode;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.dto.MetadataMintRequest;
import org.dongguk.dambo.dto.MintResponse;
import org.dongguk.dambo.dto.nft.NftCreateRequest;
import org.dongguk.dambo.infrastructure.api.GcsClient;
import org.dongguk.dambo.infrastructure.api.MusicValuationApiClient;
import org.dongguk.dambo.repository.contract.ContractRepository;
import org.dongguk.dambo.repository.musiccopyright.MusicCopyrightRepository;
import org.dongguk.dambo.repository.nft.NftRepository;
import org.dongguk.dambo.repository.user.UserRepository;
import org.dongguk.dambo.service.MusicNFTService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class NftService {
    private final MusicValuationApiClient musicValuationApiClient;
    private final GcsClient gcsClient;
    private final MusicNFTService musicNFTService;
    private final UserRepository userRepository;
    private final MusicCopyrightRepository musicCopyrightRepository;
    private final ContractRepository contractRepository;
    private final NftRepository nftRepository;

    @Transactional
    public Void createNft(
            Long userId,
            MultipartFile registrationDoc,
            MultipartFile image,
            MultipartFile audio,
            NftCreateRequest nftCreateRequest
    ) {
        log.info("[NFT 생성] 요청 시작 - userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("[NFT 생성] 사용자 조회 실패 - userId: {}", userId);
                    return CustomException.type(UserErrorCode.NOT_FOUND_USER);
                });
        log.info("[NFT 생성] 사용자 조회 완료 - user: {}", user.getName());

        // 1. 받은 ethPrice 값에서 적당히 파싱
        BigDecimal ethPrice = new BigDecimal(nftCreateRequest.ethPrice().replace("ETH", "").trim());
        log.info("[NFT 생성] ETH 가격 파싱 완료 - ethPrice: {}", ethPrice);

        // 2. 임시적으로 MusicCopyright 만들어준다.
        MusicCopyright musicCopyright = MusicCopyright.create(
                nftCreateRequest.title(),
                nftCreateRequest.singers().getFirst(),
                nftCreateRequest.composers().getFirst(),
                nftCreateRequest.lyricists().getFirst(),
                nftCreateRequest.streamingUrls().getFirst(),
                nftCreateRequest.isRegistered(),
                "",
                "",
                "",
                ethPrice,
                user
        );
        musicCopyrightRepository.save(musicCopyright);
        log.info("[NFT 생성] MusicCopyright 생성 및 저장 완료 - id: {}", musicCopyright.getId());

        // 3-1. 이미지 업로드는 필수
        String imageUrl = gcsClient.uploadImage(
                user.getId(),
                musicCopyright.getId(),
                image.getOriginalFilename(),
                image
        );
        musicCopyright.updateImageUrl(imageUrl);
        log.info("[NFT 생성] 이미지 업로드 완료 - url: {}", imageUrl);

        // 3-2. registrationDoc 업로드는 선택
        if (registrationDoc != null && !registrationDoc.isEmpty()) {
            String docUrl = gcsClient.uploadFile(
                    user.getId(),
                    musicCopyright.getId(),
                    registrationDoc.getOriginalFilename(),
                    registrationDoc
            );
            musicCopyright.updateRegistrationDoc(docUrl);
            log.info("[NFT 생성] 등록 문서 업로드 완료 - url: {}", docUrl);
        } else {
            log.info("[NFT 생성] 등록 문서 없음 - 스킵");
        }

        // 3-3. 오비오 업로드
        String audioUrl = gcsClient.uploadAudio(
                user.getId(),
                musicCopyright.getId(),
                audio.getOriginalFilename(),
                audio
        );
        musicCopyright.updateAudioUrl(audioUrl);
        log.info("[NFT 생성] 오디오 업로드 완료 - url: {}", audioUrl);

        // 4. 토큰 등록 로직 수행
        MetadataMintRequest request = MetadataMintRequest.of(
                user.getWalletAddr(),
                musicCopyright.getEthPrice(),
                musicCopyright.getTitle(),
                musicCopyright.getSinger(),
                musicCopyright.getComposer(),
                musicCopyright.getLyricist(),
                musicCopyright.getStreamingUrl(),
                musicCopyright.getImageUrl()
        );
        log.info("[NFT 생성] 메타데이터 생성 완료 - title: {}", request.name());

        String ipfsurl = musicNFTService.uploadMetadataToIPFS(request);
        log.info("[NFT 생성] 메타데이터 IPFS 업로드 완료 - ipfsUrl: {}", ipfsurl);

        MintResponse mintResponse = musicNFTService.mintNFT(user.getWalletAddr(), ipfsurl);
        log.info("[NFT 생성] NFT 민팅 완료 - txHash: {}", mintResponse.txHash());

        // 5. 토큰 등록 이후 등록된 정보를 기반으로 Nft create
        Nft nft = Nft.create(
                nftCreateRequest.nftName(),
                user.getWalletAddr(),
                mintResponse.tokenId(),
                ipfsurl,
                mintResponse.txHash(),
                musicCopyright
        );
        nftRepository.save(nft);
        log.info("[NFT 생성] NFT 저장 완료 - id: {}", nft.getId());

        // 6. 초기 상태의 계약 레코드를 생성해준다.
        Contract contract = Contract.create(
                null,
                null,
                null,
                EContractStatus.REGISTERED,
                null,
                null,
                musicCopyright
        );
        contractRepository.save(contract);
        log.info("[NFT 생성] 계약 레코드 생성 완료 - contractId: {}", contract.getId());

        log.info("[NFT 생성] 전체 프로세스 완료 - userId: {}, nftId: {}", userId, nft.getId());
        return null;
    }

}
