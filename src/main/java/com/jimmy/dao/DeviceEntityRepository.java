package com.jimmy.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jimmy.entity.DeviceEntity;

@Repository
public interface DeviceEntityRepository extends JpaRepository<DeviceEntity, Long> {
}
