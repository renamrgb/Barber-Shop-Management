package com.rpx.bsm.config;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
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

    @Autowired
    private MessageTemplateRepository messageTemplateRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {

        ProductProcedureType productProcedureTypes = new ProductProcedureType("Padrão", true);
        productProcedureTypesRepository.save(productProcedureTypes);
        for (int i = 0; i < 25; i++)
            productProcedureTypesRepository.save(new ProductProcedureType("TIPO" + i, true));
        for (int i = 0; i < 25; i++) {
            produtoRepository.save(new Product("PRODUTO" + i, 100.00, "MARCA DE TESTE", 100, new ProductProcedureType(1L), true));
        }
        for (int i = 25; i < 50; i++) {
            produtoRepository.save(new Product("PRODUTO" + i, 100.00, "MARCA DE TESTE", 100, new ProductProcedureType(1L), false));
        }
        for (int i = 0; i < 25; i++) {
            Customer customer = new Customer();
            customer.setUser(new User("CLIENTE " + i, "customer" + i + "@teste.com.br", "12345678", "18997101710", "50349034826", "102345879", new Address(), new AccessLevel(NivelAcessoEnum.ROLE_ADMIN), true));
            customerRepository.save(customer);
        }
        /*============================================================================================================*/
        Procedure proc1 = new Procedure("Corte de Cabelo", 100.00, true);
        procedimentoRepository.save(proc1);
        for (int i = 0; i < 25; i++)
            procedimentoRepository.save(new Procedure("PROCEDIMENTO" + i, 100.00, true));
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
        for (int i = 0; i < 25; i++) {
            Professional p = new Professional();
            p.setUser(new User(
                    "PROFISSIONAL " + i, "user" + i + "@teste.com.br", "12345678", "18997101710", "50349034826", "102345879", new Address(), new AccessLevel(NivelAcessoEnum.ROLE_ADMIN), true
            ));
            p.setProcedures(new HashSet<>());
            professionalRepository.save(p);
        }

        /*============================================================================================================*/

        PaymentMethod fp = new PaymentMethod("Cartão de Credito", true);
        formaPagamentoRepository.save(fp);
        for (int i = 0; i < 25; i++)
            formaPagamentoRepository.save(new PaymentMethod("FORMA DE PAGAMENTO" + i, true));
        /*============================================================================================================*/
        ExpenseType expenseType = new ExpenseType("Com parcelas", true, true);
        expenseTypeRepository.save(expenseType);

        for (int i = 0; i < 25; i++)
            expenseTypeRepository.save(new ExpenseType("TIPO DE DESPESA" + i, true, true));

        expenseType = new ExpenseType("Sem parcelas", false, true);
        expenseTypeRepository.save(expenseType);
        /*============================================================================================================*/
        for (int i = 0; i < 25; i++)
            messageTemplateRepository.save(new MessageTemplate("MODELO" + i, "MENSAGEM DE TESTE", true));

        for (int i = 0; i < 25; i++)
            expenseRepository.save(new Expense("DESPESA "+i, 100.00, 30, LocalDate.parse("2023-03-04"), 0,  new ExpenseType(1L)));

    }

}
