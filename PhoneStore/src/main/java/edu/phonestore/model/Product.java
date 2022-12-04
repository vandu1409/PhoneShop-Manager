/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.model;

/**
 *
 * @author Văn Dự
 */
public class Product {
    private String productId;
    private String title;
    private String image;
    private Double price;
    private Double importPrice;
    private int ram;
    private int rom;
    private int quantity;
    private String color;
    private String brandId;

    public Product() {
    }

    public Product(String productId, String title, String image, Double price, Double importPrice, int ram, int rom, int quantity, String color, String brandId) {
        this.productId = productId;
        this.title = title;
        this.image = image;
        this.price = price;
        this.importPrice = importPrice;
        this.ram = ram;
        this.rom = rom;
        this.quantity = quantity;
        this.color = color;
        this.brandId = brandId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

   

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Double importPrice) {
        this.importPrice = importPrice;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quatity) {
        this.quantity = quatity;
    }

 
    
    
}

    