package com.practice.esmp_demo.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class CreateHcmio {
    private String tradeDate;
    private String BranchNo;
    private String CustSeq;
    private String DocSeq;
    private String Stock;
    private char BsType;
    private double Price;
    private int Qty;
    private String modDate;
    private String modTime;
    private String modUser;
}
