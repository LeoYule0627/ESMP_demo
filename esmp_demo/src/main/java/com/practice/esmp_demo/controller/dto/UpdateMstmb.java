package com.practice.esmp_demo.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMstmb {
    private BigDecimal curPrice;
    private String modDate;
    private String modTime;
    private String modUser;
}
