package com.rpx.bsm.config;

import com.rpx.bsm.entities.Estoque;
import com.rpx.bsm.entities.FormaPagamento;
import com.rpx.bsm.entities.Produto;
import com.rpx.bsm.repositories.FormaPagamentoRepository;
import com.rpx.bsm.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Override
    public void run(String... args) throws Exception {

        Produto prod1 = new Produto();
        Estoque eProd1 = new Estoque();

        prod1.setTitulo("Produto teste 1");
        prod1.setValor(100.00);
        eProd1.setQuantidade(1);
        eProd1.setProduto(prod1);
        prod1.setEstoque(eProd1);
        produtoRepository.save(prod1);

        FormaPagamento fp = new FormaPagamento("Cartão de Credito", true);

        formaPagamentoRepository.save(fp);

    }

}
