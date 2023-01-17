package com.rpx.bsm.config;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.repositories.paymentMethodRepository;
import com.rpx.bsm.repositories.ProcedimentoRepository;
import com.rpx.bsm.repositories.ProdutoRepository;
import com.rpx.bsm.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private paymentMethodRepository formaPagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProcedimentoRepository procedimentoRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void run(String... args) throws Exception {


        Produto prod1 = new Produto();
        prod1.setTitulo("Produto teste 1");
        prod1.setValor(100.00);
        prod1.setQuantidade(1);
        prod1.setMarca("Marca Teste 1");
        prod1.setAtivo(true);
        produtoRepository.save(prod1);

        /*============================================================================================================*/
        Procedimento proc1 = new Procedimento("Corte de Cabelo", 100.00, true);
        procedimentoRepository.save(proc1);
        /*============================================================================================================*/
        Cliente cli = new Cliente();
        Usuario u = new Usuario();
        u.setNome("Rafael Paulo");
        u.setTelefone("18997101710");
        u.setNome("Rafael Paulo");
        u.setAdmin(true);
        u.setEmail("rafael8paulo@gmail.com");
        u.setAtivo(true);
        u.setSenha(bCryptPasswordEncoder.encode("12345678"));
        u.setNivelAcesso(new NivelAcesso(NivelAcessoEnum.ROLE_ADMIN));

        Profissional prof = new Profissional();
        prof.setUsuario(u);
        Set<Procedimento> procedimentos = new HashSet<>();
        procedimentos.add(new Procedimento(1L));
        prof.setProcedimentos(procedimentos);
        usuarioRepository.save(u);
        /*============================================================================================================*/

        PaymentMethod fp = new PaymentMethod("Cartão de Credito", true);
        formaPagamentoRepository.save(fp);

    }

}
