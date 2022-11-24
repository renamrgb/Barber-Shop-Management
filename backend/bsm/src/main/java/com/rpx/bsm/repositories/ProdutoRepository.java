package com.rpx.bsm.repositories;

import com.rpx.bsm.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}