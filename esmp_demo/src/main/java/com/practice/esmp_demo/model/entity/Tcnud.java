package com.practice.esmp_demo.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(TcnudId.class)
public class Tcnud {
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
    @Column(name = "Price")
    private int price;
    @Column(name = "Qty")
    private int qty;
    @Column(name = "RemainQty")
    private int remainQty;
    @Column(name = "Fee")
    private int fee;
    @Column(name = "Cost")
    private int cost;
    @Column(name = "ModDate")
    private String modDate;
    @Column(name = "ModTime")
    private String modTime;
    @Column(name = "ModUser")
    private String modUser;
}
class TcnudId implements Serializable {
    private String tradeDate;
    private String branchNo;
    private String custSeq;
    private String docSeq;
}