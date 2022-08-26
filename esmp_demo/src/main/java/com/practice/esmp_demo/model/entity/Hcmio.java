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
@IdClass(HcmioId.class)
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
    private String bsType;
    @Column(name = "Price")
    private String price;
    @Column(name = "Qty")
    private String qty;
    @Column(name = "Amt")
    private String amt;
    @Column(name = "Fee")
    private String fee;
    @Column(name = "Tax")
    private String tax;
    @Column(name = "StinTax")
    private String stinTax;
    @Column(name = "NetAmt")
    private String netAmt;
    @Column(name = "ModDate")
    private String modDate;
    @Column(name = "ModTime")
    private String modTime;
    @Column(name = "ModUser")
    private String modUser;
}

class HcmioId implements Serializable {
    private String tradeDate;
    private String branchNo;
    private String custSeq;
    private String docSeq;
}