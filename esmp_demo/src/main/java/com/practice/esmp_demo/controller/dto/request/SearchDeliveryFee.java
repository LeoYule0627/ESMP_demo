package com.practice.esmp_demo.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SearchDeliveryFee {

    @NotBlank
    @Length(min = 4, max = 4, message = "分公司代號四碼")
    private String branchNo;
    @NotBlank
    @Length(min = 2, max = 2, message = "客戶代號兩碼")
    private String custSeq;
}
