package com.practice.esmp_demo.controller.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Search {
    @NotBlank
    @Length(min = 4, max = 4, message = "分公司代號四碼")
    private String branchNo;
    @NotBlank
    @Length(min = 2, max = 2, message = "客戶代號兩碼")
    private String custSeq;
    @Length(min = 0, max = 4, message = "股票代號四碼")
    private String stock;
    @NotNull
    private double max;
    @NotNull
    private double min;
}
