package com.rpx.bsm.services;

import com.rpx.bsm.entities.Produto;
import com.rpx.bsm.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Produto insert(Produto obj) {
        return repository.save(obj);
    }

}
