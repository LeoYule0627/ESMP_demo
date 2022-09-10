package com.practice.esmp_demo.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(PrimaryId.class)
public class Hcmio {
    @Id
    @Column(name = "TradeDate")
    private String tradeDate;
    @Id
    @Column(name = "BranchNo")
    private String branchNo;
    @Id
    @Column(name = "CustSeq")
    private String custSeq;
    @Id
    @Column(name = "DocSeq")
    private String docSeq;
    @Column(name = "Stock")
    private String stock;
    @Column(name = "BsType")
    private char bsType;
    @Column(name = "Price")
    private double price;
    @Column(name = "Qty")
    private int qty;
    @Column(name = "Amt")
    private double amt;
    @Column(name = "Fee")
    private int fee;
    @Column(name = "Tax")
    private int tax;
    @Column(name = "StinTax")
    private int stinTax;
    @Column(name = "NetAmt")
    private double netAmt;
    @Column(name = "ModDate")
    private String modDate;
    @Column(name = "ModTime")
    private String modTime;
    @Column(name = "ModUser")
    private String modUser;
}