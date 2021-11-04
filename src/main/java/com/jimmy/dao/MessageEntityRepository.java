package com.jimmy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jimmy.entity.MessageEntity;

@Repository
public interface MessageEntityRepository extends JpaRepository<MessageEntity, Long> {
}
