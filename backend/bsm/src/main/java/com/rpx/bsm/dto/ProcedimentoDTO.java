package com.rpx.bsm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProcedimentoDTO {
    private Long id;
    private String descricao;
    private Double valor;
    private Boolean ativo;

    public ProcedimentoDTO(Long id, String descricao, Double valor, Boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.ativo = ativo;
    }
}
