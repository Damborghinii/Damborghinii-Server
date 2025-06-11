package org.dongguk.dambo.implement.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.entity.UserContract;
import org.dongguk.dambo.repository.usercontract.UserContractRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserContractSaver {
    private final UserContractRepository userContractRepository;

    @Transactional
    public UserContract saveUserContract(UserContract userContract){
        return userContractRepository.save(userContract);
    }
}
