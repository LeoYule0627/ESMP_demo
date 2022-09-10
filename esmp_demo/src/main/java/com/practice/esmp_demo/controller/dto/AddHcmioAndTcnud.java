package com.practice.esmp_demo.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class AddHcmioAndTcnud {
    @NotNull
    @Pattern(regexp="^{8}$",message = "日期格式yyyyMMdd")
    private String tradeDate;
    private String BranchNo;
    private String CustSeq;
    private String DocSeq;
    private String Stock;
    private double Price;
    private int Qty;
    private String modDate;
    private String modTime;
    private String modUser;
}
