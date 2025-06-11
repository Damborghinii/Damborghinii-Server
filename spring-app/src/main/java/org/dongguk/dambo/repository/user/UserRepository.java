package org.dongguk.dambo.repository.user;

import org.dongguk.dambo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
