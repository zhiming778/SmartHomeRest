package com.jimmy.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jimmy.dao.BrandEntityRepository;
import com.jimmy.entity.BrandEntity;
import com.jimmy.util.BrandConverter;
import com.jimmy.vo.Brand;
import com.jimmy.vo.PagedResponse;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandEntityRepository repository;

    @Override
    public PagedResponse<Brand> findPaginatedBrands(int page, int rows, String order) {
        Page<BrandEntity> p = repository.findAll(PageRequest.of(page, rows, Sort.by(order)));
        PagedResponse<Brand> response = new PagedResponse<>();
        response.setPage(p.getNumber());
        response.setRows(p.getSize());
        response.setOrder(p.getSort().toString());
        response.setBody(
                p.getContent().stream().map(entity -> BrandConverter.toBrand(entity)).collect(Collectors.toList()));
        return response;
    }

    @Override
    public Brand findById(Long id) {
        return BrandConverter.toBrand(repository.findById(id).orElse(null));
    }

    @Transactional
    @Override
    public Brand saveBrand(Brand brand) {
        BrandEntity entity = repository.save(BrandConverter.toBrandEntity(brand));
        return BrandConverter.toBrand(entity);
    }

    @Override
    public Brand updateBrand(Long id, Brand brand) {
        if (!repository.existsById(id))
            return null;
        BrandEntity temp = BrandConverter.toBrandEntity(brand);
        temp.setId(id);
        return BrandConverter.toBrand(repository.saveAndFlush(temp));
    }

    @Override
    public Brand deleteBrand(Long id) {
        BrandEntity entity = repository.findById(id).orElse(null);
        if (entity == null)
            return null;
        repository.deleteById(id);
        return BrandConverter.toBrand(entity);
    }

}
