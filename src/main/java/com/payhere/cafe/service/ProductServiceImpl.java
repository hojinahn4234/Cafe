package com.payhere.cafe.service;

import com.payhere.cafe.domain.dto.ProductDTO;
import com.payhere.cafe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository repository;



}
