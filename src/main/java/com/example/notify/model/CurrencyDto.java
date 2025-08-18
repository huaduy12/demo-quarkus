package com.example.notify.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {
    private Long id;
    @NotBlank(message = "Mã ccy không được để trống")
    private String ccyCode;
    @NotBlank(message = "Tên ccy không được để trống")
    private String ccyName;

    @NotNull(message = "Số ccy không được để trống")
    @Min(value = 1, message = "Số ccy phải lớn hơn 0")
    private Integer numOfDecimal;
}
