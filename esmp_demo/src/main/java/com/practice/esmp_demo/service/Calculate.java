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
        try {
            return buyPrice.multiply(qty).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public BigDecimal getFee(BigDecimal qty, BigDecimal buyPrice) {
        try {
            return buyPrice.multiply(qty)
                    .multiply(feeRate).setScale(0, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public BigDecimal getTax(BigDecimal qty, BigDecimal buyPrice) {
        try {
            return buyPrice.multiply(qty)
                    .multiply(taxRate).setScale(0, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public BigDecimal getNetAmt(BigDecimal qty, BigDecimal buyPrice, char BsType) {
        try {
            BigDecimal amt = buyPrice.multiply(qty);
            BigDecimal fee = amt.multiply(feeRate).setScale(0, RoundingMode.HALF_UP);
            BigDecimal tax = amt.multiply(taxRate).setScale(0, RoundingMode.HALF_UP);
            System.out.println(BsType);
            if (BsType == 'B') {
                return amt.add(fee).setScale(2, RoundingMode.HALF_UP);
            } else {
                return amt.subtract(fee).subtract(tax).setScale(2, RoundingMode.HALF_UP);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public BigDecimal getCost(BigDecimal qty, BigDecimal buyPrice) {
        try {
            BigDecimal amt = buyPrice.multiply(qty);
            BigDecimal fee = amt.multiply(feeRate).setScale(0, RoundingMode.HALF_UP);
            BigDecimal cost = amt.add(fee).setScale(2, RoundingMode.HALF_UP);
            return cost;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public BigDecimal getNowPrice(BigDecimal nowPrice) {
        try {
            return nowPrice.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String getModDate() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar modDate = Calendar.getInstance();
            return dateFormat.format(modDate.getTime());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String getModTime() {
        try {
            Format modTime = new SimpleDateFormat("HHmmss");
            return modTime.format(new Date());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
