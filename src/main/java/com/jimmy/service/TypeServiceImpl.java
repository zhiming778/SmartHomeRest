package com.jimmy.service;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jimmy.dao.TypeEntityRepository;
import com.jimmy.entity.TypeEntity;
import com.jimmy.util.TypeConverter;
import com.jimmy.vo.PagedResponse;
import com.jimmy.vo.Type;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeEntityRepository repository;

    @Override
    public PagedResponse<Type> findPaginatedTypes(int page, int rows, String order) {
        Page<TypeEntity> p = repository.findAll(PageRequest.of(page, rows, Sort.by(order)));
        PagedResponse<Type> response = new PagedResponse<>();
        response.setPage(p.getNumber());
        response.setRows(p.getSize());
        response.setOrder(p.getSort().toString());
        response.setBody(
                p.getContent().stream().map(entity -> TypeConverter.toType(entity)).collect(Collectors.toList()));
        return response;
    }

    @Override
    public Type findById(Long id) {
        Optional<TypeEntity> optional = repository.findById(id);
        return TypeConverter.toType(optional.orElse(null));
    }

    @Transactional
    @Override
    public Type saveType(Type type) {
        TypeEntity savedEntity = repository.save(TypeConverter.toTypeEntity(type));
        return TypeConverter.toType(savedEntity);
    }

    @Override
    public Type updateType(Long id, Type type) {
        if (!repository.existsById(id))
            return null;
        TypeEntity entity = TypeConverter.toTypeEntity(type);
        entity.setId(id);
        return TypeConverter.toType(repository.saveAndFlush(entity));
    }

    @Override
    public Type deleteType(Long id) {
        TypeEntity entity = repository.findById(id).orElse(null);
        if (entity == null)
            return null;
        repository.deleteById(id);
        return TypeConverter.toType(entity);
    }

}
