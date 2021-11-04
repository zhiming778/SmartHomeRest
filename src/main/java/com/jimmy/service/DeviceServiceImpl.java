package com.jimmy.service;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jimmy.dao.BrandEntityRepository;
import com.jimmy.dao.DeviceEntityRepository;
import com.jimmy.dao.TypeEntityRepository;
import com.jimmy.entity.BrandEntity;
import com.jimmy.entity.DeviceEntity;
import com.jimmy.entity.TypeEntity;
import com.jimmy.util.DeviceConverter;
import com.jimmy.vo.Device;
import com.jimmy.vo.PagedResponse;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceEntityRepository deviceEntityRepository;

    @Autowired
    TypeEntityRepository typeEntityRepository;

    @Autowired
    BrandEntityRepository brandEntityRepository;

    @Override
    public PagedResponse<Device> findPaginateDevices(int page, int rows, String order) {
        Page<DeviceEntity> p = deviceEntityRepository.findAll(PageRequest.of(page, rows, Sort.by(order)));
        PagedResponse<Device> response = new PagedResponse<>();
        response.setPage(p.getNumber());
        response.setRows(p.getSize());
        response.setOrder(p.getSort().toString());
        response.setBody(
                p.getContent().stream().map(entity -> DeviceConverter.toDevice(entity)).collect(Collectors.toList()));
        return response;
    }

    @Override
    public Device findById(Long id) {
        Optional<DeviceEntity> optional = deviceEntityRepository.findById(id);
        return DeviceConverter.toDevice(optional.orElse(null));
    }

    @Transactional
    @Override
    public Device saveDevice(Device device) {
        TypeEntity typeEntity = typeEntityRepository.findById(device.getTypeId()).orElse(null);
        if (typeEntity == null)
            return null;
        BrandEntity brandEntity = brandEntityRepository.findById(device.getBrandId()).orElse(null);
        if (brandEntity == null)
            return null;
        DeviceEntity entity = DeviceConverter.toDeviceEntity(device);
        entity.setType(typeEntity);
        entity.setBrand(brandEntity);
        return DeviceConverter.toDevice(deviceEntityRepository.save(entity));
    }

    @Override
    public Device updateDevice(Long id, Device device) {
        if (!deviceEntityRepository.existsById(id))
            return null;
        TypeEntity typeEntity = typeEntityRepository.getById(device.getTypeId());
        if (typeEntity == null)
            return null;
        BrandEntity brandEntity = brandEntityRepository.getById(device.getBrandId());
        if (brandEntity == null)
            return null;
        DeviceEntity entity = DeviceConverter.toDeviceEntity(device);
        entity.setType(typeEntity);
        entity.setBrand(brandEntity);
        entity.setId(id);
        return DeviceConverter.toDevice(deviceEntityRepository.saveAndFlush(entity));
    }

    @Transactional
    @Override
    public Device deleteById(Long id) {
        DeviceEntity entity = deviceEntityRepository.findById(id).orElse(null);
        if (entity == null)
            return null;
        Device deletedDevice = DeviceConverter.toDevice(entity);
        deviceEntityRepository.deleteById(id);
        return deletedDevice;
    }

}
