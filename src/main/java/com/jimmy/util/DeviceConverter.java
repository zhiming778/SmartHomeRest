package com.jimmy.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.jimmy.entity.DeviceEntity;
import com.jimmy.vo.Device;

public class DeviceConverter {
    public static Device toDevice(DeviceEntity entity) {
        if (entity == null)
            return null;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(entity.getManufactureDate());
        return new Device(entity.getId(), entity.getName(), calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH) + 1, calendar.get(calendar.DAY_OF_MONTH), entity.getType().getId(),
                entity.getBrand().getId());
    }

    public static DeviceEntity toDeviceEntity(Device device) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(device.getManufactureYear(), device.getManufactureMonth() - 1, device.getManufactureDay());
        Date date = calendar.getTime();
        DeviceEntity entity = new DeviceEntity();
        entity.setName(device.getName());
        entity.setManufactureDate(date);
        return entity;
    }
}
