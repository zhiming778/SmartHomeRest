package com.jimmy.service;

import com.jimmy.vo.PagedResponse;
import com.jimmy.vo.Type;

public interface TypeService {
    PagedResponse<Type> findPaginatedTypes(int page, int rows, String order);

    Type findById(Long id);

    Type saveType(Type type);

    Type updateType(Long id, Type type);

    Type deleteType(Long id);
}
