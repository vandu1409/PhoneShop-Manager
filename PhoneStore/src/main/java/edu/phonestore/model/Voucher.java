/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.model;

import java.util.Date;

/**
 *
 * @author Văn Dự
 */
public class Voucher {
    private String voucherCode;
    private String voucherName;
    private double discountByPrice;
    private double discountByPercent;
    private Date stratDate,enddate;
    private double amountApplied,maximunAmount;
    private String notes;

    public Voucher() {
    }

    
    public Voucher(String voucherCode, String voucherName, double discountByPrice, double discountByPercent, Date stratDate, Date enddate, double amountApplied, double maximunAmount, String notes) {
        this.voucherCode = voucherCode;
        this.voucherName = voucherName;
        this.discountByPrice = discountByPrice;
        this.discountByPercent = discountByPercent;
        this.stratDate = stratDate;
        this.enddate = enddate;
        this.amountApplied = amountApplied;
        this.maximunAmount = maximunAmount;
        this.notes = notes;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String nameVoucher) {
        this.voucherName = nameVoucher;
    }

    public double getDiscountByPrice() {
        return discountByPrice;
    }

    public void setDiscountByPrice(double discountByPrice) {
        this.discountByPrice = discountByPrice;
    }

    public double getDiscountByPercent() {
        return discountByPercent;
    }

    public void setDiscountByPercent(double discountByPercent) {
        this.discountByPercent = discountByPercent;
    }

    public Date getStratDate() {
        return stratDate;
    }

    public void setStratDate(Date stratDate) {
        this.stratDate = stratDate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date endate) {
        this.enddate = endate;
    }

    public double getAmountApplied() {
        return amountApplied;
    }

    public void setAmountApplied(double amountApplied) {
        this.amountApplied = amountApplied;
    }

    public double getMaximunAmount() {
        return maximunAmount;
    }

    public void setMaximunAmount(double maximunAmount) {
        this.maximunAmount = maximunAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return  voucherCode + " - " + voucherName ;
    }
    
    
    
    
}
