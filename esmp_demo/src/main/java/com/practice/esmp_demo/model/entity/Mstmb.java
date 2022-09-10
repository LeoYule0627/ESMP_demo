package com.practice.esmp_demo.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Mstmb {
    @Id
    @Column(name = "Stock")
    private String stock;
    @Column(name = "StockName")
    private String stockName;
    @Column(name = "MarketType")
    private String marketType;
    @Column(name = "CurPrice")
    private BigDecimal curPrice;
    @Column(name = "RefPrice")
    private BigDecimal refPrice;
    @Column(name = "Currency")
    private String currency;
    @Column(name = "ModDate")
    private String modDate;
    @Column(name = "ModTime")
    private String modTime;
    @Column(name = "ModUser")
    private String modUser;
}
