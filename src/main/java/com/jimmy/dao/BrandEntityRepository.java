package com.jimmy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jimmy.entity.BrandEntity;

@Repository
public interface BrandEntityRepository extends JpaRepository<BrandEntity, Long> {
}
