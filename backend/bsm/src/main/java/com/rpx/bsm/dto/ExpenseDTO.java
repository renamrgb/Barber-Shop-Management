package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.Installment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class ExpenseDTO {
    private Long Id;
    private String description;
    private ExpenseTypeDTO expenseType;
    private Double total;
    private Integer daysBeetwenInstallments;
    private LocalDate releaseDate;
    private LocalDate dueDate;
    private Integer quantityOfInstallments;
    private Set<InstallmentDTO> installments;
    public ExpenseDTO(Expense obj){
        setId(obj.getId());
        setDescription(obj.getDescription());
        setExpenseType(new ExpenseTypeDTO(obj.getExpenseType()));
        setTotal(obj.getTotal());
        setDaysBeetwenInstallments(obj.getDaysBeetwenInstallments());
        setReleaseDate(obj.getReleaseDate());
        setQuantityOfInstallments(obj.getQuantityOfInstallments());
        setDueDate(obj.getDueDate());
        installments = new HashSet<>();
        for (Installment e : obj.getInstallments()){
            installments.add(new InstallmentDTO(e));
        }

    }

}
