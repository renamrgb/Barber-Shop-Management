package com.rpx.bsm.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcedimentoDTO {
    private Long id;

    private String descricao;
    private Double valor;

    private Boolean ativo;

    public ProcedimentoDTO(Long id) {
        this.id = id;
    }

    public ProcedimentoDTO(Long id, String descricao, Double valor, Boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.ativo = ativo;
    }
}
