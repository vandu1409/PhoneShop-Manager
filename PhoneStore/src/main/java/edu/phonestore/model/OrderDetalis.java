/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.model;

/**
 *
 * @author Văn Dự
 */
public class OrderDetalis {
    private String productId;
    private String orderId;
    private int quatity;
    private double totalProduct;

    public OrderDetalis() {
    }

    public OrderDetalis(String productId, String orderId, int quatity, double totalProduct) {
        this.productId = productId;
        this.orderId = orderId;
        this.quatity = quatity;
        this.totalProduct = totalProduct;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public double getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(double totalProduct) {
        this.totalProduct = totalProduct;
    }
    
    
}
