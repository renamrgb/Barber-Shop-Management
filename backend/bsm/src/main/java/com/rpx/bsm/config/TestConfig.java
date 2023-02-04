package com.rpx.bsm.config;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.repositories.PaymentMethodRepository;
import com.rpx.bsm.repositories.ProcedureRepository;
import com.rpx.bsm.repositories.ProductRepository;
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
    private PaymentMethodRepository formaPagamentoRepository;

    @Autowired
    private ProductRepository produtoRepository;

    @Autowired
    private ProcedureRepository procedimentoRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {


        Product prod1 = new Product();
        prod1.setTitle("Produto teste 1");
        prod1.setPrice(100.00);
        prod1.setQuantity(1);
        prod1.setBrand("Marca Teste 1");
        prod1.setIsActive(true);
        produtoRepository.save(prod1);

        /*============================================================================================================*/
        Procedure proc1 = new Procedure("Corte de Cabelo", 100.00, true);
        procedimentoRepository.save(proc1);
        /*============================================================================================================*/
        Customer cli = new Customer();
        User u = new User();
        u.setName("Rafael Paulo");
        u.setPhoneNumber("18997101710");
        u.setName("Rafael Paulo");
        u.setEmail("rafael8paulo@gmail.com");
        u.setIsActive(true);
        u.setPassword(bCryptPasswordEncoder.encode("12345678"));
        u.setNivelAcesso(new NivelAcesso(NivelAcessoEnum.ROLE_ADMIN));

        Profissional prof = new Profissional();
        prof.setUsuario(u);
        Set<Procedure> procedimentos = new HashSet<>();
        procedimentos.add(new Procedure(1L));
        prof.setProcedimentos(procedimentos);
        usuarioRepository.save(u);
        /*============================================================================================================*/

        PaymentMethod fp = new PaymentMethod("Cartão de Credito", true);
        formaPagamentoRepository.save(fp);

    }

}
