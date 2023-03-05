package com.rpx.bsm.config;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.repositories.*;
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
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ProductProcedureTypesRepository productProcedureTypesRepository;

    @Override
    public void run(String... args) throws Exception {

        ProductProcedureType productProcedureTypes = new ProductProcedureType("Padrão", true);
        productProcedureTypesRepository.save(productProcedureTypes);
        for (int i=0; i < 25; i++)
            productProcedureTypesRepository.save(new ProductProcedureType("TIPO"+i, true));
        for (int i=0; i < 25; i++){
            produtoRepository.save(new Product("PRODUTO"+i, 100.00, "MARCA DE TESTE", 100, new ProductProcedureType(1L),true));
        }

        for (int i=25; i < 50; i++){
            produtoRepository.save(new Product("PRODUTO"+i, 100.00, "MARCA DE TESTE", 100, new ProductProcedureType(1L), false));
        }

        /*============================================================================================================*/
        Procedure proc1 = new Procedure("Corte de Cabelo", 100.00, true);
        procedimentoRepository.save(proc1);
        for (int i=0; i < 25; i++)
            procedimentoRepository.save(new Procedure("PROCEDIMENTO"+i, 100.00, true));
        /*============================================================================================================*/
        Customer cli = new Customer();
        User u = new User();
        u.setName("Rafael Paulo");
        u.setPhoneNumber("18997101710");
        u.setName("Rafael Paulo");
        u.setEmail("rafael8paulo@gmail.com");
        u.setIsActive(true);
        u.setPassword(bCryptPasswordEncoder.encode("12345678"));
        u.setAccessLevel(new AccessLevel(NivelAcessoEnum.ROLE_ADMIN));

        Professional prof = new Professional();
        prof.setUser(u);
        Set<Procedure> procedimentos = new HashSet<>();
        procedimentos.add(new Procedure(1L));
        prof.setProcedures(procedimentos);
        usuarioRepository.save(u);
        /*============================================================================================================*/

        PaymentMethod fp = new PaymentMethod("Cartão de Credito", true);
        formaPagamentoRepository.save(fp);
        for (int i=0; i < 25; i++)
            formaPagamentoRepository.save(new PaymentMethod("FORMA DE PAGAMENTO"+i, true));
        /*============================================================================================================*/
        ExpenseType expenseType = new ExpenseType("Com parcelas", true, true);
        expenseTypeRepository.save(expenseType);

        for (int i=0; i < 25; i++)
            expenseTypeRepository.save(new ExpenseType("TIPO DE DESPESA"+i, true, true));

        expenseType = new ExpenseType("Sem parcelas", false, true);
        expenseTypeRepository.save(expenseType);
        /*============================================================================================================*/

    }

}
