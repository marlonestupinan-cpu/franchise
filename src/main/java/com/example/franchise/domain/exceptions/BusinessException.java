package com.example.franchise.domain.exceptions;

import com.example.franchise.domain.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class BusinessException extends ProcessorException {
    public BusinessException(TechnicalMessage technicalMessage, String... params) {
        super(technicalMessage.getMessage(params), technicalMessage);
    }
}