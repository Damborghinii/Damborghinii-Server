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
            NftCreateRequest nftCreateRequest
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.type(UserErrorCode.NOT_FOUND_USER));

        // 1. 받은 ethPrice 값에서 적당히 파싱
        BigDecimal ethPrice = new BigDecimal(nftCreateRequest.ethPrice().replace("ETH", "").trim());

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
                ethPrice,
                user
        );
        musicCopyrightRepository.save(musicCopyright);

        // 3-1. 이미지 업로드는 필수
        String imageUrl = gcsClient.uploadImage(
                user.getId(),
                musicCopyright.getId(),
                image.getOriginalFilename(),
                image
        );
        musicCopyright.updateImageUrl(imageUrl);

        // 3-2. registrationDoc 업로드는 선택
        if (registrationDoc != null && !registrationDoc.isEmpty()) {
            String docUrl = gcsClient.uploadFile(
                    user.getId(),
                    musicCopyright.getId(),
                    registrationDoc.getOriginalFilename(),
                    registrationDoc
            );
            musicCopyright.updateRegistrationDoc(docUrl);
        }

//        // 4. 토큰 등록 로직 수행
//        MetadataMintRequest request = MetadataMintRequest.of(
//                user.getWalletAddr(),
//                musicCopyright.getEthPrice(),
//                musicCopyright.getTitle() ,
//                musicCopyright.getSinger(),
//                musicCopyright.getComposer(),
//                musicCopyright.getLyricist(),
//                musicCopyright.getStreamingUrl(),
//                musicCopyright.getImageUrl()
//        );
//        String ipfsurl = musicNFTService.uploadMetadataToIPFS(request);
//        MintResponse mintResponse = musicNFTService.mintNFT(user.getWalletAddr(), ipfsurl);
//
        // 5. 토큰 등록 이후 등록된 정보를 기반으로 Nft create
        Nft nft = Nft.create(
                nftCreateRequest.nftName(),
                user.getWalletAddr(),
                0L,
                "ipfsurl",
                "mintResponse.txHash()",
                musicCopyright
        );
        nftRepository.save(nft);

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

        return null;
    }

}
