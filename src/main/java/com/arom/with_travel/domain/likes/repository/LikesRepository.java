package com.arom.with_travel.domain.likes.repository;

import com.arom.with_travel.domain.likes.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
