package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Expense;
import com.rpx.bsm.entities.Installment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    private Integer quantityOfInstallments;

    private List<InstallmentDTO> installments;

    public ExpenseDTO(Expense obj){
        setId(obj.getId());
        setDescription(obj.getDescription());
        setExpenseType(new ExpenseTypeDTO(obj.getExpenseType()));
        setTotal(obj.getTotal());
        setDaysBeetwenInstallments(obj.getDaysBeetwenInstallments());
        setReleaseDate(obj.getReleaseDate());
        setQuantityOfInstallments(obj.getQuantityOfInstallments());

        installments = new ArrayList<>();
        for (Installment e : obj.getInstallments()){
            installments.add(new InstallmentDTO(e));
        }

    }

}
