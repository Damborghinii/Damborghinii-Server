package org.dongguk.dambo.implement.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.entity.UserContract;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserContractUpdater {
    public void updateRound(List<UserContract> userContractList) {
        userContractList.forEach(
                UserContract::updateRound
        );
    }
}
