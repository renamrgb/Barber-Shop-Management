package com.rpx.bsm.config;

import com.rpx.bsm.entities.*;
import com.rpx.bsm.enums.NivelAcessoEnum;
import com.rpx.bsm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
    @Autowired
    private ParameterRepository parametersRepository;

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
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
        Procedure proc1 = new Procedure("Corte de Cabelo", 100.00, true, LocalTime.of(0, 30));
        procedimentoRepository.save(proc1);
        for (int i = 0; i < 25; i++)
            procedimentoRepository.save(new Procedure("PROCEDIMENTO" + i, 100.00, true, LocalTime.of(0, 30)));
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
            p.getProcedures().add(proc1);
            p.getProcedures().add(new Procedure(2L));
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

        Expense e2 = new Expense("DESPESA 26", 100.00, 30, LocalDate.parse("2023-03-04"), 3,  new ExpenseType(1L));
        for (int i = 0; i < 3; i++){
            Installment installment = new Installment(BigDecimal.valueOf(100.00), LocalDate.parse("2023-03-04"));
            installment.setExpense(e2);
            e2.getInstallments().add(installment);
        }
        expenseRepository.save(e2);
        for (int i = 0; i < 25; i++){
            Expense e = new Expense("DESPESA "+i, 100.00, 30, LocalDate.parse("2023-03-04"), 0,  new ExpenseType(27L));
            Installment installment = new Installment(BigDecimal.valueOf(100.00), LocalDate.parse("2023-03-04"));
            installment.setExpense(e);
            e.getInstallments().add(installment);
            expenseRepository.save(e);
        }
        Organization organization = new Organization();
        organization.setCnpj("92384290348092");
        organization.setName("Xavvier Barber Shop");
        organizationRepository.save(organization);
        Parameter parameter = new Parameter();
        parameter.setOrganization(organization);
        parameter.getParameterValues().add(new ParameterValue("Horário de início","START_TIME", "08:00", parameter));
        parameter.getParameterValues().add(new ParameterValue("Horário de início do almoço","LUNCH_START_TIME", "12:00", parameter));
        parameter.getParameterValues().add(new ParameterValue("Horário de termino do almoço","LUNCH_BREAK_TIME", "14:00", parameter));
        parameter.getParameterValues().add(new ParameterValue("Horário de encerramento","END_TIME", "18:00", parameter));
        parametersRepository.save(parameter);
        /*============================================================================================================*/
        Set<Procedure> procedures = new HashSet<>();
        procedures.add(new Procedure(1L));
        Schedule s = new Schedule();
        s.setCustomer(new Customer(1L));
        s.setProcedures(procedures);
        LocalDateTime agora = LocalDateTime.now();
        agora = agora.withHour(16).withMinute(0).withSecond(0).withNano(0);
        s.setStartDate(agora);
        s.setEndDate(agora.plusMinutes(90));
        s.setProfessional(new Professional(1L));
        s.setPaymentSchedule(new PaymentSchedule(s));
        scheduleRepository.save(s);
        /*============================================================================================================*/
        Schedule s2 = new Schedule();
        s2.setCustomer(new Customer(2L));
        s2.setProcedures(procedures);
        agora = LocalDateTime.now();
        agora = agora.withHour(10).withMinute(0).withSecond(0).withNano(0);
        s2.setStartDate(agora);
        s2.setEndDate(agora.plusMinutes(90));
        s2.setProfessional(new Professional(1L));
        s2.setPaymentSchedule(new PaymentSchedule(s2));
        scheduleRepository.save(s2);
    }

}
