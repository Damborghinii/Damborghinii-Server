package org.dongguk.dambo.implement.user;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUpdater {
    @Transactional
    public void updateUserCashOnRepayment(User user, Long repaymentAmount){
        user.updateCashOnRepayment(repaymentAmount);
    }

    @Transactional
    public void updateOtherUserCashOnRepayment(List<User> userList, List<Long> repaymentAmountList) {
        for(int i = 0; i < userList.size(); i++) {
            userList.get(i).updateCashOnReceiveRepayment(repaymentAmountList.get(i));
        }
    }
}
