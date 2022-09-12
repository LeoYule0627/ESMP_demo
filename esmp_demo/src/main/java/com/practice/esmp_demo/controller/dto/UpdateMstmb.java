package com.practice.esmp_demo.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMstmb {
    @NotBlank
    @Length(min = 4, max = 4, message = "股票代號四碼")
    private String stock;
    @NotNull
    @DecimalMin(value = "0.00", message = "價格需大於0.00")
    private BigDecimal curPrice;
    private String modUser;
}
