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
public class Customer {

    private String customerId;
    private String fullname;
    private String email;
    private String phone;
    private boolean gender;
    private Date birthday;
    private Date createDate;
    private String address;
    private String notes;
    private int purchaseTimes;

    public Customer() {
    }

    public Customer(String customerId, String fullname, String email, String phone, boolean gender, Date birthday, Date createDate, String address, String notes, int purchaseTimes) {
        this.customerId = customerId;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
        this.createDate = createDate;
        this.address = address;
        this.notes = notes;
        this.purchaseTimes = purchaseTimes;
    }

  

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPurchaseTimes() {
        return purchaseTimes;
    }

    public void setPurchaseTimes(int purchaseTimes) {
        this.purchaseTimes = purchaseTimes;
    }

    @Override
    public String toString() {
        return  customerId +" - "+fullname +" - " + phone ;
    }
    
    

}
