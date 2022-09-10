package com.practice.esmp_demo.service;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@Service
public class Calculate {

    private int qty;
    private double buyPrice;

    public int getMarketValue(Object qty, Object nowPrice) {
        int marketValue = (int) Math.round(Integer.parseInt(qty.toString()) * Double.parseDouble(nowPrice.toString()));
        return (int) (marketValue-Math.round(marketValue*0.003)-Math.round(marketValue*0.001425));
    }

    public int getUnrealProfit(Object qty, Object nowPrice, Object cost){
        int marketValue = (int) Math.round(Integer.parseInt(qty.toString()) * Double.parseDouble(nowPrice.toString()));
        return (int) (marketValue-Math.round(marketValue*0.003)-Math.round(marketValue*0.001425)-Integer.parseInt(cost.toString()));
    }

    public void set(int qty,double buyPrice){
        this.qty = qty;
        this.buyPrice = buyPrice;
    }

    public double getAmt(){
        return this.buyPrice * this.qty;
    }

    public int getFee(){
        return (int) (Math.round(this.buyPrice * this.qty * 0.001425));
    }

    public int getTax(){
        return (int) (Math.round(this.buyPrice * this.qty * 0.003));
    }

    public double getNetAmt(){
        double netAmt = (this.buyPrice * this.qty) - (Math.round(this.buyPrice * this.qty * 0.001425)) - (Math.round(this.buyPrice * this.qty * 0.003));
        return netAmt;
    }


    public String getModDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        return dateFormat.format(c1.getTime());
    }

    public String getModTime(){
        Format timeFormat = new SimpleDateFormat("HHmmss");
        return timeFormat.format(new Date());
    }


}
