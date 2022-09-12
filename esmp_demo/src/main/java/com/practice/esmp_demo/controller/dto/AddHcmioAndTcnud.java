package com.practice.esmp_demo.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Data
public class AddHcmioAndTcnud {
    @NotBlank
    @Pattern(regexp = "^[(?=.\\d)]{8}$", message = "日期格式yyyyMMdd")
    private String tradeDate;
    @NotBlank
    @Length(min = 4, max = 4, message = "分公司代號四碼")
    private String branchNo;
    @NotBlank
    @Length(min = 2, max = 2, message = "客戶代號兩碼")
    private String custSeq;
    @NotBlank
    @Pattern(regexp = "^[(?=.[A-Z])(?=.[A-Z])(?=.\\d)]{5}", message = "2個大寫英文和3個數字")
    private String docSeq;
    @NotBlank
    @Length(min = 4, max = 4, message = "股票代號四碼")
    private String stock;
    @NotNull
    @DecimalMin(value = "0.00", message = "價格需大於0.00")
    private BigDecimal price;
    @NotNull
    @Min(value = 1000, message = "股數需大於1000")
    private int qty;
    private String modUser;
}
