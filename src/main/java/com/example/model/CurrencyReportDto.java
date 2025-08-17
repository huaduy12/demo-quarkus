package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyReportDto {
    private Long id;
    private String ccyCode;
    private String ccyName;
    private Integer numOfDecimal;
    private String message;

    public CurrencyReportDto(Long id, String ccyCode, String ccyName, Integer numOfDecimal, String message) {
        this.id = id;
        this.ccyCode = ccyCode;
        this.ccyName = ccyName;
        this.numOfDecimal = numOfDecimal;
        this.message = message;
    }
}
