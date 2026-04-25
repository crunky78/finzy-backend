package com.finzy_backend.repository;

import com.finzy_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialTypeAndSocialId(User.SocialType socialType, String socialId);
    Optional<User> findByEmail(String email);
    boolean existsByNickname(String nickname);
}