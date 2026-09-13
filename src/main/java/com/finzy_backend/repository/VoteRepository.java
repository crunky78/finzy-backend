package com.finzy_backend.repository;

import com.finzy_backend.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Page<Vote> findByIsPublicTrueOrderByCreatedAtDesc(Pageable pageable);
    Page<Vote> findByIsPublicTrueAndCategoryOrderByCreatedAtDesc(
            com.finzy_backend.entity.Review.Category category, Pageable pageable);
}