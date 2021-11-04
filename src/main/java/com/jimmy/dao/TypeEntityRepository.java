package com.jimmy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jimmy.entity.TypeEntity;

@Repository
public interface TypeEntityRepository extends JpaRepository<TypeEntity, Long> {
}
