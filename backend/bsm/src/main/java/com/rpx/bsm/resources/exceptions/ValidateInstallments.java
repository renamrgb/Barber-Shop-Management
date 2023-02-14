package com.rpx.bsm.resources.exceptions;

public class ValidateInstallments extends RuntimeException {

    private static final long serialVersionUID = 4928599035264976611L;

    public ValidateInstallments(String message) {
        super(message);
    }

    public ValidateInstallments(Throwable t) {
        super(t);
    }


}
