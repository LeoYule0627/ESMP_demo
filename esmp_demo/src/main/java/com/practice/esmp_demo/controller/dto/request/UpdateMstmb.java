package com.practice.esmp_demo.controller.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Data
public class UpdateMstmb {
    @NotBlank
    @Length(min = 1, max = 6, message = "股票代號一~六碼")
    private String stock;
    @NotNull
    @DecimalMin(value = "0.00", message = "現價需大於0.00")
    @Digits(integer = 9, fraction = 2, message = "現價格式不正確")
    private BigDecimal curPrice;
}
