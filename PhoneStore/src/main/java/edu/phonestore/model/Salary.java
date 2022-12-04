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
public class Salary {

    private int id;
    private String userId;
    private double basicSalary;// lương cơ bản
    private double commisson;//huê hồng
    private double advanceSalary; //lương ứng trước
    private double salaryDeducted;//lương bịn trừ
    private double bonus;
    private Date stratDate;
    private Date endDate;
    private double numberDayWorking;
    private double totalSalary;
    private Date receviedDate;
    private String notes;

    public Salary() {
    }

    public Salary(int id, String userId, double basicSalary, double commisson, double advanceSalary, double salaryDeducted, double bonus, Date stratDate, Date endDate, double numberDayWorking, double totalSalary, Date receviedDate, String notes) {
        this.id = id;
        this.userId = userId;
        this.basicSalary = basicSalary;
        this.commisson = commisson;
        this.advanceSalary = advanceSalary;
        this.salaryDeducted = salaryDeducted;
        this.bonus = bonus;
        this.stratDate = stratDate;
        this.endDate = endDate;
        this.numberDayWorking = numberDayWorking;
        this.totalSalary = totalSalary;
        this.receviedDate = receviedDate;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getCommisson() {
        return commisson;
    }

    public void setCommisson(double commisson) {
        this.commisson = commisson;
    }

    public double getAdvanceSalary() {
        return advanceSalary;
    }

    public void setAdvanceSalary(double advanceSalary) {
        this.advanceSalary = advanceSalary;
    }

    public double getSalaryDeducted() {
        return salaryDeducted;
    }

    public void setSalaryDeducted(double salaryDeducted) {
        this.salaryDeducted = salaryDeducted;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public Date getStratDate() {
        return stratDate;
    }

    public void setStratDate(Date stratDate) {
        this.stratDate = stratDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getNumberDayWorking() {
        return numberDayWorking;
    }

    public void setNumberDayWorking(double numberDayWorking) {
        this.numberDayWorking = numberDayWorking;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public Date getReceviedDate() {
        return receviedDate;
    }

    public void setReceviedDate(Date receviedDate) {
        this.receviedDate = receviedDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
