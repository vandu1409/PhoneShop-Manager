/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.phonestore.dao;

import edu.phonestore.helper.JdbcHelper;
import edu.phonestore.model.Product;
import edu.phonestore.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Văn Dự
 */
public class ProductDao {

    public boolean insert(Product product) {

        String query = "insert into Products values(?,?,?,?,?,?,?,?,?,?)";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, product.getProductId(), product.getTitle(), product.getImage(),
                product.getPrice(), product.getImportPrice(),
                product.getRam(), product.getRom(), product.getQuantity(), product.getColor(), product.getBrandId())) {

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Product product) {

        String query = "UPDATE Products set title=?,image = ?,\n"
                + "price = ?,importprice=?,ram=?,rom=?,quatity=?,color=?,companyId=? where productId=?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, product.getTitle(), product.getImage(),
                product.getPrice(), product.getImportPrice(),
                product.getRam(), product.getRom(), product.getQuantity(), product.getColor(), product.getBrandId(), product.getProductId())) {

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(String productId) {

        String query = "delete from Products where productId = ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, productId)) {

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Product> findAll() {

        String query = "select * from Products";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query)) {
            List<Product> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = readFromResultSet(rs);
                list.add(product);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Product findById(String id) {
        String query = "select * from Products where productId = ?";

        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query, id)) {

            ResultSet rs = ps.executeQuery();

            Product product = null;
            while (rs.next()) {
                product = readFromResultSet(rs);
            }

            rs.close();

            return product;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
     public List<Product> findBYBrand(String brandId) {

        String query = "select * from Products where companyId = ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query,brandId)) {
            List<Product> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = readFromResultSet(rs);
                list.add(product);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

        public List<Product> findByKeyword(String keyword) {

        String query = "select * from Products where title like ?";
        try ( PreparedStatement ps = JdbcHelper.prepareStatement(query,"%"+keyword+"%")) {
            List<Product> list = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = readFromResultSet(rs);
                list.add(product);
            }

            rs.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Product readFromResultSet(ResultSet rs) throws SQLException {

        Product product = new Product();
        product.setProductId(rs.getString(1));
        product.setTitle(rs.getString(2));
        product.setImage(rs.getString(3));
        product.setPrice(rs.getDouble(4));
        product.setImportPrice(rs.getDouble(5));
        product.setRam(rs.getInt(6));
        product.setRom(rs.getInt(7));
        product.setQuantity(rs.getInt(8));
        product.setColor(rs.getString(9));
        product.setBrandId(rs.getString(10));

        return product;
    }
}
