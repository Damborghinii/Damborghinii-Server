package org.dongguk.dambo.implement.user;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.User;
import org.dongguk.dambo.domain.exception.user.UserErrorCode;
import org.dongguk.dambo.repository.user.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> CustomException.type(UserErrorCode.NOT_FOUND_USER));
    }
}
