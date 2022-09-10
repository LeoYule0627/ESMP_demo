package com.practice.esmp_demo.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class Calculate {

    BigDecimal feeRate = new BigDecimal("0.001425");
    BigDecimal taxRate = new BigDecimal("0.003");

    public BigDecimal getAmt(BigDecimal qty, BigDecimal buyPrice) {
        return buyPrice.multiply(qty).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getFee(BigDecimal qty, BigDecimal buyPrice) {
        return buyPrice.multiply(qty)
                .multiply(feeRate).setScale(0, RoundingMode.DOWN);
    }

    public BigDecimal getTax(BigDecimal qty, BigDecimal buyPrice) {
        return buyPrice.multiply(qty)
                .multiply(taxRate).setScale(0, RoundingMode.DOWN);
    }

    public BigDecimal getNetAmt(BigDecimal qty, BigDecimal buyPrice, char BsType) {
        BigDecimal amt = buyPrice.multiply(qty);
        BigDecimal fee = amt.multiply(feeRate).setScale(0, RoundingMode.DOWN);
        BigDecimal tax = amt.multiply(taxRate).setScale(0, RoundingMode.DOWN);
        System.out.println(BsType);
        if (BsType == 'B') {
            return amt.subtract(fee).setScale(2, RoundingMode.HALF_UP);
        } else {
            return amt.subtract(fee).subtract(tax).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getCost(BigDecimal qty, BigDecimal buyPrice) {
        BigDecimal amt = buyPrice.multiply(qty);
        BigDecimal fee = amt.multiply(feeRate).setScale(0, RoundingMode.DOWN);
        BigDecimal cost = amt.subtract(fee).setScale(2, RoundingMode.HALF_UP);
        return cost;
    }


    public String getModDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        return dateFormat.format(c1.getTime());
    }

    public String getModTime() {
        Format timeFormat = new SimpleDateFormat("HHmmss");
        return timeFormat.format(new Date());
    }


}
