package com.jimmy.util;

import com.jimmy.entity.TypeEntity;
import com.jimmy.vo.Type;

public class TypeConverter {
    public static Type toType(TypeEntity entity) {
        if (entity == null)
            return null;
        return new Type(entity.getId(), entity.getName(), entity.getDescription());
    }

    public static TypeEntity toTypeEntity(Type type) {
        TypeEntity entity = new TypeEntity();
        entity.setName(type.getName());
        entity.setDescription(type.getDescription());
        return entity;
    }
}
