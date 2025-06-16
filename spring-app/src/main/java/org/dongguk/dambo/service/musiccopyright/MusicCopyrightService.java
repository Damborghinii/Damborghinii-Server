package org.dongguk.dambo.service.musiccopyright;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.constant.LoanConstants;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.entity.MusicCopyright;
import org.dongguk.dambo.domain.entity.User;
import org.dongguk.dambo.domain.exception.contract.ContractErrorCode;
import org.dongguk.dambo.domain.exception.musiccopyright.MusicCopyrightErrorCode;
import org.dongguk.dambo.domain.exception.user.UserErrorCode;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.dto.musiccopyright.request.EvaluateCopyrightValueRequest;
import org.dongguk.dambo.dto.musiccopyright.response.CopyrightDetailResponse;
import org.dongguk.dambo.dto.musiccopyright.response.EvaluateCopyrightValueResponse;
import org.dongguk.dambo.dto.musiccopyright.response.MyCopyrightResponse;
import org.dongguk.dambo.dto.musiccopyright.response.MyCopyrightsResponse;
import org.dongguk.dambo.repository.contract.ContractRepository;
import org.dongguk.dambo.repository.musiccopyright.MusicCopyrightRepository;
import org.dongguk.dambo.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MusicCopyrightService {
    private final UserRepository userRepository;
    private final MusicCopyrightRepository musicCopyrightRepository;
    private final ContractRepository contractRepository;

    public MyCopyrightsResponse getMyNfts(Long userId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.type(UserErrorCode.NOT_FOUND_USER));

        List<EContractStatus> statuses;
        if ("ALL".equalsIgnoreCase(status)) {
            statuses = List.of(EContractStatus.REGISTERED, EContractStatus.INVESTING, EContractStatus.MATCHED);
        } else {
            statuses = List.of(EContractStatus.valueOf(status));
        }

        List<MyCopyrightResponse> myCopyrightResponses =
                contractRepository.findAllByOwnerIdAndStatuses(userId, statuses).stream()
                        .map(proj -> MyCopyrightResponse.builder()
                                .copyrightId(proj.getId())
                                .contractId(proj.getContractId())
                                .imageUrl(proj.getImageUrl())
                                .title(proj.getTitle())
                                .type("음원 NFT")
                                .ethPrice(proj.getEthPrice().toPlainString() + "ETH")
                                .status(proj.getStatus().name())
                                .build()
                        ).toList();

        return new MyCopyrightsResponse(myCopyrightResponses);
    }

    public CopyrightDetailResponse getCopyrightDetail(Long copyrightId) {
        MusicCopyright copyright = musicCopyrightRepository.findById(copyrightId)
                .orElseThrow(() -> CustomException.type(MusicCopyrightErrorCode.NOT_FOUND_MUSIC_COPYRIGHT));

        Contract contract = contractRepository.findByMusicCopyright_Id(copyrightId)
                .orElseThrow(() -> CustomException.type(ContractErrorCode.NOT_FOUND_CONTRACT));


        return CopyrightDetailResponse.builder()
                .copyrightId(copyright.getId())
                .status(contract.getStatus().name())
                .imageUrl(copyright.getImageUrl())
                .title(copyright.getTitle())
                .type("음원 NFT")
                .ethPrice(copyright.getEthPrice().toPlainString() + "ETH")
                .wonPrice(NumberFormat.getInstance(Locale.KOREA).format(copyright.getWonPrice()) + "원")
                .singers(copyright.getSinger())
                .composers(copyright.getComposer())
                .lyricists(copyright.getLyricist())
                .streamingUrls(copyright.getStreamingUrl())
                .isRegistered(copyright.getIsRegistered() ? "저작권이 등록되어 있는 음원" : "저작권 미등록 음원")
                .registrationDoc(copyright.getRegistrationDoc())
                .build();
    }

    public EvaluateCopyrightValueResponse evaluateCopyright(EvaluateCopyrightValueRequest request) {
        // request 이용해서 AI 돌리는 로직 필요

        BigDecimal ethPrice = BigDecimal.valueOf(
                ThreadLocalRandom.current().nextDouble(50.0, 200.0)
        ).setScale(4, RoundingMode.HALF_UP);
        Long wonPrice = ethPrice.multiply(BigDecimal.valueOf(LoanConstants.EthereumMarketPrice)).longValue();


        return EvaluateCopyrightValueResponse.builder()
                .ethPrice(ethPrice.toPlainString() + "ETH")
                .wonPrice(NumberFormat.getInstance(Locale.KOREA).format(wonPrice) + "원")
                .build();
    }
}
