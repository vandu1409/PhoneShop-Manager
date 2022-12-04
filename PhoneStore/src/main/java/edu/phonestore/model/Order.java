/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.model;

import edu.phonestore.dao.CustomerDao;
import java.util.Date;

/**
 *
 * @author Văn Dự
 */
public class Order {

    public final static String PAID = "Đã thanh toán";
    public final static String UNPAID = "Chưa thanh toán";
    private String orderId;
    private double total;
    private Date createDate;
    private String customerId;
    private String voucherCode;
    private Double discount;
    private Double memberDiscount;
    private String status;
    private String creator;

    public Order() {
    }

    public Order(String orderId, double total, Date createDate, String customerId, String voucherCode, Double discount, Double memberDiscount, String status, String creator) {
        this.orderId = orderId;
        this.total = total;
        this.createDate = createDate;
        this.customerId = customerId;
        this.voucherCode = voucherCode;
        this.discount = discount;
        this.memberDiscount = memberDiscount;
        this.status = status;
        this.creator = creator;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getMemberDiscount() {
        return memberDiscount;
    }

    public void setMemberDiscount(Double memberDiscount) {
        this.memberDiscount = memberDiscount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.findById(customerId);

        return orderId + " - " + customerId + " - " + customer.getFullname();
    }

}
