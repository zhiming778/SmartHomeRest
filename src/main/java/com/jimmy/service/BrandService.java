package com.jimmy.service;

import com.jimmy.vo.Brand;
import com.jimmy.vo.PagedResponse;

public interface BrandService {
    PagedResponse<Brand> findPaginatedBrands(int page, int rows, String order);

    Brand findById(Long id);

    Brand saveBrand(Brand brand);

    Brand updateBrand(Long id, Brand brand);

    Brand deleteBrand(Long id);
}
