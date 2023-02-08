package com.rpx.bsm.dto;

import com.rpx.bsm.entities.Expense;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


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

    public ExpenseDTO(Expense obj){
        setId(obj.getId());
        setDescription(obj.getDescription());
        setExpenseType(new ExpenseTypeDTO(obj.getExpenseType()));
        setTotal(obj.getTotal());
        setDaysBeetwenInstallments(getDaysBeetwenInstallments());
        setReleaseDate(getReleaseDate());
        setQuantityOfInstallments(getQuantityOfInstallments());
    }

}
