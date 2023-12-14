package com.payhere.cafe.service;

import com.payhere.cafe.domain.dto.ProductDTO;
import com.payhere.cafe.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper mapper;



}
