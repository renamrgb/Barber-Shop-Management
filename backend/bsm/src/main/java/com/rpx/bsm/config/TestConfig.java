package com.rpx.bsm.config;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.repositories.FormaPagamentoRepository;
import com.rpx.bsm.repositories.ProdutoRepository;
import com.rpx.bsm.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public void run(String... args) throws Exception {

        Estoque eProd1 = new Estoque();
        Produto prod1 = new Produto();
        prod1.setTitulo("Usuario teste 1");
        prod1.setValor(100.00);
        eProd1.setQuantidade(1);
        prod1.setEstoque(eProd1);
        produtoRepository.save(prod1);


        Cliente cli = new Cliente();
        cli.setNome("Rafael Paulo");
        cli.setTelefone("18997101710");
        cli.setEndereco(new Endereco("19210000", "Rua das hortencias", "Jardim das Flores", "", "Tarabai", "SP"));

        Usuario u = new Usuario();
        u.setNome("Rafael Paulo");
        u.setAdmin(true);
        u.setEmail("rafael8paulo@gmail.com");
        u.setAtivo(true);
        u.setSenha("12345678");
        u.setCliente(cli);
        Profissional prof = new Profissional("Rafael", "18997101710", true);
        u.setProfissional(prof);

        usuarioRepository.save(u);

        FormaPagamento fp = new FormaPagamento("Cartão de Credito", true);
        formaPagamentoRepository.save(fp);

    }

}
