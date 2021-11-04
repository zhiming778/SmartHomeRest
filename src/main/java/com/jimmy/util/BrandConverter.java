package com.jimmy.util;

import com.jimmy.entity.BrandEntity;
import com.jimmy.vo.Brand;

public class BrandConverter {
    public static Brand toBrand(BrandEntity entity) {
        if (entity == null)
            return null;
        return new Brand(entity.getId(), entity.getName(), entity.getLocation());
    }

    public static BrandEntity toBrandEntity(Brand brand) {
        BrandEntity entity = new BrandEntity();
        entity.setName(brand.getName());
        entity.setLocation(brand.getLocation());
        return entity;
    }
}
