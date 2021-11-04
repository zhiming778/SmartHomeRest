package com.jimmy.service;

import com.jimmy.vo.Device;
import com.jimmy.vo.PagedResponse;

public interface DeviceService {

    PagedResponse<Device> findPaginateDevices(int page, int rows, String order);

    Device findById(Long id);

    Device saveDevice(Device device);

    Device updateDevice(Long id, Device device);

    Device deleteById(Long id);
}
