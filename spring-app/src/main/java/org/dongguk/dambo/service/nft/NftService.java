package org.dongguk.dambo.service.nft;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.entity.MusicCopyright;
import org.dongguk.dambo.domain.entity.Nft;
import org.dongguk.dambo.domain.entity.User;
import org.dongguk.dambo.domain.exception.user.UserErrorCode;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.dto.nft.NftCreateRequest;
import org.dongguk.dambo.infrastructure.api.GcsClient;
import org.dongguk.dambo.infrastructure.api.MusicValuationApiClient;
import org.dongguk.dambo.repository.contract.ContractRepository;
import org.dongguk.dambo.repository.musiccopyright.MusicCopyrightRepository;
import org.dongguk.dambo.repository.nft.NftRepository;
import org.dongguk.dambo.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class NftService {
    private final MusicValuationApiClient musicValuationApiClient;
    private final GcsClient gcsClient;
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

        // 1. 외부 APi 사용하여 MusicCopyright ethPrice 구해준다.
        //

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
                BigDecimal.valueOf(
                        ThreadLocalRandom.current().nextDouble(50.0, 200.0)
                ).setScale(4, RoundingMode.HALF_UP),
                user
        );
        musicCopyrightRepository.save(musicCopyright);

        // 3. registrationDoc & imageUrl 을 GCS 업로드 후 업데이트
        musicCopyright.updateImageUrl(gcsClient.uploadImage(user.getId(), musicCopyright.getId(), image.getOriginalFilename(), image));
        musicCopyright.updateRegistrationDoc(gcsClient.uploadFile(user.getId(), musicCopyright.getId(), registrationDoc.getOriginalFilename(), registrationDoc));

        // 4. 토큰 등록 로직 수행
        //

        // 5. 토큰 등록 이후 등록된 정보를 기반으로 Nft create
        Nft nft = Nft.create(
                user.getWalletAddr(),
                "",
                1L,
                "",
                "",
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
