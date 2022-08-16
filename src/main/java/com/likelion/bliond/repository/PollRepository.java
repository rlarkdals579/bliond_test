package com.likelion.bliond.repository;

import com.likelion.bliond.domain.Poll;
import com.likelion.bliond.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findByTitleContaining(String keyword);

    Poll findByUser(User user);
}
